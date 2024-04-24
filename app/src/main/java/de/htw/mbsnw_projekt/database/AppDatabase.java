package de.htw.mbsnw_projekt.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.htw.mbsnw_projekt.database.daos.SpielDao;
import de.htw.mbsnw_projekt.database.models.*;
import de.htw.mbsnw_projekt.database.type_converters.LocalDateTimeConverter;

@Database(entities = {Spiel.class, Punkt.class, OrtListe.class, Zielort.class, Ziel.class}, version = 1)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(roomCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //doInBackground(() -> populateDatabase(instance.noteDao()));
        }
    };

    /*private static void populateDatabase(NoteDao noteDao) {
        noteDao.insert(new Note("Title 1", "Description 1", 1));
        noteDao.insert(new Note("Title 2", "Description 2", 2));
        noteDao.insert(new Note("Title 3", "Description 3", 3));
    }*/

    private static void doInBackground(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public abstract SpielDao spielDao();

}
