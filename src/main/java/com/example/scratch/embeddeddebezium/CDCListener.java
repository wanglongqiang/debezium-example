package com.example.scratch.embeddeddebezium;

import static io.debezium.data.Envelope.FieldName.SOURCE;

import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CDCListener {

  private final Executor executor = Executors.newSingleThreadExecutor();

  private final EmbeddedEngine engine;

  private final KafkaTemplate<String, Message> kafkaTemplate;

  private TableChangeTopicConfig tableChangeTopicConfig;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private CDCListener(Configuration connector, KafkaTemplate<String, Message> kafkaTemplate, TableChangeTopicConfig tableChangeTopicConfig) {
    this.engine = EmbeddedEngine.create()
        .using(connector)
        .notifying(this::handleEvent).build();
    this.kafkaTemplate = kafkaTemplate;
    this.tableChangeTopicConfig = tableChangeTopicConfig;
  }

  @PostConstruct
  private void start() {
    this.executor.execute(engine);
  }

  @PreDestroy
  private void stop() {
    if (engine != null) {
      engine.stop();
    }
  }

  private void handleEvent(SourceRecord sourceRecord) {
    Struct sourceRecordValue = (Struct) sourceRecord.value();
    if (sourceRecordValue != null) {
      String sourceSchemaName = sourceRecordValue.schema().name();
      if (!sourceSchemaName.contains("Heartbeat")) {
        try {
          Struct source = sourceRecordValue.getStruct(SOURCE);
          String connector = source.getString("connector");
          String database = source.getString("db");
          String table = source.getString("table");
          if (table == null) {
            return;
          }
          String schema = "";
          String key = "";
          if (connector.equals("postgresql")) {
            schema = source.getString("schema");
            key = String.format("%s.%s.%s", database, schema, table);
          } else if (connector.equals("mysql")) {
            key = String.format("%s.%s", database, table);
          }
          Message message = Message.builder()
              .database(database)
              .schema(schema)
              .table(table)
              .build();
          kafkaTemplate.send(tableChangeTopicConfig.getName(), key, message);
          log.debug("Produced :: key: {} | message: {}", key, message);
          log.debug("Source record value: {}", sourceRecordValue);
        } catch (DataException e) {
          log.debug(e.getMessage());
        }
      }
    }
  }
}
