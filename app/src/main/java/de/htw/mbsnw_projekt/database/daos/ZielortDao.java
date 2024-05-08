package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

}
