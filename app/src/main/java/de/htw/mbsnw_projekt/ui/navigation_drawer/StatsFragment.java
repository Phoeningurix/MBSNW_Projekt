package de.htw.mbsnw_projekt.ui.navigation_drawer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.view_models.HomeViewModel;
import de.htw.mbsnw_projekt.view_models.SpielViewModel;
import de.htw.mbsnw_projekt.view_models.StatsViewModel;


public class StatsFragment extends Fragment {

    private StatsViewModel viewModel;

    private TextView spieleAnzahl, spieleGewonnenAnzahl, spieleVerlorenAnzahl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StatsViewModel.class);

        spieleAnzahl = view.findViewById(R.id.spiele_anzahl_text);
        spieleGewonnenAnzahl = view.findViewById(R.id.spiele_gewonnen_anzahl_text);
        spieleVerlorenAnzahl = view.findViewById(R.id.spiele_verloren_anzahl_text);

        viewModel.getAlleSpiele().observe(this.getViewLifecycleOwner(), spiele -> spieleAnzahl.setText("Spiele gespielt: " + spiele.size()));
        viewModel.getErfolgreicheSpiele().observe(this.getViewLifecycleOwner(), spiele -> spieleGewonnenAnzahl.setText("Spiele gewonnen: " + spiele.size()));
        viewModel.getNichterfolgreicheSpiele().observe(this.getViewLifecycleOwner(), spiele -> spieleVerlorenAnzahl.setText("Spiele verloren: " + spiele.size()));

    }
}