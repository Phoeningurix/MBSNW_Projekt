package de.htw.mbsnw_projekt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;


import java.time.LocalDateTime;
import java.util.List;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.OrtListe;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.database.models.Zielort;
import de.htw.mbsnw_projekt.database.repositories.Repository;
import de.htw.mbsnw_projekt.ui.navigation_drawer.MainMenuActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate: String: " + App.getAndroidApp().getString(R.string.app_name));


        Repository repository = App.getRepository();

        //repository.insert(new Spiel(LocalDateTime.now().minusHours(1), null, 13984632748L, 137485L));

        repository.getAktuellesSpiel(spiel -> Log.d(TAG, "aktuelles Spiel: " + spiel));

        TextView textView = findViewById(R.id.beispielText);

        repository.getAktuellesSpiel(spiel -> textView.setText(spiel != null ? spiel.toString() : "null"));

        repository.getSpiele().observe(this, spiele -> {
            Log.d(TAG, "Setting Endtimestamp of all games");
            for (Spiel spiel : spiele) {
                if (spiel.getEndTimestamp() == null) {
                    spiel.setEndTimestamp(LocalDateTime.now());
                    repository.update(spiel);
                }
            }
        });

        Button button = findViewById(R.id.openMainMenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        //repository.insert(new OrtListe(false, "Testorte"));
        //repository.insert(new Zielort(52.455100, 13.524743, "HTW Ufer", 1));
        //repository.insert(new Zielort(52.545587, 13.423519, "Käthe-Kollwitz-Gymnasium", 1));

        repository.getAlleZielorte().observe(this, zielorts -> {
            Log.d(TAG, "onCreate: Anzahl Zielorte: " + zielorts.size());
            for (Zielort zielort : zielorts) {
                Log.d(TAG, "onCreate: Zielort: " + zielort);
            }
        });

        repository.getAllOrtslisten().observe(this, ortListes -> {
            Log.d(TAG, "onCreate: Anzahl Ortlisten: " + ortListes.size());
            for (OrtListe ortListe : ortListes) {
                Log.d(TAG, "onCreate: Ortliste: " + ortListe);
            }
        });




    }



}