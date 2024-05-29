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

    void getSpiel(int spielId, Consumer<Spiel> task);


    //------------------------------------PUNKT------------------------------------

    void insert(Punkt punkt);

    void update(Punkt punkt);

    void delete(Punkt punkt);

    LiveData<List<Punkt>> getSpielPunkte(int spiel_id);

    LiveData<Punkt> getLatestPunkt(int spiel_id);

    LiveData<List<Punkt>> getSpielPunkteZeitraum(int spiel_id, LocalDateTime start, LocalDateTime end);

    LiveData<List<Punkt>> getPunkte();

    //------------------------------------ZIELORT------------------------------------

    void insert(Zielort zielort);

    void update(Zielort zielort);

    void delete(Zielort zielort);

    LiveData<List<Zielort>> getAlleZielorte();

    void getZielort(int zielortId, Consumer<Zielort> task);


    //------------------------------------ZIEL------------------------------------

    void insert(Ziel ziel);

    void update(Ziel ziel);

    void delete(Ziel ziel);

    LiveData<List<Ziel>> getSpielZiele(int spielId);

    LiveData<List<Ziel>> getErreichteSpielZiel(int spielId);

    LiveData<List<Ziel>> getNichtErreichteSpielZiele(int spielId);

    default void getSpiel(Ziel ziel, Consumer<Spiel> task) {
        getSpiel(ziel.getSpielId(), task);
    }

    default void getZielort(Ziel ziel, Consumer<Zielort> task) {
        getZielort(ziel.getZielortId(), task);
    }


    //------------------------------------ORTLISTE------------------------------------

    void insert(OrtListe ortListe);

    void update(OrtListe ortListe);

    void delete(OrtListe ortListe);

    LiveData<List<OrtListe>> getAllOrtslisten();

    LiveData<List<Zielort>> getAlleZielorte(int ortlisteId);

    void getOrtListe(int zielortId, Consumer<OrtListe> task);


}
