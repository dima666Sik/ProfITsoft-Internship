package ua.code.intership.proft.it.soft.springspaceinfohw2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitMass;

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