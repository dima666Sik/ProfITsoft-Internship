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
public class Diameter extends SIBasicPhysicalQuantity{
    @Id
    @SequenceGenerator(
            name = "diameter_id_sequence",
            sequenceName = "diameter_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "diameter_id_sequence"
    )
    private Long id;
    private SIUnitLength unit; // in kilometers
    @OneToOne(mappedBy = "diameter", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Planet planet;
}
