package de.htw.mbsnw_projekt.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.htw.mbsnw_projekt.database.models.OrtListe;
import de.htw.mbsnw_projekt.database.models.Zielort;

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

    @Query("SELECT * FROM zielort WHERE ort_liste_id = :ortlisteId ORDER BY name")
    LiveData<List<Zielort>> getAlleZielorte(int ortlisteId);

    @Query("SELECT ort_liste.* FROM ort_liste JOIN zielort ON zielort.ort_liste_id = ort_liste.ort_liste_id WHERE zielort.zielort_id = :zielortId")
    OrtListe getOrtListe(int zielortId);

}
