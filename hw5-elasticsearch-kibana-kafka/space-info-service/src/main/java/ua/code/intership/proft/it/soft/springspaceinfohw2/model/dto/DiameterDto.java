package ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto;

import lombok.Builder;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.si.data.SIUnitLength;
@Builder
public record DiameterDto (
        SIUnitLength unit,
        double value
){
}
