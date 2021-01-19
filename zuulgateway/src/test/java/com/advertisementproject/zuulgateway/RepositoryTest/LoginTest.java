package com.advertisementproject.zuulgateway.RepositoryTest;

import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoginTest {

    @Autowired
    UserRepository repository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.0");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    // We need to create a utility class that mocks our data
    // We also need to find a better solution to not repeat code in every test class

    @Test
    public void shouldRegisterUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("teletubbies@hotmail.com");
        user.setHashedPassword("OKejDaniel");
        user.setPhoneNumber("0709724042");
        user.setEnabled(true);
        repository.save(user);
        Optional<User> userFromDB = repository.findById(user.getId());
        Assertions.assertThat(user.getId()).isEqualTo(userFromDB.get().getId());
    }
}
