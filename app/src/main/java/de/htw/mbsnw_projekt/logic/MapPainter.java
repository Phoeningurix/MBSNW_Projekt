package de.htw.mbsnw_projekt.logic;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Ziel;

public interface MapPainter {

    void zielHinzufuegen(Ziel ziel);

    void punktHinzufuegen(Punkt punkt);

}
