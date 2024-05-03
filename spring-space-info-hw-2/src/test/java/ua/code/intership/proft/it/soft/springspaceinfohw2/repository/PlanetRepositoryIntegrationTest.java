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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlanetRepositoryIntegrationTest {
    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByPlanetarySystemId() {
        Planet planet = Planet.builder()
                              .name("Earth")
                              .mass(Mass.builder()
                                        .value(10.0)
                                        .unit(SIUnitMass.KILOGRAM)
                                        .build())
                              .diameter(Diameter.builder()
                                                .value(10.0)
                                                .unit(SIUnitLength.KILOMETER)
                                                .build())
                              .hasRings(true)
                              .hasMoons(true)
                              .atmosphericComposition("Nitrogen, Oxygen")
                              .build();

        PlanetarySystem planetarySystem = testEntityManager.persist(PlanetarySystem.builder()
                                                 .name("Help")
                                                 .build());

        planet.setPlanetarySystem(planetarySystem);

        testEntityManager.persist(planet);

        Pageable paging = PageRequest.of(0,3);

        Page<Planet> retrievedProject = planetRepository.findByPlanetarySystemId(planet.getPlanetarySystem().getId(), paging);

        assertThat(retrievedProject).contains(planet);
    }

    @Test
    void findByPlanetarySystemIdAndPlanetarySystemName() {
        Planet planet = Planet.builder()
                              .name("Earth")
                              .mass(Mass.builder()
                                        .value(10.0)
                                        .unit(SIUnitMass.KILOGRAM)
                                        .build())
                              .diameter(Diameter.builder()
                                                .value(10.0)
                                                .unit(SIUnitLength.KILOMETER)
                                                .build())
                              .hasRings(true)
                              .hasMoons(true)
                              .atmosphericComposition("Nitrogen, Oxygen")
                              .build();

        PlanetarySystem planetarySystem = testEntityManager.persist(PlanetarySystem.builder()
                                                                                   .name("Help1")
                                                                                   .build());

        planet.setPlanetarySystem(planetarySystem);

        testEntityManager.persist(planet);

        Pageable paging = PageRequest.of(0,3);

        Page<Planet> retrievedProject = planetRepository.findByPlanetarySystemIdAndPlanetarySystemName(planet.getPlanetarySystem().getId(), planet.getPlanetarySystem().getName(), paging);

        assertThat(retrievedProject).contains(planet);
    }
}