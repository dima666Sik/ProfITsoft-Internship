package ua.code.intership.proft.it.soft.model.si.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Diameter extends SIBasicPhysicalQuantity{
    private SIUnitLength unit; // in kilometers
}
