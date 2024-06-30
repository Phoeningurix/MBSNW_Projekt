package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class StatsViewModel extends ViewModel {

    private Repository repository;

    private LiveData<List<Spiel>> alleSpiele;
    private LiveData<List<Spiel>> erfolgreicheSpiele;

    private LiveData<List<Spiel>> nichterfolgreicheSpiele;

    public StatsViewModel() {
        repository = App.getRepository();

        alleSpiele = repository.getSpiele();
        erfolgreicheSpiele = repository.getErfolgreicheSpiele();
        nichterfolgreicheSpiele = repository.getNichtErfolgreicheSpiele();
    }

    public LiveData<List<Spiel>> getAlleSpiele() {
        return alleSpiele;
    }

    public LiveData<List<Spiel>> getErfolgreicheSpiele() {
        return erfolgreicheSpiele;
    }

    public LiveData<List<Spiel>> getNichterfolgreicheSpiele() {
        return nichterfolgreicheSpiele;
    }
}
