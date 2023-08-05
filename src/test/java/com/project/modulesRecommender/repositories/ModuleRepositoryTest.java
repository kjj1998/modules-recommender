package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.module.Module;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.neo4j.driver.Values.parameters;

@Testcontainers
@DataNeo4jTest
class ModuleRepositoryTest {

    @Container
    private static Neo4jContainer<?> neo4jContainer =
            new Neo4jContainer<>(DockerImageName.parse("neo4j:4.4"))
                    .withAdminPassword("somePassword");
    @Autowired
    private ModuleRepository repository;
    private static Driver driver;
    private static final String SET_UP_DATA =
            "MERGE (s:Student {first_name: 'John', last_name: 'Doe', student_id: '12345fghfh6' }) MERGE (m1:Module {course_name: 'A0001', course_code: 'A0001', community: 1 }) MERGE (m2:Module {course_name: 'A0002', course_code: 'A0002', community: 1 }) MERGE (s)-[:TAKES]->(m1)";

    private static final String SET_UP_RELATIONSHIP =
                    "MATCH (m1:Module {course_code: 'A0001'}) " +
                    "MATCH (m2:Module {course_code: 'A0002'}) " +
                    "MERGE (m1)-[:SIMILAR { score: 0.7 }]-(m2)";
//                    "MATCH (m1:Module {course_code: 'A0001'})-[s:SIMILAR]-(m2:Module {course_code: 'A0002'}) ";
//                    "SET s.score = $score";
    @BeforeAll
    static void setUp() {
        neo4jContainer.start();

        AuthToken auth = AuthTokens.basic("neo4j", neo4jContainer.getAdminPassword());
        driver = GraphDatabase.driver(neo4jContainer.getBoltUrl(), auth);
        var session = driver.session();
        session.executeWrite(tx -> {
            var result = tx.run(SET_UP_DATA);
            result.consume();
            return 1;
        });
        session.executeWrite(tx -> {
            var result = tx.run(SET_UP_RELATIONSHIP, parameters("score", 0.7));
            result.consume();
            return 1;
        });
    }

    @AfterAll
    static void tearDown() {
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
    public void findByIdShouldWork() {
        Optional<Module> result = repository.findById("A0001");
        assertThat(result.get()).isNotNull();
    }

    @Test
    public void recommendShouldWork() {
        List<Module> result = repository.recommend2("12345fghfh6");

        assertThat(result.get(0).courseCode).isEqualTo("A0002");
    }
}