package de.htw.mbsnw_projekt.database.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(
        tableName = "ziel",
        indices = @Index(value = {"zielort_id", "spiel_id"}),
        primaryKeys = {"zielort_id", "spiel_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Zielort.class,
                        parentColumns = "zielort_id",
                        childColumns = "zielort_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.SET_NULL
                ),
                @ForeignKey(
                        entity = Spiel.class,
                        parentColumns = "spiel_id",
                        childColumns = "spiel_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.SET_NULL
                )
        }
)
public class Ziel {

    @ColumnInfo(name = "zielort_id")
    private int zielortId;

    @ColumnInfo(name = "spiel_id")
    private int spielId;

    private int reihenfolge;

    @Nullable
    private LocalDateTime timestamp;

    public Ziel(int zielortId, int spielId, int reihenfolge, @Nullable LocalDateTime timestamp) {
        this.zielortId = zielortId;
        this.spielId = spielId;
        this.reihenfolge = reihenfolge;
        this.timestamp = timestamp;
    }


    public int getZielortId() {
        return zielortId;
    }

    public void setZielortId(int zielortId) {
        this.zielortId = zielortId;
    }

    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielId) {
        this.spielId = spielId;
    }

    public int getReihenfolge() {
        return reihenfolge;
    }

    private void setReihenfolge(int reihenfolge) {
        this.reihenfolge = reihenfolge;
    }

    @Nullable
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@Nullable LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
