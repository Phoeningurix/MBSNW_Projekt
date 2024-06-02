package de.htw.mbsnw_projekt.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.database.type_converters.LocalDateTimeConverter;

@Entity(
        tableName = "spiel",
        indices = @Index(value = "spiel_id")
)
public class Spiel implements Parcelable {

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

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(LocalDateTimeConverter.localDateTimeToString(startTimestamp));
        out.writeString(LocalDateTimeConverter.localDateTimeToString(endTimestamp));
        out.writeLong(seed);
        out.writeLong(timeLimit);
    }

    private Spiel(Parcel in) {
        id = in.readInt();
        startTimestamp = LocalDateTimeConverter.localDateTimeFromString(in.readString());
        endTimestamp = LocalDateTimeConverter.localDateTimeFromString(in.readString());
        seed = in.readLong();
        timeLimit = in.readLong();
    }

    public static final Creator<Spiel> CREATOR = new Creator<Spiel>() {
        @Override
        public Spiel createFromParcel(Parcel in) {
            return new Spiel(in);
        }

        @Override
        public Spiel[] newArray(int size) {
            return new Spiel[size];
        }
    };
}
