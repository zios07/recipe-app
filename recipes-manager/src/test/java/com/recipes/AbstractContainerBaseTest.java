package com.recipes;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class AbstractContainerBaseTest {

    @Container static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource static void registerPgProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                        () -> String.format("jdbc:postgresql://localhost:%d/%s", postgres.getFirstMappedPort(),
                                        postgres.getDatabaseName()));
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
    }

    @Test public void test() {
        assertTrue(postgres.isRunning());
    }

}
