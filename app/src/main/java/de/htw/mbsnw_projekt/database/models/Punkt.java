package de.htw.mbsnw_projekt.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(
        tableName = "punkt",
        indices = @Index(value = "punkt_id"),

        foreignKeys = @ForeignKey(
                entity = Spiel.class,
                parentColumns = "spiel_id",
                childColumns = "spiel_id",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.SET_NULL
        )
)
public class Punkt {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "punkt_id")
    private int id;

    private double latitude;

    private double longitude;

    private LocalDateTime timestamp;

    @ColumnInfo(name = "spiel_id")
    private int spielId;

    public Punkt(double latitude, double longitude, LocalDateTime timestamp, int spielId) {
        setLatitude(latitude);
        setLongitude(longitude);
        setTimestamp(timestamp);
        setSpielId(spielId);
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

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getSpielId() {
        return spielId;
    }

    private void setSpielId(int spielId) {
        this.spielId = spielId;
    }
}
