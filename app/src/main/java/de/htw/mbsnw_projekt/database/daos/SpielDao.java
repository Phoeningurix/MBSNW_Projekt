package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Spiel;

@Dao
public interface SpielDao {

    @Insert
    void insert(Spiel spiel);

    @Update
    void update(Spiel spiel);

    @Delete
    void delete(Spiel spiel);

    @Query("SELECT * FROM spiel ORDER BY start_timestamp DESC")
    LiveData<List<Spiel>> getSpiele();

    @Query("SELECT * FROM spiel WHERE end_timestamp IS NULL")
    Spiel getAktuellesSpiel();

    @Query("SELECT * FROM spiel WHERE spiel_id NOT IN (SELECT spiel_id FROM ziel WHERE timestamp IS NULL) AND end_timestamp IS NOT NULL ORDER BY start_timestamp DESC")
    LiveData<List<Spiel>> getErfolgreicheSpiele();

    @Query("SELECT * FROM spiel WHERE spiel_id IN (SELECT spiel_id FROM ziel WHERE timestamp IS NULL) AND end_timestamp IS NOT NULL ORDER BY start_timestamp DESC")
    LiveData<List<Spiel>> getNichtErfolgreicheSpiele();

    @Query("SELECT * FROM spiel WHERE spiel_id = :spielId")
    Spiel getSpiel(int spielId);

}
