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
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;

public class SpielViewModel extends ViewModel {
    private static final String TAG = "SpielViewModel";

    private final Spiel aktuellesSpiel;
    private Ziel aktuellesZielObj;
    private final Repository repository;

    private final LiveData<List<Ziel>> spielZiele;
    private final LiveData<Ziel> aktuellesZiel;
    private final LiveData<Zielort> aktuellerZielort;

    public SpielViewModel(Spiel aktuellesSpiel) {
        this.aktuellesSpiel = aktuellesSpiel;
        repository = App.getRepository();
        spielZiele = repository.getSpielZiele(aktuellesSpiel.getId());
        aktuellesZiel = repository.getAktuellesZiel(aktuellesSpiel.getId());
        aktuellerZielort = repository.getAktuellenZielort(aktuellesSpiel.getId());

        aktuellesZiel.observeForever(ziel -> aktuellesZielObj = ziel);
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

    public void finishAktuellesZiel() {
        if (aktuellesZielObj != null) {
            aktuellesZielObj.setTimestamp(LocalDateTime.now());
            repository.update(aktuellesZielObj);
        }

    }

    public void setUpCountDown(Consumer<Long> updateTextView) {

        // TODO: 02.06.2024 Countdown
        // https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android

        long spielDauer = aktuellesSpiel.getTimeLimit();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = aktuellesSpiel.getStartTimestamp();
        long diff = ChronoUnit.MILLIS.between(start, now);
        /*long millis = ZonedDateTime.of(localDateTime.minus(start), ZoneId.systemDefault()).toInstant().toEpochMilli();*/


        new CountDownTimer(spielDauer - diff, 1000) {

            public void onTick(long millisUntilFinished) {
                updateTextView.accept(millisUntilFinished);
            }

            public void onFinish() {
                updateTextView.accept(0L);
                Log.d(TAG, "onFinish: Game ended");
            }

        }.start();
    }

    public String millisToString(long millis) {

        long zeit = millis / 1000;

        long sekunden = zeit % 60;
        long minuten = Math.floorDiv(zeit, 60L) % 60;
        long stunden = Math.floorDiv(zeit, 3600L);

        return String.format(Locale.GERMAN, "%02d", stunden) + ":" + String.format(Locale.GERMAN, "%02d", minuten) + ":" + String.format(Locale.GERMAN, "%02d", sekunden);
    }

}
