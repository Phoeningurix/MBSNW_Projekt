package de.htw.mbsnw_projekt.logic;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
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

    @Override
    public double streckeBerechnen(List<Punkt> punkte) {
        double strecke = 0;
        for (int i = 0; i < punkte.size() - 1; i++) {
            strecke += getEntfernung(punkte.get(i), punkte.get(i+1));
        }
        return strecke;
    }
}
