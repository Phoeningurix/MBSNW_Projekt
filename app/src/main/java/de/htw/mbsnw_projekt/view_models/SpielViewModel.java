package de.htw.mbsnw_projekt.view_models;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;
import de.htw.mbsnw_projekt.logic.MapPainterImpl;

public class SpielViewModel extends ViewModel {
    private static final String TAG = "SpielViewModel";

    public double minAbstandZumZiel = 20;

    private final Spiel aktuellesSpiel;
    private Ziel aktuellesZielObj;
    private Zielort aktuellesZielortObj;

    private Punkt aktuellesPunktObj;
    private final Repository repository;

    private final LiveData<List<Ziel>> spielZiele;
    private final LiveData<Ziel> aktuellesZiel;
    private final LiveData<Zielort> aktuellerZielort;

    private final LiveData<Punkt> latestPunkt;

    private final LiveData<List<Punkt>> spielPunkte;

    private  CountDownTimer countDownTimer;

    public SpielViewModel(Spiel aktuellesSpiel) {
        this.aktuellesSpiel = aktuellesSpiel;
        repository = App.getRepository();
        spielZiele = repository.getSpielZiele(aktuellesSpiel.getId());
        aktuellesZiel = repository.getAktuellesZiel(aktuellesSpiel.getId());
        aktuellerZielort = repository.getAktuellenZielort(aktuellesSpiel.getId());
        latestPunkt = repository.getLatestPunkt(aktuellesSpiel.getId());
        spielPunkte = repository.getSpielPunkte(aktuellesSpiel.getId());

        aktuellesZiel.observeForever(ziel -> aktuellesZielObj = ziel);
        aktuellerZielort.observeForever(zielort -> aktuellesZielortObj = zielort);
        latestPunkt.observeForever(punkt -> aktuellesPunktObj = punkt);
    }

    public double getMinAbstandZumZiel() {
        return minAbstandZumZiel;
    }

    public void setMinAbstandZumZiel(double minAbstandZumZiel) {
        this.minAbstandZumZiel = minAbstandZumZiel;
    }

    public Spiel getAktuellesSpiel() {
        return aktuellesSpiel;
    }

    public LiveData<List<Ziel>> getSpielZiele() {
        return spielZiele;
    }

    public LiveData<Ziel> getAktuellesZiel() {
        return aktuellesZiel;
    }

    public LiveData<Zielort> getAktuellenZielort() {
        return aktuellerZielort;
    }

    public LiveData<Punkt> getLatestPunkt() {
        return latestPunkt;
    }

    public Ziel getAktuellesZielObj() {
        return aktuellesZielObj;
    }

    public Punkt getAktuellesPunktObj() {
        return aktuellesPunktObj;
    }

    public LiveData<List<Punkt>> getSpielPunkte() {
        return spielPunkte;
    }

    public Zielort getAktuellesZielortObj() {
        return aktuellesZielortObj;
    }

    /**
     * Ziel erreichen
     */
    public void finishAktuellesZiel() {
        if (aktuellesZielObj != null) {
            Log.d(TAG, "finishAktuellesZiel: akutelles Ziel beendet");
            aktuellesZielObj.setTimestamp(LocalDateTime.now());
            repository.update(aktuellesZielObj);
        }
    }

    /**
     * Beendet aktuelles Spiel
     */
    public void spielBeenden() {
        Log.d(TAG, "spielBeenden: Aktuelles Spiel beenden");
        if (aktuellesSpiel != null) {
            App.getGameLogic().spielBeenden(aktuellesSpiel);
            countDownTimer.cancel();
            Log.d(TAG, "spielBeenden: Spiel beendet");
        }
    }

    /**
     * Spiel Countdown darstellen
     * @param updateTextView consumer
     */
    public void setUpCountDown(Consumer<Long> updateTextView) {

        // TODO: 02.06.2024 Countdown
        // https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android

        countDownTimer = new CountDownTimer(App.getGameLogic().getTimeLeft(aktuellesSpiel), 1000) {
            public void onTick(long millisUntilFinished) {
                updateTextView.accept(millisUntilFinished);
            }

            public void onFinish() {
                updateTextView.accept(0L);
                Log.d(TAG, "onFinish: Game ended");
            }

        }.start();
    }



}
