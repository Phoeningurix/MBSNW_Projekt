package de.htw.mbsnw_projekt.database.daos;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.List;

import de.htw.mbsnw_projekt.database.AppDatabase;
import de.htw.mbsnw_projekt.database.RoomDBTesting;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;

@RunWith(AndroidJUnit4.class)
public class PunktDaoTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private AppDatabase db;
    private PunktDao dao;

    @Before
    public void setUp() {
        db = RoomDBTesting.setUpMemoryDB(InstrumentationRegistry.getInstrumentation().getTargetContext());
        dao = db.punktDao();
    }

    @Test
    public void testInsertDelete() throws InterruptedException {
        System.out.println("Testing PunktDao");
        Spiel spiel = new Spiel(LocalDateTime.now().minusHours(1), null, 123, 4800_000);
        db.spielDao().insert(spiel);
        Spiel actualSpiel = RoomDBTesting.getOrAwaitValue(db.spielDao().getSpiele()).get(0);
        System.out.println("Spiel ID: " + spiel.getId());
        System.out.println("Spiel ID after insert: " + actualSpiel.getId());

        Punkt p = new Punkt(53, 23, LocalDateTime.now(), actualSpiel.getId());
        dao.insert(p);

        LiveData<List<Punkt>> liveDataList = dao.getPunkte();
        List<Punkt> result = RoomDBTesting.getOrAwaitValue(liveDataList);

        assertEquals(1, result.size());
        assertEquals(p, result.get(0));
    }

}
