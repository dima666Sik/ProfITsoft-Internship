package ua.code.intership.proft.it.soft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.code.intership.proft.it.soft.domain.model.Planet;
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Page<Planet> findByPlanetarySystemId(Long planetarySystemId, Pageable pageable);
    Page<Planet> findByPlanetarySystemIdAndPlanetarySystemName(Long planetarySystemId, String planetarySystemName, Pageable pageable);
}
