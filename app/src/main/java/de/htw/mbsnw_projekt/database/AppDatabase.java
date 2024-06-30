package de.htw.mbsnw_projekt.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.htw.mbsnw_projekt.database.daos.OrtListeDao;
import de.htw.mbsnw_projekt.database.daos.PunktDao;
import de.htw.mbsnw_projekt.database.daos.SpielDao;
import de.htw.mbsnw_projekt.database.daos.ZielDao;
import de.htw.mbsnw_projekt.database.daos.ZielortDao;
import de.htw.mbsnw_projekt.database.models.*;
import de.htw.mbsnw_projekt.database.type_converters.LocalDateTimeConverter;

@Database(entities = {Spiel.class, Punkt.class, OrtListe.class, Zielort.class, Ziel.class}, version = 2)
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
                            .addCallback(roomCallback)
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
            doInBackground(() -> populateDatabase(instance.zielortDao(), instance.ortListeDao()));
        }
    };

    private static void populateDatabase(ZielortDao zielortDao, OrtListeDao ortListeDao) {
        // TODO: 30.06.2024 Zielorte hinzufügen
        ortListeDao.insert(new OrtListe(false, "Default"));
        zielortDao.insert(new Zielort(52.533579, 13.417889, "Wasserturm", 1));
        zielortDao.insert(new Zielort(52.455100, 13.524743, "HTW Ufer", 1));
        zielortDao.insert(new Zielort(52.545587, 13.423519, "Käthe-Kollwitz-Gymnasium", 1));
        zielortDao.insert(new Zielort(52.543908, 13.424302, "Falafel Aladdin (Prenzlauer Berg)", 1));
    }

    private static void doInBackground(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public abstract SpielDao spielDao();

    public abstract PunktDao punktDao();

    public abstract ZielortDao zielortDao();

    public abstract ZielDao zielDao();

    public abstract OrtListeDao ortListeDao();

}
