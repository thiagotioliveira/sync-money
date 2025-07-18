package dev.thiagooliveira.syncmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SyncMoneyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SyncMoneyApplication.class, args);
  }
}
