package de.htw.mbsnw_projekt.logic;

import java.util.List;

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
        // TODO: 02.06.2024 Nicht nur 1 returnen bitte :D
        return 1;
    }



}
