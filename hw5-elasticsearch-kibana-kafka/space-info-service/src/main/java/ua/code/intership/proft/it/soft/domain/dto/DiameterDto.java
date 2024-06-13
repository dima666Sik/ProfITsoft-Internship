package ua.code.intership.proft.it.soft.domain.dto;

import lombok.Builder;
import ua.code.intership.proft.it.soft.domain.model.si.data.SIUnitLength;

@Builder
public record DiameterDto (
        SIUnitLength unit,
        double value
){
}
