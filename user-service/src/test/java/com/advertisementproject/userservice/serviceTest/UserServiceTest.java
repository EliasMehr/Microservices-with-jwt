package com.advertisementproject.userservice.serviceTest;

import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.db.repository.UserRepository;
import com.advertisementproject.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.UUID;


@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {

    private final UserRepository repository;
    private final UserService userService;

    @Autowired
    public UserServiceTest(UserRepository repository) {
        this.repository = repository;
        this.userService = new UserService(repository);
    }

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.0");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setupDb() {
//        User userKalle = User.builder()
//                .id(UUID.fromString("c6e1b024-1624-4dcd-ae90-bcbed3c5555b"))
//                .firstName("Kalle")
//                .lastName("Anka")
//                .address("Gatagatan 15")
//                .city("Ankeborg")
//                .zipCode("12345")
//                .email("kalle@anka.se")
//                .phoneNumber("0708123456")
//                .identificationNumber("19891011-1957")
//                .companyType(CompanyType.NOT_SPECIFIED)
//                .enabled(true)
//                .rawPassword("Knattarna12!")
//                .hashedPassword(new BCryptPasswordEncoder(12).encode("Knattarna12!"))
//                .role(Role.CUSTOMER)
//                .build();
//
//        repository.save(userKalle);
//
//        User userAdlibris = User.builder() //TODO company name needs to be represented. Right now we have only first name, last name, but we could use "name" as both company and customer has in the project plan
//                .id(UUID.fromString("39b0a604-c2b5-4df8-a442-1f649ecd635f"))
//                .firstName("Artur")
//                .lastName("Svensson")
//                .address("Sveavägen 56C")
//                .city("Stockholm")
//                .zipCode("11134")
//                .email("kontakt@adlibris.se")
//                .phoneNumber("0844140501") //TODO allow phone numbers such as 08-etc? 1 at the end is not part of a real number
//                .identificationNumber("556261-3512")
//                .companyType(CompanyType.RETAIL)
//                .enabled(true)
//                .rawPassword("BraBok:13")
//                .hashedPassword(new BCryptPasswordEncoder(12).encode("BraBok:13"))
//                .role(Role.CUSTOMER)
//                .build();
//        repository.save(userAdlibris);

    }



    @Test
    void contextLoads() {
    }

    @Test
    void registerUser() {
//
//        //TODO make this a registration test!
//        User userKalle = User.builder()
//                .id(UUID.fromString("9a42a264-529e-43b6-ad4d-9a832a91f927"))
//                .firstName("Erik")
//                .lastName("Andersson")
//                .address("Kvartersgatan 23")
//                .city("Kista")
//                .zipCode("11642")
//                .email("epic_hero@gmail.com")
//                .phoneNumber("0713123476")
//                .identificationNumber("19891011-1957")
//                .companyType(CompanyType.NOT_SPECIFIED)
//                .enabled(true)
//                .rawPassword("Knattarna12!")
//                .hashedPassword(new BCryptPasswordEncoder(12).encode("Knattarna12!"))
//                .role(Role.CUSTOMER)
//                .build();
//        repository.save(userKalle);
//
//        Assertions.assertEquals("epic_hero@gmail.com", userService.findUserById(UUID.fromString("9a42a264-529e-43b6-ad4d-9a832a91f927")).getEmail());
    }

    @Test
    void getUserExpectNotFound() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserById(UUID.fromString("9a42a264-529e-43b6-ad4d-9a832a91f927")));
    }
}