package de.htw.mbsnw_projekt.logic;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;

public interface GameLogic {

    /**
     * Index des aktuellen Ziels zurückgeben
     * @param zielList Ziel-Liste
     * @return Index
     */
    int getCurrentZielIndex(List<Ziel> zielList);

    /**
     * Übrige Zeit zurückgeben
     * @param spiel aktuelles Spiel
     * @return Zeit in millisekunden
     */
    long getTimeLeft(Spiel spiel);

    /**
     * Aktuelles Spiel beenden
     * @param spiel Spiel
     */
    void spielBeenden(Spiel spiel);

    /**
     * Ziel erreichen
     * @param ziel Ziel
     */
    void setZielReached(Ziel ziel);

    /**
     * Zeit umrechnen
     * @param millis Zeit in Millisekunden
     * @return String mit formatierter Zeit
     */
    String millisToString(long millis);






}
