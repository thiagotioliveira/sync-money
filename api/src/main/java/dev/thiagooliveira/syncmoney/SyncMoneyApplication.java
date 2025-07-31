package dev.thiagooliveira.syncmoney;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(title = "Sync-Money API", version = "1.0.0", description = "Sync-Money API"))
@SpringBootApplication
public class SyncMoneyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SyncMoneyApplication.class, args);
  }
}
