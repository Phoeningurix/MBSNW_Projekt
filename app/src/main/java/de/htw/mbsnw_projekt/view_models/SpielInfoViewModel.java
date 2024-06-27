package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpielInfoViewModel extends ViewModel {

    private Spiel spiel;

    private final Repository repository;

    private LiveData<List<Ziel>> alleSpielZiele;

    private LiveData<List<Zielort>> alleSpielZielorte;


    public SpielInfoViewModel(Spiel spiel) {
        this.spiel = spiel;
        repository = App.getRepository();

        alleSpielZiele = repository.getSpielZiele(spiel.getId());

    }

    public LiveData<List<Ziel>> getAlleSpielZiele() {
        return alleSpielZiele;
    }
}
