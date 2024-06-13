package ua.code.intership.proft.it.soft.domain.model.si.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.code.intership.proft.it.soft.domain.model.Planet;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mass extends SIBasicPhysicalQuantity{
    @Id
    @SequenceGenerator(
            name = "mass_id_sequence",
            sequenceName = "mass_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mass_id_sequence"
    )
    private Long id;
    private SIUnitMass unit; // kg
    @OneToOne(mappedBy = "mass", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Planet planet;
}
