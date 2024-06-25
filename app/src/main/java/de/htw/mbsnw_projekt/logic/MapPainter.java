package de.htw.mbsnw_projekt.logic;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

public interface MapPainter {

    void zielHinzufuegen(Zielort zielort);

    void punktHinzufuegen(Punkt punkt);

    void punkteSetzen(List<Punkt> punkte);

}
