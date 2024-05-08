package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.util.List;

import de.htw.mbsnw_projekt.database.models.Punkt;

@Dao
public interface PunktDao {

    @Insert
    void insert(Punkt punkt);

    @Update
    void update(Punkt punkt);

    @Delete
    void delete(Punkt punkt);

    @Query("SELECT * FROM punkt WHERE spiel_id = :spiel_id")
    LiveData<List<Punkt>> getSpielPunkte(int spiel_id);

    @Query("SELECT * FROM punkt WHERE spiel_id = :spiel_id AND timestamp >= :start AND timestamp < :end")
    LiveData<List<Punkt>> getSpielPunkteZeitraum(int spiel_id, LocalDateTime start, LocalDateTime end);

    @Query("SELECT * FROM punkt ORDER BY timestamp DESC")
    LiveData<List<Punkt>> getPunkte();

}
