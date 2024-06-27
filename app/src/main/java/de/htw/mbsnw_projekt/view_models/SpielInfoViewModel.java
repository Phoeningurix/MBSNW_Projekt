package de.htw.mbsnw_projekt.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpielInfoViewModel extends ViewModel {

    private static final String TAG = "SpielInfoViewModel";

    private Spiel spiel;

    private final Repository repository;

    private LiveData<List<Ziel>> alleSpielZiele;

    private LiveData<List<Zielort>> alleSpielZielorte;

    //private int zieleAnzahl;


    public SpielInfoViewModel(Spiel spiel) {
        this.spiel = spiel;
        repository = App.getRepository();

        alleSpielZiele = repository.getSpielZiele(spiel.getId());
        alleSpielZielorte = repository.getAlleSpielZielorte(spiel.getId());

        //alleSpielZiele.observeForever(ziele -> zieleAnzahl = ziele.size());

    }

    public LiveData<List<Ziel>> getAlleSpielZiele() {
        return alleSpielZiele;
    }

    public LiveData<List<Zielort>> getAlleSpielZielorte() {
        return alleSpielZielorte;
    }

    /*public int getZieleAnzahl() {
        return zieleAnzahl;
    }*/
}
