package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

@Dao
public interface ZielortDao {

    @Insert
    void insert(Zielort zielort);

    @Update
    void update(Zielort zielort);

    @Delete
    void delete(Zielort zielort);

    @Query("SELECT * FROM zielort ORDER BY name ASC")
    LiveData<List<Zielort>> getAlleZielorte();

    @Query("SELECT * FROM zielort WHERE zielort_id = :zielortId")
    Zielort getZielort(int zielortId);

    @Query("SELECT zielort.* FROM zielort, ziel  WHERE ziel.zielort_id = zielort.zielort_id AND ziel.spiel_id = :spielId AND ziel.timestamp IS NULL ORDER BY ziel.reihenfolge ASC LIMIT 1")
    LiveData<Zielort> getAktuellenZielort(int spielId);

    @Query("SELECT zielort.* FROM zielort, ziel  WHERE ziel.zielort_id = zielort.zielort_id AND ziel.spiel_id = :spielId ORDER BY ziel.reihenfolge ASC")
    LiveData<List<Zielort>> getAlleSpielZielorte(int spielId);

}
