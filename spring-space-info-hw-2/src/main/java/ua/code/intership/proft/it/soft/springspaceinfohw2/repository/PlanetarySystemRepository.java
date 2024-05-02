package ua.code.intership.proft.it.soft.springspaceinfohw2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.PlanetarySystem;

public interface PlanetarySystemRepository extends JpaRepository<PlanetarySystem, Long> {
    boolean existsByName(String name);
}
