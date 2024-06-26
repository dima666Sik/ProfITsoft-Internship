package ua.code.intership.proft.it.soft.service.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.time.Duration;
import java.util.List;

@Slf4j
@TestConfiguration
public class TestElasticSearchConfiguration extends ElasticsearchConfiguration {

    public static final String ELASTICSEARCH_DOCKER_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:8.10.4";

    @Bean(destroyMethod = "stop")
    public ElasticsearchContainer elasticsearchContainer() {
        ElasticsearchContainer container = new ElasticsearchContainer(
                ELASTICSEARCH_DOCKER_IMAGE);
        container.setEnv(List.of(
                "discovery.type=single-node",
                "ES_JAVA_OPTS=-Xms1g -Xmx1g",
                "xpack.security.enabled=false")
        );
        container.start();
        log.info("Started ES container on address {}", container.getHttpHostAddress());
        return container;
    }

    @Bean
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                                  .connectedTo(elasticsearchContainer().getHttpHostAddress())
                                  .build();
    }

}