package de.htw.mbsnw_projekt.ui.navigation_drawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.ui.SpielActivity;
import de.htw.mbsnw_projekt.ui.SpielInfoActivity;
import de.htw.mbsnw_projekt.ui.recycler_view.SpielRecyclerView;
import de.htw.mbsnw_projekt.ui.recycler_view.SpieleAdapter;
import de.htw.mbsnw_projekt.view_models.SpieleFragmentViewModel;


public class SpieleFragment extends Fragment implements SpielRecyclerView {

    private SpieleFragmentViewModel viewModel;

    private TextView keineSpiele;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spiele, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SpieleFragmentViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_spiele);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        keineSpiele = view.findViewById(R.id.no_spiele_text);

        SpieleAdapter adapter = new SpieleAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getAlleSpiele().observe(getViewLifecycleOwner(), spiele -> {
            if (spiele.isEmpty()) {
                keineSpiele.setVisibility(TextView.VISIBLE);
            } else {
                adapter.setSpiele(spiele);
                keineSpiele.setVisibility(TextView.GONE);
            }

        });

    }

    @Override
    public void onClick(Spiel spiel) {

        if (spiel.getEndTimestamp() != null) {
            Intent intent = new Intent(getContext(), SpielInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", spiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), SpielActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("aktuellesSpiel", spiel);
            intent.putExtra("spielBundle", bundle);
            startActivity(intent);
        }
    }

}