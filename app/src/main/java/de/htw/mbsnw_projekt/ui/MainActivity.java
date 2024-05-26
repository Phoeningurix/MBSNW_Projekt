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


import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
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

        /*repository.getSpiele().observeForever(spiele -> {
            Log.d(TAG, "alleSpiele: " + spiele.size());
            if (spiele.get(0).getEndTimestamp() == null) {
                spiele.get(0).setEndTimestamp(LocalDateTime.now());
                repository.update(spiele.get(0));
            }
        });*/

        Button button = findViewById(R.id.openMainMenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }



}