package de.htw.mbsnw_projekt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.Random;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;
import de.htw.mbsnw_projekt.view_models.CreateSpielViewModel;

public class CreateSpielActivity extends AppCompatActivity {
    private static final String TAG = "CreateSpielActivity";

    // TODO: 02.06.2024 Spiel Einstellungen Input
    private Button createSpielButton;

    private SeekBar timeLimitSeekBar;

    private TextView timeLimitText;

    private SeekBar zielAnzahlSeekBar;

    private TextView zielAnzahlText;

    private CreateSpielViewModel viewModel;

    private Repository repository;

    private int anzahlZiele = 3;

    private int ortlisteID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_spiel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(CreateSpielViewModel.class);
        repository = App.getRepository();

        createSpielButton = findViewById(R.id.create_spiel);
        createSpielButton.setOnClickListener(this::onCreateSpielButtonClicked);

        timeLimitSeekBar = findViewById(R.id.time_limit_seek_bar);
        timeLimitSeekBar.setMax(24);
        timeLimitSeekBar.setProgress(3);

        timeLimitText = findViewById(R.id.time_limit_text);
        timeLimitText.setText("Time limit: " + timeLimitSeekBar.getProgress() + " h");
        timeLimitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeLimitText.setText("Time limit: " + progress + " h");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        zielAnzahlSeekBar = findViewById(R.id.ziel_number_seek_bar);
        zielAnzahlSeekBar.setMax(20);
        zielAnzahlSeekBar.setProgress(5);

        zielAnzahlText = findViewById(R.id.ziel_number_text);
        zielAnzahlText.setText("Anzahl Ziele: " + zielAnzahlSeekBar.getProgress());

        zielAnzahlSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zielAnzahlText.setText("Anzahl Ziele: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Toolbar toolbar = findViewById(R.id.create_spiel_toolbar);
        setSupportActionBar(toolbar);
        
    }

    private void onCreateSpielButtonClicked(View view) {
        // TODO: 02.06.2024 read setting from textfields
        Spiel neuesSpiel = new Spiel(LocalDateTime.now(), null, 0, timeLimitSeekBar.getProgress()*1000*60*60);
        viewModel.createSpiel(neuesSpiel, erstelltesSpiel -> {
            zieleErstellen(erstelltesSpiel, zielAnzahlSeekBar.getProgress(), ortlisteID);

            Log.d(TAG, "onCreateSpielButtonClicked: Erstelltes Spiel: " + erstelltesSpiel);
            Intent intent = new Intent(CreateSpielActivity.this, SpielActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", erstelltesSpiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        });
    }

    private void zieleErstellen(Spiel erstelltesSpiel, int anzahl, int ortliste) {
        // TODO: 02.06.2024 Ziele aus spezifischen Ortlisten
        repository.getAlleZielorte(ortliste).observe(this, zielorte -> {
            Random r = new Random();
            Log.d(TAG, "zieleErstellen: Erstelle " + anzahl + " Ziele aus Liste mit " + zielorte.size() + " Zielen");
            for (int i = 0; i < anzahl; i++) {
                if (zielorte.isEmpty()) {
                    Log.w(TAG, "zieleErstellen: Zu wenige Zielorte!");
                    break;
                }
                Zielort zielort = zielorte.get(r.nextInt(zielorte.size()));
                zielorte.remove(zielort);
                repository.insert(new Ziel(zielort.getId(), erstelltesSpiel.getId(), i, null));
            }
        });
    }

}