package de.htw.mbsnw_projekt.logic;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Zielort;

public interface GeoLogic {

    double getEntfernung(Zielort zielort, Punkt punkt);

}
