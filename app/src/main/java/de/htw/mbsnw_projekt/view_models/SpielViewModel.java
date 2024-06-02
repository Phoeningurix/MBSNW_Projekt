package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.ViewModel;

import de.htw.mbsnw_projekt.database.models.Spiel;

public class SpielViewModel extends ViewModel {

    private final Spiel aktuellesSpiel;

    public SpielViewModel(Spiel aktuellesSpiel) {
        this.aktuellesSpiel = aktuellesSpiel;
    }

    public Spiel getAktuellesSpiel() {
        return aktuellesSpiel;
    }


}
