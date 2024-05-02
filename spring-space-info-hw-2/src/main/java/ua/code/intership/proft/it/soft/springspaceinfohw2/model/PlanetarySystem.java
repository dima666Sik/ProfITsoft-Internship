package ua.code.intership.proft.it.soft.springspaceinfohw2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "planetary_systems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetarySystem {
    @Id
    @SequenceGenerator(
            name = "planetary_system_id_sequence",
            sequenceName = "planetary_system_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "planetary_system_id_sequence"
    )
    private Long id;
    private String name;
    @OneToMany(mappedBy = "planetarySystem", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Planet> planets;
}
