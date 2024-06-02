package de.htw.mbsnw_projekt.view_models;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.function.Consumer;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class CreateSpielViewModel extends ViewModel {

    private static final String TAG = "CreateSpielViewModel";

    private final Repository repository;

    public CreateSpielViewModel() {
        repository = App.getRepository();
    }

    public void createSpiel(Spiel spiel, Consumer<Spiel> taskAfter) {
        if (spiel.getEndTimestamp() != null) {
            Log.e(TAG, "createSpiel: hat schon ein EndTimeStamp");
        }
        repository.insert(spiel);
        repository.getAktuellesSpiel(taskAfter);
    }

}
