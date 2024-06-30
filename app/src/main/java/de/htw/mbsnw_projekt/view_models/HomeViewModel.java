package de.htw.mbsnw_projekt.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.function.Consumer;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class HomeViewModel extends ViewModel {

    private final Repository repository;

    private final LiveData<Spiel> aktuellesSpielLiveData;

    public HomeViewModel() {
        repository = App.getRepository();
        aktuellesSpielLiveData = repository.getAktuellesSpielLiveData();
    }

    public LiveData<Spiel> getAktuellesSpielLiveData() {
        return aktuellesSpielLiveData;
    }

    /**
     * Aktuelles Spiel zurückgeben
     * @param task Task
     */
    public void getAktuellesSpiel(Consumer<Spiel> task) {
        repository.getAktuellesSpiel(task);
    }

    /**
     * Zurückgeben ob ein aktuelles Spiel existiert
     * @param taskErfolgreich Task wenn Spiel existiert
     * @param taskNichtErfolgreich Task wenn Spiel nicht existiert
     */
    public void existiertAktuellesSpiel(Runnable taskErfolgreich, Runnable taskNichtErfolgreich) {
        getAktuellesSpiel(spiel -> {
            if(spiel == null) {
                if (taskNichtErfolgreich != null) {
                    taskNichtErfolgreich.run();
                }
            } else {
                if (taskErfolgreich != null) {
                    taskErfolgreich.run();
                }
            }
        });
    }

}
