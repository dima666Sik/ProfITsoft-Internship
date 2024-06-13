package ua.code.intership.proft.it.soft.config;

import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonBeanConfig {
    @Bean
    public JsonFactory jsonFactory() {
        return new JsonFactory();
    }
}
