package ua.code.intership.proft.it.soft;

import ua.code.intership.proft.it.soft.model.Planet;
import ua.code.intership.proft.it.soft.model.si.data.Diameter;
import ua.code.intership.proft.it.soft.model.si.data.Mass;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitLength;
import ua.code.intership.proft.it.soft.model.si.data.SIUnitMass;

public class Main {
    public static void main(String[] args) {
        Planet planet1 = Planet.builder()
                              .mass(Mass.builder()
                                        .unit(SIUnitMass.KILOGRAM)
                                        .value(10.5)
                                        .build())
                              .diameter(Diameter.builder()
                                                .unit(SIUnitLength.KILOMETER)
                                                .value(14.5)
                                                .build())
                              .hasMoons(true)
                              .hasRings(true)
                              .build();
        Planet planet2 = Planet.builder()
                              .mass(Mass.builder()
                                        .unit(SIUnitMass.KILOGRAM)
                                        .value(10.5)
                                        .build())
                              .diameter(Diameter.builder()
                                                .unit(SIUnitLength.KILOMETER)
                                                .value(14.5)
                                                .build())
                              .hasMoons(true)
                              .hasRings(true)
                              .build();
        System.out.println(planet1.equals(planet2));
    }
}
