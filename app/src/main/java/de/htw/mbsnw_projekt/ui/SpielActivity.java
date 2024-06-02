package de.htw.mbsnw_projekt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.logic.GameLogic;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;

public class SpielActivity extends AppCompatActivity {

    private static final String TAG = "SpielActivity";
    private GameLogic gameLogic;

    TextView zielCounter;
    SpielViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spiel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("spielBundle");
        Spiel spiel = null;
        if (bundle != null) {
            spiel = bundle.getParcelable("aktuellesSpiel");
        }
        final Spiel aktuellesSpiel = spiel;

        gameLogic = App.getGameLogic();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            /** @noinspection unchecked*/
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SpielViewModel(aktuellesSpiel);
            }
        }).get(SpielViewModel.class);

        zielCounter = findViewById(R.id.ziel_counter);

        Toast.makeText(this, "Aktuelles Spiel: " + aktuellesSpiel, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: Aktuelles Spiel: " + aktuellesSpiel);

        App.getRepository().getSpiele().observeForever(spiele -> {
            Log.d(TAG, "alleSpiele: " + spiele.size());
            for (Spiel s : spiele) {
                Log.d(TAG, "onCreate: spiel:" + s);
            }

        });

        viewModel.getSpielZiele().observe(this, list -> {
            String text = (gameLogic.getCurrentZielIndex(list) + 1) + " / " + list.size();
            zielCounter.setText(text);
        });

    }
}