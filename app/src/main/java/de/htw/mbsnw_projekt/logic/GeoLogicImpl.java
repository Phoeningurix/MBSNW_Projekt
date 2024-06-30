package de.htw.mbsnw_projekt.logic;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Zielort;

public class GeoLogicImpl implements GeoLogic {

    private static volatile GeoLogicImpl instance;

    public static GeoLogicImpl getInstance() {
        if (instance == null) {
            synchronized (GeoLogicImpl.class) {
                if (instance == null) {
                    instance = new GeoLogicImpl();
                }
            }
        }
        return instance;
    }
    
    @Override
    public double getEntfernung(Zielort zielort, Punkt punkt) {
        return zielort.toGeopoint().distanceToAsDouble(punkt.toGeopoint());
    }

    @Override
    public double getEntfernung(Punkt p1, Punkt p2) {
        return p1.toGeopoint().distanceToAsDouble(p2.toGeopoint());
    }

}
