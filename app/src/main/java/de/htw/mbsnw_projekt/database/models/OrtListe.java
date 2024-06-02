package de.htw.mbsnw_projekt.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ort_liste",
        indices = @Index(value = "ort_liste_id")
)
public class OrtListe {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ort_liste_id")
    private int id;

    private boolean custom;

    private String name;

    public OrtListe(boolean custom, String name) {
        this.name = name;
        setCustom(custom);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCustom() {
        return custom;
    }

    private void setCustom(boolean custom) {
        this.custom = custom;
    }

    @Override
    public String toString() {
        return "OrtListe{" +
                "id=" + id +
                ", name=" + name +
                ", custom=" + custom +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
