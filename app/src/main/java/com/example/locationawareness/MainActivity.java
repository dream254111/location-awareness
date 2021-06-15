package com.example.locationawareness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView locationText;
    private Button sendButton;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = findViewById(R.id.location_txt);
        sendButton = findViewById(R.id.send_btn);

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);

        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, 1);

        sendButton.setEnabled(false);
        locationText.setText(R.string.getting_location);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        sendButton.setOnClickListener(this);
    }

    private boolean checkPermission(String service) {
        int permission = ActivityCompat.checkSelfPermission(this, service);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        sendButton.setEnabled(true);
        locationText.setText(R.string.finish_location);
    }

    @Override
    public void onClick(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/maps/place/" + latitude + "+" + longitude);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}