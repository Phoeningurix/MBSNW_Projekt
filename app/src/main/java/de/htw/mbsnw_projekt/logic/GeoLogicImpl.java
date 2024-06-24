package de.htw.mbsnw_projekt.logic;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Zielort;

public class GeoLogicImpl implements GeoLogic {

    @Override
    public double getEntfernung(Zielort zielort, Punkt punkt) {
        return zielort.toGeopoint().distanceToAsDouble(punkt.toGeopoint());
    }


}
