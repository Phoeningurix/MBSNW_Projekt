package de.htw.mbsnw_projekt.database.repositories;

import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import de.htw.mbsnw_projekt.database.models.*;

public interface Repository {

    //------------------------------------SPIEL------------------------------------

    void insert(Spiel spiel);

    void update(Spiel spiel);

    void delete(Spiel spiel);

    LiveData<List<Spiel>> getSpiele();

    void getAktuellesSpiel(Consumer<Spiel> task);

    LiveData<List<Spiel>> getErfolgreicheSpiele();

    LiveData<List<Spiel>> getNichtErfolgreicheSpiele();

    //------------------------------------PUNKT------------------------------------

    void insert(Punkt punkt);

    void update(Punkt punkt);

    void delete(Punkt punkt);

    LiveData<List<Punkt>> getSpielPunkte(int spiel_id);

    LiveData<List<Punkt>> getSpielPunkteZeitraum(int spiel_id, LocalDateTime start, LocalDateTime end);

    LiveData<List<Punkt>> getPunkte();



}
