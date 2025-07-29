package dev.thiagooliveira.syncmoney;

import org.springframework.boot.SpringApplication;

public class TestSyncMoneyApplication {

  public static void main(String[] args) {
    SpringApplication.from(SyncMoneyApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
