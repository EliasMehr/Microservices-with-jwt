package com.advertisementproject.zuulgateway.RepositoryTest;

import com.advertisementproject.zuulgateway.Utilities.TestDataSourceConfig;
import com.advertisementproject.zuulgateway.db.models.User;
import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@Testcontainers
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = TestDataSourceConfig.class)
public class LoginTest {

    @Autowired
    UserRepository repository;


    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.0");


    @Test
    public void shouldRegisterUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Teletubbies");
        user.setPassword("OKejDaniel");
        user.setEnabled(true);

        repository.save(user);
        Optional<User> byId = repository.findById(user.getId());
        Assertions.assertThat(user.getId()).isEqualTo(byId.get().getId());
    }
}
