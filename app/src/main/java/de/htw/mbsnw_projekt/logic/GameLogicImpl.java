package de.htw.mbsnw_projekt.logic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import de.htw.mbsnw_projekt.app.App;
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

    @Override
    public long getTimeLeft(Spiel spiel) {
        long spielDauer = spiel.getTimeLimit();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = spiel.getStartTimestamp();
        long diff = ChronoUnit.MILLIS.between(start, now);
        return spielDauer - diff;
    }

    // TODO: 02.06.2024 Implementierung
    @Override
    public void spielBeenden(Spiel spiel) {
        spiel.setEndTimestamp(LocalDateTime.now());
        App.getRepository().update(spiel);
    }

    @Override
    public void setZielReached(Ziel ziel) {

    }

    public String millisToString(long millis) {

        long zeit = millis / 1000;

        long sekunden = zeit % 60;
        long minuten = Math.floorDiv(zeit, 60L) % 60;
        long stunden = Math.floorDiv(zeit, 3600L);

        return String.format(Locale.GERMAN, "%02d", stunden) + ":" + String.format(Locale.GERMAN, "%02d", minuten) + ":" + String.format(Locale.GERMAN, "%02d", sekunden);
    }


}
