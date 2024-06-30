package de.htw.mbsnw_projekt.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.time.Duration;
import java.util.Locale;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.logic.MapPainter;
import de.htw.mbsnw_projekt.logic.MapPainterImpl;
import de.htw.mbsnw_projekt.view_models.SpielInfoViewModel;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;

public class SpielInfoActivity extends AppCompatActivity {

    private static final String TAG = "SpielInfoActivity";

    private TextView spielZieleAnzahl;

    private TextView spielDauer;

    private TextView spielZeitlimit;

    private SpielInfoViewModel viewModel;

    private MapView map;

    private MapPainter mapPainter;

    private Toolbar toolbar;

    private ImageView spielInfoIcon;

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
        spielDauer = findViewById(R.id.spiel_dauer_text);
        spielZeitlimit = findViewById(R.id.spiel_zeit_limit_text);
        toolbar = findViewById(R.id.spiel_info_toolbar);
        spielInfoIcon = findViewById(R.id.spiel_info_icon);

        viewModel.getNichErreichteZiele().observe(this, ziele -> {
            if (ziele.isEmpty()) {
                spielInfoIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_successful, null));
            } else if (ausgewaehltesSpiel.getEndTimestamp() == null) {
                spielInfoIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_running, null));
            } else {
                spielInfoIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_failed, null));
            }
        });

        toolbar.setTitle("Spiel Nr. " + ausgewaehltesSpiel.getId());

        map = findViewById(R.id.spiel_info_map);

        mapPainter = new MapPainterImpl(map, this);

        viewModel.getAlleSpielZiele().observe(this, ziele -> spielZieleAnzahl.setText("Anzahl Ziele: " + ziele.size()));
        String zeitlimit = "Zeitlimit: " + ((spiel.getTimeLimit()/1000)/60)/60 + "h";
        spielZeitlimit.setText(zeitlimit);

        if (ausgewaehltesSpiel.getEndTimestamp() != null) {
            Duration duration = Duration.between(ausgewaehltesSpiel.getStartTimestamp(), ausgewaehltesSpiel.getEndTimestamp());
            long seconds = duration.getSeconds();

            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;

            spielDauer.setText(String.format(Locale.GERMAN, "Spieldauer: %02d:%02d:%02d", hours, minutes, secs));

        } else {
            spielDauer.setText("Spieldauer: 00:00:00");
        }


        map.getController().setCenter(new GeoPoint(52.520553, 13.408770));



        map.getController().setZoom(13.0);

        viewModel.getAlleSpielZielorte().observe(this, zielorte -> mapPainter.alleZielorteHinzufuegen(this, zielorte));

        viewModel.getAlleSpielPunkte().observe(this, punkte ->{
            mapPainter.punkteSetzen(punkte);

            /*double maxLatidude = punkte.stream().map(Punkt::getLatitude).reduce(Double::max).orElse(0.0);
            double minLatidude = punkte.stream().map(Punkt::getLatitude).reduce(Double::min).orElse(0.0);
            double maxLongitude = punkte.stream().map(Punkt::getLatitude).reduce(Double::max).orElse(0.0);
            double minLongitude = punkte.stream().map(Punkt::getLatitude).reduce(Double::min).orElse(0.0);

            BoundingBox box = map.getBoundingBox();
            box.set(maxLatidude, minLongitude , minLatidude, maxLongitude);
            map.zoomToBoundingBox(box, false);*/

        });

    }
}