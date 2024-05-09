package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.OrtListe;

@Dao
public interface OrtListeDao {

    @Insert
    void insert(OrtListe ortListe);

    @Update
    void update(OrtListe ortListe);

    @Delete
    void delete(OrtListe ortListe);

    @Query("SELECT * FROM ort_liste ORDER BY name")
    LiveData<List<OrtListe>> getAllOrtslisten();

}
