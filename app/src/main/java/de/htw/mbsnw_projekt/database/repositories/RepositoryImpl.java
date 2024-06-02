package de.htw.mbsnw_projekt.database.repositories;

import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.daos.OrtListeDao;
import de.htw.mbsnw_projekt.database.daos.PunktDao;
import de.htw.mbsnw_projekt.database.daos.SpielDao;
import de.htw.mbsnw_projekt.database.daos.ZielDao;
import de.htw.mbsnw_projekt.database.daos.ZielortDao;
import de.htw.mbsnw_projekt.database.models.OrtListe;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

public class RepositoryImpl extends AbstractRepository {

    private static volatile RepositoryImpl instance;

    public static RepositoryImpl getInstance() {
        if (instance == null) {
            synchronized (RepositoryImpl.class) {
                if (instance == null) {
                    instance = new RepositoryImpl();
                }
            }
        }
        return instance;
    }

    //------------------------------------DAOS------------------------------------
    private final SpielDao spielDao;
    private final PunktDao punktDao;
    private final ZielortDao zielortDao;
    private final ZielDao zielDao;

    private final OrtListeDao ortListeDao;


    //------------------------------------LIVE-DATA------------------------------------
    private final LiveData<List<Spiel>> spiele;
    private final LiveData<List<Spiel>> erfolgreicheSpiele;
    private final LiveData<List<Spiel>> nichtErfolgreicheSpiele;
    private final LiveData<List<Punkt>> punkte;
    private final LiveData<List<Zielort>> zielorte;
    private final LiveData<List<OrtListe>> alleOrtlisten;


    private RepositoryImpl() {
        spielDao = App.getDatabase().spielDao();
        punktDao = App.getDatabase().punktDao();
        zielortDao = App.getDatabase().zielortDao();
        zielDao = App.getDatabase().zielDao();
        ortListeDao = App.getDatabase().ortListeDao();

        spiele = spielDao.getSpiele();
        erfolgreicheSpiele = spielDao.getErfolgreicheSpiele();
        nichtErfolgreicheSpiele = spielDao.getNichtErfolgreicheSpiele();
        punkte = punktDao.getPunkte();
        zielorte = zielortDao.getAlleZielorte();
        alleOrtlisten = ortListeDao.getAllOrtslisten();

    }

    //------------------------------------SPIEL------------------------------------

    @Override
    public void insert(Spiel spiel) {
        doInBackground(() -> spielDao.insert(spiel));
    }

    @Override
    public void insert(Spiel spiel, Runnable after) {
        doInBackground(
                () -> spielDao.insert(spiel),
                after
        );
    }

    @Override
    public void update(Spiel spiel) {
        doInBackground(() -> spielDao.update(spiel));
    }

    @Override
    public void delete(Spiel spiel) {
        doInBackground(() -> spielDao.delete(spiel));
    }

    @Override
    public LiveData<List<Spiel>> getSpiele() {
        return spiele;
    }

    @Override
    public void getAktuellesSpiel(Consumer<Spiel> task) {
        doInBackground(() -> spielDao.getAktuellesSpiel(), task);
    }

    @Override
    public LiveData<List<Spiel>> getErfolgreicheSpiele() {
        return erfolgreicheSpiele;
    }

    @Override
    public LiveData<List<Spiel>> getNichtErfolgreicheSpiele() {
        return nichtErfolgreicheSpiele;
    }

    @Override
    public void getSpiel(int spielId, Consumer<Spiel> task) {
        doInBackground(() -> spielDao.getSpiel(spielId), task);
    }

    //------------------------------------PUNKT------------------------------------

    @Override
    public void insert(Punkt punkt) {
        doInBackground(() -> punktDao.insert(punkt));
    }

    @Override
    public void update(Punkt punkt) {
        doInBackground(() -> punktDao.update(punkt));
    }

    @Override
    public void delete(Punkt punkt) {
        doInBackground(() -> punktDao.delete(punkt));
    }

    @Override
    public LiveData<List<Punkt>> getSpielPunkte(int spiel_id) {
        return punktDao.getSpielPunkte(spiel_id);
    }

    @Override
    public LiveData<Punkt> getLatestPunkt(int spiel_id) {
        return punktDao.getLatestPunkt(spiel_id);
    }

    @Override
    public LiveData<List<Punkt>> getSpielPunkteZeitraum(int spiel_id, LocalDateTime start, LocalDateTime end) {
        return punktDao.getSpielPunkteZeitraum(spiel_id, start, end);
    }

    @Override
    public LiveData<List<Punkt>> getPunkte() {
        return punkte;
    }

    //------------------------------------ZIELORT------------------------------------

    @Override
    public void insert(Zielort zielort) {
        doInBackground(() -> zielortDao.insert(zielort));
    }

    @Override
    public void update(Zielort zielort) {
        doInBackground(() -> zielortDao.update(zielort));
    }

    @Override
    public void delete(Zielort zielort) {
        doInBackground(() -> zielortDao.delete(zielort));
    }

    @Override
    public LiveData<List<Zielort>> getAlleZielorte() {
        return zielorte;
    }

    @Override
    public void getZielort(int zielortId, Consumer<Zielort> task) {
        doInBackground(() -> zielortDao.getZielort(zielortId), task);
    }

    //------------------------------------ZIEL------------------------------------

    @Override
    public void insert(Ziel ziel) {
        doInBackground(() -> zielDao.insert(ziel));
    }

    @Override
    public void update(Ziel ziel) {
        doInBackground(() -> zielDao.update(ziel));
    }

    @Override
    public void delete(Ziel ziel) {
        doInBackground(() -> zielDao.delete(ziel));
    }

    @Override
    public LiveData<List<Ziel>> getSpielZiele(int spielId) {
        return zielDao.getSpielZiele(spielId);
    }

    @Override
    public LiveData<List<Ziel>> getErreichteSpielZiel(int spielId) {
        return zielDao.getErreichteSpielZiel(spielId);
    }

    @Override
    public LiveData<List<Ziel>> getNichtErreichteSpielZiele(int spielId) {
        return zielDao.getNichtErreichteSpielZiele(spielId);
    }

    //------------------------------------Ortliste------------------------------------

    @Override
    public void insert(OrtListe ortListe) {
        doInBackground(() -> ortListeDao.insert(ortListe));
    }

    @Override
    public void update(OrtListe ortListe) {
        doInBackground(() -> ortListeDao.update(ortListe));
    }

    @Override
    public void delete(OrtListe ortListe) {
        doInBackground(() -> ortListeDao.delete(ortListe));
    }

    @Override
    public LiveData<List<OrtListe>> getAllOrtslisten() {
        return alleOrtlisten;
    }

    @Override
    public LiveData<List<Zielort>> getAlleZielorte(int ortlisteId) {
        return ortListeDao.getAlleZielorte(ortlisteId);
    }

    @Override
    public void getOrtListe(int zielortId, Consumer<OrtListe> task) {
        doInBackground(() -> ortListeDao.getOrtListe(zielortId), task);
    }
}
