package de.htw.mbsnw_projekt.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

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
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Ziel;
import de.htw.mbsnw_projekt.database.models.Zielort;

public class MapPainterImpl implements MapPainter {

    private static final String TAG = "MapPainterImpl";
    private final MapView map;
    private List<GeoPoint> wegPunkte;
    private final Polyline line;

    private final List<Marker> markerList;

    public MapPainterImpl(MapView mapView, Context activityContext) {

        map = mapView;

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(20.0);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(activityContext, map);
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);

        wegPunkte = new LinkedList<>();
        line = new Polyline();
        line.getOutlinePaint().setColor(Color.CYAN);
        //line.getOutlinePaint().setARGB(95, 166, 240, 255);
        line.getOutlinePaint().setStrokeWidth(30);
        line.getOutlinePaint().setStrokeCap(Paint.Cap.ROUND);
        map.getOverlays().add(line);

        markerList = new ArrayList<>();

    }

    public void zielHinzufuegen(Context context, Zielort zielort) {
        Drawable markerIconBlau = ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.marker_blau1, null);
        Drawable markerIconGruen = ResourcesCompat.getDrawable(App.getAndroidApp().getResources(), R.drawable.marker_gruen1, null);
        if (markerIconBlau == null || markerIconGruen == null) {
            Log.w(TAG, "zielHinzufuegen: Marker Drawable wurde nicht gefunden");
            return;
        }
        markerIconBlau = rescaleDrawable(markerIconBlau, 60, 60);
        markerIconGruen = rescaleDrawable(markerIconGruen, 60, 60);

        for (Marker m : markerList) {
            m.setIcon(markerIconBlau);
            m.setAlpha(0.5f);
        }

        Marker marker = new Marker(map);
        GeoPoint punkt = zielort.toGeopoint();
        marker.setPosition(punkt);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(markerIconGruen);
        marker.setOnMarkerClickListener((marker1, mapView) -> {
            mapView.getController().setZoom(20.0);
            mapView.getController().setCenter(punkt);
            return true;
        });
        markerList.add(marker);
        map.getOverlays().add(marker);
        map.getController().setCenter(punkt);
    }

    public Drawable rescaleDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(App.getAndroidApp().getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
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

            //map.getController().setCenter(wegPunkte.get(wegPunkte.size() - 1));
        }

    }

}
