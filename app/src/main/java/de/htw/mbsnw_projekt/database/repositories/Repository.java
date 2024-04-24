package de.htw.mbsnw_projekt.database.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import de.htw.mbsnw_projekt.database.models.*;

public interface Repository {

    void insert(Spiel spiel);

    void update(Spiel spiel);

    void delete(Spiel spiel);

    LiveData<List<Spiel>> getSpiele();

    void getAktuellesSpiel(Consumer<Spiel> task);

    LiveData<List<Spiel>> getErfolgreicheSpiele();

    LiveData<List<Spiel>> getNichtErfolgreicheSpiele();

}
