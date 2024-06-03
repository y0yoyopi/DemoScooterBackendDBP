package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp;



import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class AbstractContainerBaseTest {

    static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("e2eTestDb")
            .withUsername("e2e")
            .withPassword("e2e");

    static {
        postgresqlContainer.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
}

