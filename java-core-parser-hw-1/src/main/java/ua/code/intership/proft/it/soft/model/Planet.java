package ua.code.intership.proft.it.soft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.code.intership.proft.it.soft.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.model.si.data.Mass;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    private long id;
    private String name;
    private Mass mass;
    private Diameter diameter;
    private boolean hasRings;
    private boolean hasMoons;
    private String atmosphericComposition;
    private PlanetarySystem planetarySystem;

}



