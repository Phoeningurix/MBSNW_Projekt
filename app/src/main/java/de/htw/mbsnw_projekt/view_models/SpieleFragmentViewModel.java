package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpieleFragmentViewModel extends ViewModel {

    private final Repository repository;

    private final LiveData<List<Spiel>> alleSpiele;

    public SpieleFragmentViewModel() {
        repository = App.getRepository();

        alleSpiele = repository.getSpiele();
    }

    public LiveData<List<Spiel>> getAlleSpiele() {
        return alleSpiele;
    }

}
