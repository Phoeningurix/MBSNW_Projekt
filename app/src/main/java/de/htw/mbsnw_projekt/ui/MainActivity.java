package de.htw.mbsnw_projekt.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.repositories.Repository;

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

        repository.getSpiele().observeForever(spiele -> {
            Log.d(TAG, "alleSpiele: " + spiele.size());
            if (spiele.get(0).getEndTimestamp() == null) {
                spiele.get(0).setEndTimestamp(LocalDateTime.now());
                repository.update(spiele.get(0));
            }
        });

    }
}