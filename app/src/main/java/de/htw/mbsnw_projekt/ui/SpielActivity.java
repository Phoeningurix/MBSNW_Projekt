package de.htw.mbsnw_projekt.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.logic.GameLogic;
import de.htw.mbsnw_projekt.logic.GeoTrackingService;
import de.htw.mbsnw_projekt.logic.MapPainter;
import de.htw.mbsnw_projekt.logic.MapPainterImpl;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;

public class SpielActivity extends AppCompatActivity {

    private static final String TAG = "SpielActivity";
    private GameLogic gameLogic;

    private TextView zielCounter;
    private TextView zielName;
    private SpielViewModel viewModel;

    private MapView map;

    TextView timer;

    Button nextZiel;

    MapPainter mapPainter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //für OSMDROID
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

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
        zielName = findViewById(R.id.ziel_name);
        nextZiel = findViewById(R.id.next_ziel);
        timer = findViewById(R.id.timer);

        nextZiel.setVisibility(View.GONE);

        map = (MapView) findViewById(R.id.map);
        mapPainter = new MapPainterImpl(map, this);

        /*mapPainter.punktHinzufuegen(new Punkt(52.761384, 13.234324, LocalDateTime.now().minusSeconds(20), viewModel.getAktuellesSpiel().getId()));
        mapPainter.punktHinzufuegen(new Punkt(52.700000, 13.280000, LocalDateTime.now().minusSeconds(5), viewModel.getAktuellesSpiel().getId()));
        mapPainter.punktHinzufuegen(new Punkt(52.760000, 13.123456, LocalDateTime.now(), viewModel.getAktuellesSpiel().getId()));
        */

        viewModel.getSpielPunkte().observe(this, punkte -> mapPainter.punkteSetzen(punkte));

        viewModel.getLatestPunkt().observe(this, punkt -> {
            if (punkt != null) {
                if (App.getGeoLogic().getEntfernung(viewModel.getAktuellesZielortObj(), punkt) <= viewModel.getMinAbstandZumZiel()) {
                    nextZiel.setVisibility(View.VISIBLE);
                } else {
                    nextZiel.setVisibility(View.GONE);
                }
            }
        });

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

        viewModel.getAktuellenZielort().observe(this, zielort -> {
            if (zielort != null) {
                zielName.setText(zielort.getName());
            } else {
                zielName.setText("Ziel");
                Log.d(TAG, "onCreate: getAktuellenZielort gibt null zurück.");
            }
        });

        nextZiel.setOnClickListener(this::onNextZielButtonClicked);

        viewModel.setUpCountDown(millisLeft -> timer.setText(App.getGameLogic().millisToString(millisLeft)));

        startTrackingService();

    }

    private void onNextZielButtonClicked(View view) {
        viewModel.finishAktuellesZiel();
        nextZiel.setVisibility(View.GONE);
    }

    private void startTrackingService() {
        Intent intent = new Intent(this.getApplicationContext(), GeoTrackingService.class);
        intent.setAction(GeoTrackingService.START_ACTION);

        Bundle bundle = new Bundle();
        bundle.putParcelable("aktuellesSpiel", viewModel.getAktuellesSpiel());
        intent.putExtra("spielBundle", bundle);

        startService(intent);
    }

    private void stopTrackingService() {
        Intent intent = new Intent(this.getApplicationContext(), GeoTrackingService.class);
        intent.setAction(GeoTrackingService.STOP_ACTION);
        startService(intent);
    }

}