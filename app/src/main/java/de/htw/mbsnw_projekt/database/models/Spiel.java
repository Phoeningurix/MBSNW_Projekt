package de.htw.mbsnw_projekt.database.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(
        tableName = "spiel",
        indices = @Index(value = "spiel_id")
)
public class Spiel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "spiel_id")
    private int id;

    @ColumnInfo(name = "start_timestamp")
    private LocalDateTime startTimestamp;

    @Nullable
    @ColumnInfo(name = "end_timestamp")
    private LocalDateTime endTimestamp;

    private long seed;

    @ColumnInfo(name = "time_limit")
    private long timeLimit;

    public Spiel(LocalDateTime startTimestamp, LocalDateTime endTimestamp, long seed, long timeLimit) {
        setStartTimestamp(startTimestamp);
        setEndTimestamp(endTimestamp);
        setSeed(seed);
        setTimeLimit(timeLimit);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    private void setStartTimestamp(LocalDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Nullable
    public LocalDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(@Nullable LocalDateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public long getSeed() {
        return seed;
    }

    private void setSeed(long seed) {
        this.seed = seed;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    private void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    @Ignore
    public String toString() {
        return "Spiel{" +
                "id=" + id +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", seed=" + seed +
                ", timeLimit=" + timeLimit +
                '}';
    }
}
