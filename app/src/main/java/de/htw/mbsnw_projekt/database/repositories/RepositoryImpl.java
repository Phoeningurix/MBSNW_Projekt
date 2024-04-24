package de.htw.mbsnw_projekt.database.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.daos.SpielDao;
import de.htw.mbsnw_projekt.database.models.Spiel;

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

    private final SpielDao spielDao;

    private final LiveData<List<Spiel>> spiele;
    private final LiveData<List<Spiel>> erfolgreicheSpiele;
    private final LiveData<List<Spiel>> nichtErfolgreicheSpiele;

    private RepositoryImpl() {
        spielDao = App.getDatabase().spielDao();
        // TODO: 24.04.2024 Andere Daos hinzufügen

        spiele = spielDao.getSpiele();
        erfolgreicheSpiele = spielDao.getErfolgreicheSpiele();
        nichtErfolgreicheSpiele = getNichtErfolgreicheSpiele();
    }


    @Override
    public void insert(Spiel spiel) {
        doInBackground(() -> spielDao.insert(spiel));
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
        doInBackground(() -> {
            Spiel spiel = spielDao.getAktuellesSpiel();
            task.accept(spiel);
        });
    }

    @Override
    public LiveData<List<Spiel>> getErfolgreicheSpiele() {
        return erfolgreicheSpiele;
    }

    @Override
    public LiveData<List<Spiel>> getNichtErfolgreicheSpiele() {
        return nichtErfolgreicheSpiele;
    }
}
