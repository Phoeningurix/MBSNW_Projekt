package de.htw.mbsnw_projekt.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "zielort",
        indices = @Index(value = "zielort_id"),
        foreignKeys = @ForeignKey(
                entity = OrtListe.class,
                parentColumns = "ort_liste_id",
                childColumns = "ort_liste_id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.SET_NULL
        )
)
public class Zielort {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "zielort_id")
    private int id;

    private double latitude;

    private double longitude;

    private String name;

    @ColumnInfo(name = "ort_liste_id")
    private int ortListeId;

    public Zielort(double latitude, double longitude, String name, int ortListeId) {
        setLatitude(latitude);
        setLongitude(longitude);
        setName(name);
        setOrtListeId(ortListeId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrtListeId() {
        return ortListeId;
    }

    public void setOrtListeId(int ortListeId) {
        this.ortListeId = ortListeId;
    }

    @Override
    public String toString() {
        return "Zielort{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name= " + name +
                ", ortListeId=" + ortListeId +
                '}';
    }
}
