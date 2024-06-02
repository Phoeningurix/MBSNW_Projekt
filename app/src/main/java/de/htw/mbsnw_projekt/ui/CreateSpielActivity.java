package de.htw.mbsnw_projekt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.view_models.CreateSpielViewModel;

public class CreateSpielActivity extends AppCompatActivity {

    // TODO: 02.06.2024 Spiel Einstellungen Input
    Button createSpielButton;
    CreateSpielViewModel viewModel;
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

        createSpielButton = findViewById(R.id.create_spiel);
        createSpielButton.setOnClickListener(this::onCreateSpielButtonClicked);

    }

    private void onCreateSpielButtonClicked(View view) {
        Spiel neuesSpiel = new Spiel(LocalDateTime.now(), null, 0, 3_600_000);
        viewModel.createSpiel(neuesSpiel, erstelltesSpiel -> {
            Intent intent = new Intent(CreateSpielActivity.this, SpielActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", neuesSpiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        });
    }
}