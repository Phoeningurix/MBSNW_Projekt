package de.htw.mbsnw_projekt.logic;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.time.LocalDateTime;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.app.App;
import de.htw.mbsnw_projekt.database.models.Punkt;
import de.htw.mbsnw_projekt.database.models.Spiel;
import de.htw.mbsnw_projekt.ui.SpielActivity;

public class GeoTrackingService extends Service {
    private static final String TAG = "GeoTrackingService";

    private static final String CHANNEL_ID = "ID";

    public static final String START_ACTION = "start";
    public static final String STOP_ACTION = "stop";

    private LocationManager locationManager;

    private Spiel aktuellesSpiel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction() == null ? START_ACTION : intent.getAction();
        switch (action) {
            case START_ACTION:
                Bundle bundle = intent.getBundleExtra("spielBundle");
                if (bundle != null) {
                    aktuellesSpiel = bundle.getParcelable("aktuellesSpiel");
                }
                start();
                return START_STICKY;
            case STOP_ACTION:
                stopSelf();
            default:
                return super.onStartCommand(intent, flags, startId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called!");
        locationManager.removeUpdates(locationListener);
    }

    private void start() {
        createNotificationChannel();
        getLocation();
        startForeground(1, getNotification());
    }



    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "Location Service Channel",
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager =
                getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, SpielActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Service")
                .setContentText("Getting location updates")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setOngoing(true);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.S) {

            builder.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE);
        }
        return builder.build();
    }


    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000L, 5, locationListener, Looper.getMainLooper());
        } else {
            Log.d(TAG, "getLocation: Location Permission not granted");
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            // Handle location updates here
            Log.d(TAG, "onLocationChanged: (" + location.getLatitude() + ", " + location.getLongitude() + ")");
            if (App.getGameLogic().getTimeLeft(aktuellesSpiel) > 0) {
                // game runs
                App.getRepository().insert(new Punkt(location.getLatitude(), location.getLongitude(), LocalDateTime.now(), aktuellesSpiel.getId()));

            } else {
                // game over
                Log.d(TAG, "onLocationChanged: Stopping Service!!!");
                stopSelf();
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {}
        @Override
        public void onProviderEnabled(@NonNull String provider) {}
        @Override
        public void onProviderDisabled(@NonNull String provider) {}
    };

}
