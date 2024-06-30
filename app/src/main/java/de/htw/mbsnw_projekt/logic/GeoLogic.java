package de.htw.mbsnw_projekt.logic;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Zielort;

public interface GeoLogic {

    /**
     * Entfernung zwischen Zielort und aktuellem Punkt berechnen
     * @param zielort Zielort
     * @param punkt altueller Punkt
     * @return Entfernung
     */
    double getEntfernung(Zielort zielort, Punkt punkt);

    double getEntfernung(Punkt p1, Punkt p2);


}
