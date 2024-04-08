package ua.code.intership.proft.it.soft.model.si.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Mass extends SIBasicPhysicalQuantity{
    private SIUnitMass unit; // kg

}
