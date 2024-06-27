package de.htw.mbsnw_projekt.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.logic.MapPainter;
import de.htw.mbsnw_projekt.logic.MapPainterImpl;
import de.htw.mbsnw_projekt.view_models.SpielInfoViewModel;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;

public class SpielInfoActivity extends AppCompatActivity {

    private static final String TAG = "SpielInfoActivity";

    private TextView spielZieleAnzahl;

    private TextView spielZiele;

    private TextView spielZeitlimit;

    private SpielInfoViewModel viewModel;

    private MapView map;

    private MapPainter mapPainter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //für OSMDROID
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_spiel_info);
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
        final Spiel ausgewaehltesSpiel = spiel;

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            /** @noinspection unchecked*/
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SpielInfoViewModel(ausgewaehltesSpiel);
            }
        }).get(SpielInfoViewModel.class);

        spielZieleAnzahl = findViewById(R.id.spiel_anzahl_ziele_text);
        spielZiele = findViewById(R.id.spiel_ziele_text);
        spielZeitlimit = findViewById(R.id.spiel_zeit_limit_text);

        map = findViewById(R.id.spiel_info_map);

        mapPainter = new MapPainterImpl(map, this);

        viewModel.getAlleSpielZiele().observe(this, ziele -> spielZieleAnzahl.setText("Anzahl Ziele: " + ziele.size()));
        spielZiele.setText("Ziele und Route: ");
        String zeitlimit = "Zeitlimit: " + ((spiel.getTimeLimit()/1000)/60)/60 + "h";
        spielZeitlimit.setText(zeitlimit);

        map.getController().setCenter(new GeoPoint(52.520553, 13.408770));

        // TODO: 28.06.2024 Route und Ziele anzeigen

    }
}