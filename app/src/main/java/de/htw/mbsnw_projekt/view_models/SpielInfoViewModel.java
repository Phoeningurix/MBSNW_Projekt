package de.htw.mbsnw_projekt.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpielInfoViewModel extends ViewModel {

    private static final String TAG = "SpielInfoViewModel";

    private Spiel spiel;

    private final Repository repository;

    private final LiveData<List<Ziel>> alleSpielZiele;

    private final LiveData<List<Zielort>> alleSpielZielorte;

    private LiveData<List<Punkt>> alleSpielPunkte;

    private LiveData<List<Ziel>> nichErreichteZiele;


    public SpielInfoViewModel(Spiel spiel) {
        this.spiel = spiel;
        repository = App.getRepository();

        alleSpielZiele = repository.getSpielZiele(spiel.getId());
        alleSpielZielorte = repository.getAlleSpielZielorte(spiel.getId());
        alleSpielPunkte = repository.getSpielPunkte(spiel.getId());
        nichErreichteZiele = repository.getNichtErreichteSpielZiele(spiel.getId());

    }

    public LiveData<List<Ziel>> getAlleSpielZiele() {
        return alleSpielZiele;
    }

    public LiveData<List<Zielort>> getAlleSpielZielorte() {
        return alleSpielZielorte;
    }

    public LiveData<List<Punkt>> getAlleSpielPunkte() {
        return alleSpielPunkte;
    }

    public LiveData<List<Ziel>> getNichErreichteZiele() {
        return nichErreichteZiele;
    }
}
