package ua.code.intership.proft.it.soft.model.si.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class SIBasicPhysicalQuantity {
    protected double value;
}
