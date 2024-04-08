package ua.code.intership.proft.it.soft.model.si.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
public abstract class SIBasicPhysicalQuantity {
    protected double value;
}
