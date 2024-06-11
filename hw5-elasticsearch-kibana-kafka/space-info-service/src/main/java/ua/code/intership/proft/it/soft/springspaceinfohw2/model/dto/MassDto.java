package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto;

import lombok.Builder;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitMass;
@Builder
public record MassDto (
        SIUnitMass unit,
        double value
){
}
