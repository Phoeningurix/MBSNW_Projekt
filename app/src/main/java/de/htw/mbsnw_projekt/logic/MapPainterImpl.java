package de.htw.mbsnw_projekt.logic;

import android.content.Context;
import android.graphics.Color;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

public class MapPainterImpl implements MapPainter {

    private final MapView map;
    private List<GeoPoint> wegPunkte;
    Polyline line;

    List<Marker> markerList;

    public MapPainterImpl(MapView mapView, Context activityContext) {

        map = mapView;

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(18.0);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(activityContext, map);
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);

        wegPunkte = new LinkedList<>();
        line = new Polyline();
        map.getOverlays().add(line);

        markerList = new ArrayList<>();

    }

    public void zielHinzufuegen(Zielort zielort) {
        for (Marker m : markerList) {
            map.getOverlays().remove(m);
            m.setTextLabelForegroundColor(Color.RED);
            map.getOverlays().add(m);
        }
        Marker marker = new Marker(map);
        GeoPoint punkt = zielort.toGeopoint();
        marker.setPosition(punkt);
        markerList.add(marker);
        map.getOverlays().add(marker);
        map.getController().setCenter(punkt);
    }

    public void punktHinzufuegen(Punkt punkt) {
        wegPunkte.add(punkt.toGeopoint());
        line.setPoints(wegPunkte);
    }

    @Override
    public void punkteSetzen(List<Punkt> punkte) {

        if (!punkte.isEmpty()) {
            wegPunkte = punkte.stream().map(Punkt::toGeopoint).collect(Collectors.toList());
            line.setPoints(wegPunkte);

            map.getController().setCenter(wegPunkte.get(wegPunkte.size() - 1));
        }

    }

}
