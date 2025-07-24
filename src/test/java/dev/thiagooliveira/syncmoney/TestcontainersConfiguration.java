package dev.thiagooliveira.syncmoney;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer() {
    PostgreSQLContainer<?> container =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(
                cmd ->
                    cmd.getHostConfig()
                        .withPortBindings(
                            new PortBinding(
                                Ports.Binding.bindPort(5433), // local port
                                new ExposedPort(5432) // container port
                                )));
    return container;
  }
}
