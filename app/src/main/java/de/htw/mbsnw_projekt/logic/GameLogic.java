package de.htw.mbsnw_projekt.logic;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;

public interface GameLogic {

    int getCurrentZielIndex(List<Ziel> zielList);

    long getTimeLeft(Spiel spiel);

    void spielBeenden(Spiel spiel);

    void setZielReached(Ziel ziel);

    String millisToString(long millis);






}
