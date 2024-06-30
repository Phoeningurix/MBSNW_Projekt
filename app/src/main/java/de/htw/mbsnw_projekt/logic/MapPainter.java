package de.htw.mbsnw_projekt.logic;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

public interface MapPainter {

    /**
     * Ziele auf der Karte anzeigen
     * @param context context
     * @param zielort neuer Zielort
     */
    void zielHinzufuegen(Context context, Zielort zielort);

    /**
     * Alle Zielorte aus einer Liste hinzufügen
     * @param context Context
     * @param zielorte Alle Zielorte
     */
    void alleZielorteHinzufuegen(Context context, List<Zielort> zielorte);

    /**
     * Einzelnen Punkt hinzufügen
     * @param punkt Punkt
     */
    void punktHinzufuegen(Punkt punkt);

    /**
     * Alle punkte setzen
     * @param punkte Punkt-Liste
     */
    void punkteSetzen(List<Punkt> punkte);

    /**
     * Größe von einem Marker oder Icon ändern
     * @param drawable Icon Resource
     * @param width Breite
     * @param height Höhe
     * @return neues Icon
     */
    Drawable rescaleDrawable(Drawable drawable, int width, int height);

}
