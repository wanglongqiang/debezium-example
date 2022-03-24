package com.example.scratch.embeddeddebezium;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConfigurationProperties(prefix = "table-change-topic")
@Getter
@Setter
public class TableChangeTopicConfig {

  private String name;
  private Integer partitions;
  private Integer replicationFactor;

  @Bean
  public NewTopic tableChange() {
    return TopicBuilder.name(name)
        .partitions(partitions)
        .replicas(replicationFactor)
        .compact()
        .build();
  }

}
