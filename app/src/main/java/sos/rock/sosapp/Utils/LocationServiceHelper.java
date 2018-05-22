package sos.rock.sosapp.Utils;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class LocationServiceHelper extends Service {
    public static final String EXTRA_MESSENGER = "rock.sahulat.BOOKMAP_GPS";
    private static final String TAG = "BOOKMAPGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 2000;
    private static final float LOCATION_DISTANCE = 200f;
    Messenger notifyMessenger;
    public static final int MESSAGE_NOTIFITY = 120;
    private static LocationServiceHelper handler;
    private Location mLastLocation;
    public static LocationServiceHelper getInstance(){
        return handler;
    }

    public Location getLastKnowLocation(){
        return mLastLocation;
    }

    private class LocationListener implements android.location.LocationListener {
        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
            Log.e(TAG, "LocationListener " + provider);
        }

        double randomGenerator() {
            Random generator = new Random();
            return generator.nextDouble()*0.05;
        }

        @Override
        public void onLocationChanged(Location location) {
            try
            {
                double latitude = location.getLatitude();
                double longitude  = location.getLongitude();

                latitude += randomGenerator();
                longitude += randomGenerator();
                latitude = round(latitude, 4);
                longitude = round(longitude, 4);

                location.setLatitude(latitude);
                location.setLongitude(longitude);
                mLastLocation.set(location);
                Message notifiyMessage = Message.obtain();
                notifiyMessage.arg1 = MESSAGE_NOTIFITY;
                Bundle bundle = new Bundle();
                bundle.putParcelable("lastLocation", location);
                notifiyMessage.setData(bundle);
                notifyMessenger.send(notifiyMessage);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        try {
            notifyMessenger = (Messenger) intent.getExtras().get(EXTRA_MESSENGER);
        }catch (Exception e){

        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        handler = this;
        initializeLocationManager();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, "GPS Permission error");
            return;
        }
        try
        {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);

            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        }
        catch (Exception e)
        {
            Log.d(TAG, "GPS Error");
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        handler = null;
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}