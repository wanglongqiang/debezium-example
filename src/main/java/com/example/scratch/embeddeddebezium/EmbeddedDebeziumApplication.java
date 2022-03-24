package com.example.scratch.embeddeddebezium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class EmbeddedDebeziumApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmbeddedDebeziumApplication.class, args);
  }

}
