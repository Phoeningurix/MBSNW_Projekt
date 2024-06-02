package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpielViewModel extends ViewModel {

    private final Spiel aktuellesSpiel;
    private final Repository repository;

    public SpielViewModel(Spiel aktuellesSpiel) {
        this.aktuellesSpiel = aktuellesSpiel;
        repository = App.getRepository();
    }

    public Spiel getAktuellesSpiel() {
        return aktuellesSpiel;
    }

    public LiveData<List<Ziel>> getSpielZiele() {
        return repository.getSpielZiele(aktuellesSpiel.getId());
    }


}
