package com.advertisementproject.zuulgateway;

import com.advertisementproject.zuulgateway.Utilities.TestDataSourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
//@SpringBootTest(classes = TestDataSourceConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZuulGatewayApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.0");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.hikari.jdbc-url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.hikari.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.hikari.password", postgreSQLContainer::getPassword);

    }

    @Test
    void contextLoads() {
    }

}
