package de.htw.mbsnw_projekt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

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
    private CreateSpielViewModel viewModel;

    private Repository repository;

    private int anzahlZiele = 3;


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

    }

    private void onCreateSpielButtonClicked(View view) {
        // TODO: 02.06.2024 read setting from textfields
        Spiel neuesSpiel = new Spiel(LocalDateTime.now(), null, 0, 3_600_000);
        viewModel.createSpiel(neuesSpiel, erstelltesSpiel -> {
            zieleErstellen(erstelltesSpiel, anzahlZiele);

            Log.d(TAG, "onCreateSpielButtonClicked: Erstelltes Spiel: " + erstelltesSpiel);
            Intent intent = new Intent(CreateSpielActivity.this, SpielActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", erstelltesSpiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        });
    }

    private void zieleErstellen(Spiel erstelltesSpiel, int anzahl) {
        // TODO: 02.06.2024 Ziele aus spezifischen Ortlisten
        repository.getAlleZielorte().observe(this, zielorte -> {
            Random r = new Random();
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