package ua.code.intership.proft.it.soft.domain.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(indexName="messages")
public class Message {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String emailConsumer;

    @Field(type = FieldType.Text)
    private String status;

    @Field(type = FieldType.Text)
    private String errorMsg;

    @Field(type = FieldType.Integer)
    private Integer retryCount;

    @Field(type = FieldType.Text)
    private String lastAttemptTime;

}
