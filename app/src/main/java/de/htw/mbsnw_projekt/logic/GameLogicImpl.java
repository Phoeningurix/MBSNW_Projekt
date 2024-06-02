package de.htw.mbsnw_projekt.logic;

import java.util.Comparator;
import java.util.List;

import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;

public class GameLogicImpl implements GameLogic {

    private static volatile GameLogicImpl instance;

    public static GameLogicImpl getInstance() {
        if (instance == null) {
            synchronized (GameLogicImpl.class) {
                if (instance == null) {
                    instance = new GameLogicImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public int getCurrentZielIndex(List<Ziel> zielList) {
        zielList.sort(new Comparator<Ziel>() {
            @Override
            public int compare(Ziel z1, Ziel z2) {
                return Integer.compare(z1.getReihenfolge(), z2.getReihenfolge());
            }
        });

        for (int i = 0; i < zielList.size(); i++) {
            if (zielList.get(i).getTimestamp() == null) {
                return i;
            }
        }
        return -1;
    }

    // TODO: 02.06.2024 Implementierung
    @Override
    public long getTimeLeft(Spiel spiel) {
        return 0;
    }

    @Override
    public void spielBeenden(Spiel spiel) {

    }

    @Override
    public void setZielReached(Ziel ziel) {

    }


}
