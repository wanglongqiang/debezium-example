package com.example.scratch.embeddeddebezium;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

  @KafkaListener(topics = "${table-change-topic.name}")
  public void consume(ConsumerRecord<String, Message> record) {
    log.debug("Consumed :: key: {} | message {}", record.key(), record.value());
    log.debug("develop");
  }
}
