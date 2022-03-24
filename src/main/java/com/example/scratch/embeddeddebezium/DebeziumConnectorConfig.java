package com.example.scratch.embeddeddebezium;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "debezium")
@Getter
@Setter
public class DebeziumConnectorConfig {

  private String name;
  private Map<String, String> connector;
  private Map<String, String> offset;
  private Map<String, String> database;
  private Map<String, String> heartbeat;
  private Map<String, String> kafka;
  private Map<String, String> properties;

  private final String NAME = "name";
  private final String CONNECTOR = "connector";
  private final String OFFSET = "offset";
  private final String DATABASE = "database";
  private final String HEARTBEAT = "heartbeat";

  @Bean
  public io.debezium.config.Configuration connector() {
    io.debezium.config.Configuration.Builder builder = io.debezium.config.Configuration.create();
    builder.with(NAME, name);
    connector.forEach((k, v) -> builder.with(String.format("%s.%s", CONNECTOR, k), v));
    offset.forEach((k, v) -> builder.with(String.format("%s.%s", OFFSET, k), v));
    database.forEach((k, v) -> builder.with(String.format("%s.%s", DATABASE, k), v));
    heartbeat.forEach((k, v) -> builder.with(String.format("%s.%s", HEARTBEAT, k), v));
    kafka.forEach(builder::with);
    properties.forEach(builder::with);
    return builder.build();
  }
}
