package ua.code.intership.proft.it.soft.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ua.code.intership.proft.it.soft.domain.model.Message;

@Repository
public interface EmailRepository extends ElasticsearchRepository<Message, String> {
}
