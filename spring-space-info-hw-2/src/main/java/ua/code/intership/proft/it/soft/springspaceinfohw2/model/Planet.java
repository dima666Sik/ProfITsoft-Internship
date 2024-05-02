package ua.code.intership.proft.it.soft.springspaceinfohw2.model;

import jakarta.persistence.*;
import lombok.*;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.Mass;

@Entity
@Table(name = "planets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    @Id
    @SequenceGenerator(
            name = "planet_id_sequence",
            sequenceName = "planet_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "planet_id_sequence"
    )
    private Long id;

    private String name;

    private Boolean hasRings;

    private Boolean hasMoons;

    private String atmosphericComposition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mass_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Mass mass;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diameter_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Diameter diameter;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planetary_system_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PlanetarySystem planetarySystem;

}



