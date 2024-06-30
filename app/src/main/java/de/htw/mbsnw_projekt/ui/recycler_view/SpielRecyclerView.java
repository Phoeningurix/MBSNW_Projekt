package de.htw.mbsnw_projekt.ui.recycler_view;

import de.htw.mbsnw_projekt.database.models.Spiel;

public interface SpielRecyclerView {

    /**
     * Neue Activity starten
     * @param spiel Spiel übergeben
     */
    void onClick(Spiel spiel);

}
