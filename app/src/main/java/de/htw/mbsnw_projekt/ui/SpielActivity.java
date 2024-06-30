package de.htw.mbsnw_projekt.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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
import org.osmdroid.views.overlay.Marker;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.logic.GameLogic;
import de.htw.mbsnw_projekt.logic.GeoTrackingService;
import de.htw.mbsnw_projekt.logic.MapPainter;
import de.htw.mbsnw_projekt.logic.MapPainterImpl;
import de.htw.mbsnw_projekt.ui.navigation_drawer.MainMenuActivity;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;

public class SpielActivity extends AppCompatActivity {

    private static final String TAG = "SpielActivity";
    private GameLogic gameLogic;

    private TextView zielCounter;
    private TextView zielName;
    private SpielViewModel viewModel;

    private MapView map;

    private TextView timer;

    private Button nextZiel;

    private Button focusOnPlayer;

    private MapPainter mapPainter;

    private Button returnButton;

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

        //Set-Up
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
        focusOnPlayer = findViewById(R.id.center_on_player);
        returnButton = findViewById(R.id.return_button);

        nextZiel.setVisibility(View.GONE);
        returnButton.setVisibility(View.GONE);

        map = findViewById(R.id.map);
        mapPainter = new MapPainterImpl(map, this);

        /*mapPainter.punktHinzufuegen(new Punkt(52.761384, 13.234324, LocalDateTime.now().minusSeconds(20), viewModel.getAktuellesSpiel().getId()));
        mapPainter.punktHinzufuegen(new Punkt(52.700000, 13.280000, LocalDateTime.now().minusSeconds(5), viewModel.getAktuellesSpiel().getId()));
        mapPainter.punktHinzufuegen(new Punkt(52.760000, 13.123456, LocalDateTime.now(), viewModel.getAktuellesSpiel().getId()));
        */

        //Spiele Ausgeben

        Toast.makeText(this, "Aktuelles Spiel: " + aktuellesSpiel, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: Aktuelles Spiel: " + aktuellesSpiel);

        App.getRepository().getSpiele().observeForever(spiele -> {
            Log.d(TAG, "alleSpiele: " + spiele.size());
            for (Spiel s : spiele) {
                Log.d(TAG, "onCreate: spiel:" + s);
            }

        });

        //Text setzen (und Karte updaten)
        viewModel.getSpielZiele().observe(this, list -> {
            String text = (gameLogic.getCurrentZielIndex(list) + 1) + " / " + list.size();
            zielCounter.setText(text);
        });

        viewModel.getAktuellenZielort().observe(this, zielort -> {
            if (zielort != null) {
                zielName.setText(zielort.getName());
                mapPainter.zielHinzufuegen(getApplicationContext(), zielort);
            } else {
                zielName.setText("Ziel");
                Log.d(TAG, "onCreate: getAktuellenZielort gibt null zurück.");
            }
        });

        viewModel.getNichterreichteZiele().observe(this, ziele -> {
            if (ziele != null && ziele.isEmpty()) {
                Log.d(TAG, "onCreate: Alle Ziele erreicht");
                viewModel.spielBeenden();
                stopTrackingService();
            }
        });

        viewModel.getSpielPunkte().observe(this, punkte -> mapPainter.punkteSetzen(punkte));

        Marker playerMarker = new Marker(map);
        Drawable playerIcon = ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.marker_player, null);
        playerIcon = mapPainter.rescaleDrawable(playerIcon, 60, 60);
        playerMarker.setIcon(playerIcon);
        playerMarker.setAlpha(0.75f);
        playerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);


        viewModel.getLatestPunkt().observe(this, punkt -> {
            if (punkt != null && viewModel.getAktuellesZielortObj() != null) {
                playerMarker.setPosition(punkt.toGeopoint());
                map.getOverlays().add(playerMarker);
                if (App.getGeoLogic().getEntfernung(viewModel.getAktuellesZielortObj(), punkt) <= viewModel.getMinAbstandZumZiel()) {
                    nextZiel.setVisibility(View.VISIBLE);
                } else {
                    nextZiel.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getSpielLiveData().observe(this, spiel1 -> {
            if (spiel1.getEndTimestamp() != null) {
                returnButton.setVisibility(Button.VISIBLE);
                nextZiel.setVisibility(Button.INVISIBLE);
            } else {
                returnButton.setVisibility(Button.GONE);
            }
        });

        //Countdown
        viewModel.setUpCountDown(millisLeft -> timer.setText(App.getGameLogic().millisToString(millisLeft)));

        //Tracking starten
        startTrackingService();

        //Buttons
        focusOnPlayer.setOnClickListener(this::onFocusOnPlayerButtonClicked);
        nextZiel.setOnClickListener(this::onNextZielButtonClicked);
        returnButton.setOnClickListener(this::returnToHomeMenu);

    }

    /**
     * Ziel erreichen
     *
     * @param view view
     */
    private void onNextZielButtonClicked(View view) {
        viewModel.finishAktuellesZiel();
        nextZiel.setVisibility(View.GONE);
    }

    /**
     * Auf Spieler fokussieren und zoomen
     *
     * @param view view
     */
    private void onFocusOnPlayerButtonClicked(View view) {
        if (viewModel.getAktuellesPunktObj() != null) {
            map.getController().setCenter(viewModel.getAktuellesPunktObj().toGeopoint());
            map.getController().setZoom(20.0);
        }
    }

    /**
     * Startet tracking
     */
    private void startTrackingService() {
        Intent intent = new Intent(this.getApplicationContext(), GeoTrackingService.class);
        intent.setAction(GeoTrackingService.START_ACTION);

        Bundle bundle = new Bundle();
        bundle.putParcelable("aktuellesSpiel", viewModel.getAktuellesSpiel());
        intent.putExtra("spielBundle", bundle);

        startService(intent);
    }

    /**
     * Stoppt tracking
     */
    private void stopTrackingService() {
        Intent intent = new Intent(this.getApplicationContext(), GeoTrackingService.class);
        intent.setAction(GeoTrackingService.STOP_ACTION);
        startService(intent);
        Log.d(TAG, "stopTrackingService: Stopped Tracking Service");
    }

    /**
     * Zurück zum Home Menu
     */
    private void returnToHomeMenu(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}