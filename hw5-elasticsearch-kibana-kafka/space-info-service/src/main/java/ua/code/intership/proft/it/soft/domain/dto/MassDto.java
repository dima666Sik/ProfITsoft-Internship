package ua.code.intership.proft.it.soft.domain.dto;

import lombok.Builder;
import ua.code.intership.proft.it.soft.domain.model.si.data.SIUnitMass;

@Builder
public record MassDto (
        SIUnitMass unit,
        double value
){
}
