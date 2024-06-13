package ua.code.intership.proft.it.soft.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.code.intership.proft.it.soft.domain.model.PlanetarySystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlanetarySystemRepositoryIntegrationTest {
    @Autowired
    private PlanetarySystemRepository planetarySystemRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void existsByName() {
        PlanetarySystem planetarySystem = testEntityManager.persist(PlanetarySystem.builder()
                                                                                   .name("Help")
                                                                                   .build());

        testEntityManager.persist(planetarySystem);

        boolean result = planetarySystemRepository.existsByName(planetarySystem.getName());

        assertTrue(result);
    }
}