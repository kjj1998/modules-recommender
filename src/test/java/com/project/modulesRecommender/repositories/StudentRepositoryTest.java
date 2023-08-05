package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.student.models.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataNeo4jTest
class StudentRepositoryTest {
    @Container
    private static Neo4jContainer<?> neo4jContainer =
            new Neo4jContainer<>(DockerImageName.parse("neo4j:4.4"))
                    .withAdminPassword("somePassword");
    @Autowired
    private StudentRepository repository;
    private static Driver driver;
    private static final String TEST_DATA =
            " MERGE (:Student {first_name: 'John', last_name: 'Doe', student_id: '12345fghfh6' })";

    @BeforeAll
    static void initializeNeo4j() {
        neo4jContainer.start();

        AuthToken auth = AuthTokens.basic("neo4j", neo4jContainer.getAdminPassword());
        driver = GraphDatabase.driver(neo4jContainer.getBoltUrl(), auth);
        var session = driver.session();
        session.executeWrite(tx -> {
            var result = tx.run(TEST_DATA);
            result.consume();
            return 1;
        });
    }

    @AfterAll
    static void stopNeo4j() {
        driver.close();
        neo4jContainer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @Test
    public void existsByIdShouldWork() {
        assertThat(repository.existsById("12345fghfh6")).isTrue();
    }

    @Test
    public void findByIdShouldWork() {
        Optional<Student> result = repository.findById("12345fghfh6");
        assertThat(result.get()).isNotNull();
    }
}