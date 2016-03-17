package com.ksls.funmall;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class AttendanceActivity extends Activity implements LocationListener {

    private static final String TAG = AttendanceActivity.class.getSimpleName();
    private static final String[] S = { "Out of Service", "Temporarily Unavailable", "Available" };

    private LocationManager locationManager;
    private Geocoder geocoder;
    private String bestProvider;
    private TextView output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        setUpViews();
    }

    private void setUpViews() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this);

        List<String> providers = locationManager.getAllProviders();
        for (String provider : providers) {
            printProvider(provider);
        }

        System.out.print("The providers: " + providers);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(bestProvider, 2000, 1, this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        printLocation(location);
    }

    public void onProviderDisabled(String provider) {
        // let okProvider be bestProvider
        // re-register for updates
        output.append("\n\nProvider Disabled: " + provider);
    }

    public void onProviderEnabled(String provider) {
        // is provider better than bestProvider?
        // is yes, bestProvider = provider
        output.append("\n\nProvider Enabled: " + provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        output.append("\n\nProvider Status Changed: " + provider + ", Status="
                + S[status] + ", Extras=" + extras);
    }

    private void printProvider(String provider) {
        LocationProvider info = locationManager.getProvider(provider);

        Log.d(TAG, "Name:" + info.getName());
        Log.d(TAG, "Accuracy:" + info.getAccuracy());
        Log.d(TAG, "Require Cell?  " + info.requiresCell());
        Log.d(TAG, "Require Network?  " + info.requiresNetwork());
        Log.d(TAG, "Require Satellite?  " + info.requiresSatellite());
        Log.d(TAG, "Supports Altitude? " + info.supportsAltitude());
        Log.d(TAG, "Supports Bearing? " + info.supportsBearing());
        Log.d(TAG, "Supports Speed? " + info.supportsSpeed());
        Log.d(TAG, "Power requirement?" + info.getPowerRequirement());
        Log.d(TAG, "Might steal my money?" + info.hasMonetaryCost());

        //output.append(info.toString() + "\n\n");
    }

    private void printLocation(Location location){


        if (location == null)
            output.append("\nLocation[unknown]\n\n");
        else
        {
            String text = String.format("Latitude:\t %f \nLongitude:\t %f\n Altitude:\t %f\n Bearing:\t %f\n",
                    location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getBearing());
            Log.d(TAG, text);
            output.append("\n\n"+text);
        }

        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 10);
            for(Address address: addresses)
            {
                output.append("\n"+ address.getAddressLine(0));
            }


        }catch(Exception e)
        {
            Log.e("WhereAmI", "Couldn't get Geocoder data", e);
        }
    }
}
