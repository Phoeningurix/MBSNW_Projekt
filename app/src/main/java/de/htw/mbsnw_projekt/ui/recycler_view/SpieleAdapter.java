package de.htw.mbsnw_projekt.ui.recycler_view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.ui.CreateSpielActivity;
import de.htw.mbsnw_projekt.ui.SpielActivity;
import de.htw.mbsnw_projekt.ui.SpielInfoActivity;

public class SpieleAdapter extends RecyclerView.Adapter<SpieleAdapter.SpieleHolder> {

    private List<Spiel> spiele = new ArrayList<>();

    private SpielRecyclerView spielRecyclerView;

    public SpieleAdapter(SpielRecyclerView spielRecyclerView) {
        this.spielRecyclerView = spielRecyclerView;
    }

    @NonNull
    @Override
    public SpieleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spiel_item, parent, false);
        return new SpieleHolder(itemView, spielRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpieleHolder holder, int position) {
        Spiel currentSpiel = spiele.get(position);
        String name = String.format(Locale.GERMAN, "Spiel Nr. %d: %te.%<tB.%<tY", currentSpiel.getId(), currentSpiel.getStartTimestamp());
        holder.spielNameText.setText(name);
        // TODO: 27.06.2024 Spieldauer berechnen
        String zeit = String.format(Locale.GERMAN, "%tH:%<tM:%<tS", currentSpiel.getStartTimestamp());
        holder.spielZeitText.setText(zeit);
        App.getRepository().getNichtErreichteSpielZiele(currentSpiel.getId()).observeForever(ziele -> {
            if (ziele.isEmpty()) {
                holder.spielIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_successful, null));
                holder.spielNameText.setTextColor(App.getAndroidApp().getColor(R.color.primaryDarkGreen));
                holder.spielZeitText.setTextColor(App.getAndroidApp().getColor(R.color.primaryGreen));
            } else if (currentSpiel.getEndTimestamp() == null){
                holder.spielIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_running, null));
                holder.spielNameText.setTextColor(App.getAndroidApp().getColor(R.color.dark_blue));
                holder.spielZeitText.setTextColor(App.getAndroidApp().getColor(R.color.medium_blue));
            } else {
                holder.spielIcon.setImageDrawable(ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.game_failed, null));
                holder.spielNameText.setTextColor(App.getAndroidApp().getColor(R.color.dark_red));
                holder.spielZeitText.setTextColor(App.getAndroidApp().getColor(R.color.red));
            }
        });
    }

    @Override
    public int getItemCount() {
        return spiele.size();
    }

    public void setSpiele(List<Spiel> spiele) {
        this.spiele = spiele;
        // TODO: 27.06.2024 Ändern!!!
        notifyDataSetChanged();
    }

    public class SpieleHolder extends RecyclerView.ViewHolder {
        private TextView spielNameText;
        private TextView spielZeitText;
        private ImageView spielIcon;

        public SpieleHolder(@NonNull View itemView, SpielRecyclerView spielRecyclerView) {
            super(itemView);
            spielNameText = itemView.findViewById(R.id.spiel_name_text);
            spielZeitText = itemView.findViewById(R.id.spiel_zeit_text);
            spielIcon = itemView.findViewById(R.id.spiel_icon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                        // TODO: 27.06.2024 Spiel an neue Activity übergeben
                        spielRecyclerView.onClick(spiele.get(getAdapterPosition()));

                    }
                }
            });

        }
    }


    
}
