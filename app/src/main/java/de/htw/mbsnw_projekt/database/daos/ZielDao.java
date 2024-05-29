package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.Ziel;


@Dao
public interface ZielDao {

    @Insert
    void insert(Ziel ziel);

    @Update
    void update(Ziel ziel);

    @Delete
    void delete(Ziel ziel);

    @Query("SELECT * FROM ziel WHERE spiel_id = :spielId ORDER BY reihenfolge ASC")
    LiveData<List<Ziel>> getSpielZiele(int spielId);

    @Query("SELECT * FROM ziel WHERE spiel_id = :spielId AND timestamp IS NOT NULL ORDER BY reihenfolge ASC")
    LiveData<List<Ziel>> getErreichteSpielZiel(int spielId);

    @Query("SELECT * FROM ziel WHERE spiel_id = :spielId AND timestamp IS NULL ORDER BY reihenfolge ASC")
    LiveData<List<Ziel>> getNichtErreichteSpielZiele(int spielId);


}
