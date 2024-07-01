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
        //zielortDao.insert(new Zielort(52.455100, 13.524743, "HTW Ufer", 1));
        zielortDao.insert(new Zielort(52.545587, 13.423519, "Käthe-Kollwitz-Gymnasium", 1));
        zielortDao.insert(new Zielort(52.543908, 13.424302, "Falafel Aladdin (Prenzlauer Berg)", 1));
        zielortDao.insert(new Zielort(52.538491, 13.413205, "Kulturbrauerei", 1));
        zielortDao.insert(new Zielort(52.543151, 13.427510, "Zeiss-Großplanetarium", 1));
        zielortDao.insert(new Zielort(52.536348, 13.417331, "Kollwitzplatz", 1));
        /*zielortDao.insert(new Zielort(52.521214, 13.413397, "Weltzeituhr", 1));
        zielortDao.insert(new Zielort(52.519782, 13.407215, "Neptunbrunnen", 1));
        zielortDao.insert(new Zielort(52.518899, 13.400410, "Berliner Dom", 1));
        zielortDao.insert(new Zielort(52.516455, 13.393937, "Bebelplatz", 1));
        zielortDao.insert(new Zielort(52.516294, 13.377942, "Brandenburger Tor", 1));
        zielortDao.insert(new Zielort(52.514564, 13.350604, "Siegessäule", 1));
        zielortDao.insert(new Zielort(52.514379, 13.392867, "Französischer Dom", 1));
        zielortDao.insert(new Zielort(52.516799, 13.406851, "Nikolaikirche", 1));
        zielortDao.insert(new Zielort(52.527983, 13.426485, "Märchenbrunnen", 1));
        zielortDao.insert(new Zielort(52.528001, 13.437026, "Friedrichshain", 1));
        zielortDao.insert(new Zielort(52.502635, 13.446189, "Oberbaumbrücke", 1));
        zielortDao.insert(new Zielort(52.542438, 13.403157, "Mauerpark", 1));
        zielortDao.insert(new Zielort(52.520247, 13.372694, "Bundestag", 1));
        zielortDao.insert(new Zielort(52.523649, 13.369821, "Hauptbahnhof", 1));
        zielortDao.insert(new Zielort(52.476208, 13.416417, "Tempelhofer Feld", 1));
        zielortDao.insert(new Zielort(52.497633, 13.371389, "Park am Gleisdreieck", 1));
        zielortDao.insert(new Zielort(52.504613, 13.335800, "Ku'Damm", 1));
        zielortDao.insert(new Zielort(52.504613, 13.335800, "Ku'Damm", 1));
        zielortDao.insert(new Zielort(52.504829, 13.279248, "Funkturm", 1));
        zielortDao.insert(new Zielort(52.421194, 13.175243, "Wannsee", 1));
        zielortDao.insert(new Zielort(52.520038, 13.295686, "Schloss Charlottenburg", 1));
        zielortDao.insert(new Zielort(52.539225, 13.212568, "Zitadelle Spandau", 1));
        zielortDao.insert(new Zielort(52.444206, 13.573872, "Schloss Köpenik", 1));
        zielortDao.insert(new Zielort(52.553037, 13.465142, "Weißer See", 1));
        zielortDao.insert(new Zielort(52.575748, 13.407418, "Schlosspark Pankow", 1));
        zielortDao.insert(new Zielort(52.518542, 13.388624, "Friedrichstraße", 1));*/
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
