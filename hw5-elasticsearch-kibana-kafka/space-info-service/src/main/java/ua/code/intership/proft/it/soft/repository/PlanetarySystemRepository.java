package ua.code.intership.proft.it.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.code.intership.proft.it.soft.domain.model.PlanetarySystem;

public interface PlanetarySystemRepository extends JpaRepository<PlanetarySystem, Long> {
    boolean existsByName(String name);
}
