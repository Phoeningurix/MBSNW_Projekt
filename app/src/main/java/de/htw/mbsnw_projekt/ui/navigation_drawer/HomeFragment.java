package de.htw.mbsnw_projekt.ui.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.ui.CreateSpielActivity;
import de.htw.mbsnw_projekt.ui.MainActivity;
import de.htw.mbsnw_projekt.ui.SpielActivity;
import de.htw.mbsnw_projekt.view_models.HomeViewModel;


public class HomeFragment extends Fragment {

    Button startButton, resumeButton;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        TextView textView = view.findViewById(R.id.blubtext);
        startButton = view.findViewById(R.id.startGameButton);
        resumeButton = view.findViewById(R.id.resumeGameButton);

        startButton.setOnClickListener(this::onStartClicked);
        resumeButton.setOnClickListener(this::onResumeClicked);

        viewModel.getAktuellesSpiel(spiel -> textView.setText(spiel == null ? "null" : spiel.toString()));


    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.existiertAktuellesSpiel(
                () -> {
                    startButton.setVisibility(View.INVISIBLE);
                    resumeButton.setVisibility(View.VISIBLE);
                },
                () -> {
                    startButton.setVisibility(View.VISIBLE);
                    resumeButton.setVisibility(View.INVISIBLE);
                }
        );
    }

    private void onStartClicked(View view) {
        //Toast.makeText(view.getContext(), "Start new Game", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CreateSpielActivity.class);
        startActivity(intent);
    }

    private void onResumeClicked(View view) {
        viewModel.getAktuellesSpiel(aktuellesSpiel -> {
            Intent intent = new Intent(getActivity(), SpielActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", aktuellesSpiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        });
    }


}