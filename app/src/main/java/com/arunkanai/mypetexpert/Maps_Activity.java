package com.arunkanai.mypetexpert;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.arunkanai.mypetexpert.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maps_Activity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private SearchView searchView;
    private Marker petHospitalMarker;
    private Marker petShopMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null && !location.isEmpty()) {
                    Geocoder geocoder = new Geocoder(Maps_Activity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    } else {
                        Toast.makeText(Maps_Activity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    displayPetPlacesNearCurrentLocation();
                    return true;
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void displayPetPlacesNearCurrentLocation() {
        if (map != null) {
            // Clear previously added markers
            if (petHospitalMarker != null) {
                petHospitalMarker.remove();
            }
            if (petShopMarker != null) {
                petShopMarker.remove();
            }

            // Check if the user's location is available
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Get the user's current location
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    // Get the latitude and longitude of the current location
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Add multiple pet hospitals
                    List<LatLng> petHospitalLatLngList = new ArrayList<>();
                    petHospitalLatLngList.add(new LatLng(latitude, longitude));
                    petHospitalLatLngList.add(new LatLng( 7.287069273219932,   80.66306984860516));
                    petHospitalLatLngList.add(new LatLng( 7.296906159513923,   80.64937481466991));
                    petHospitalLatLngList.add(new LatLng( 7.300809625903707, 80.63111476942291));
                    petHospitalLatLngList.add(new LatLng( 7.2901921176767575, 80.62355888863105));
                    petHospitalLatLngList.add(new LatLng( 7.280667374050097, 80.61836422058663));
                    petHospitalLatLngList.add(new LatLng( 7.32048257755821, 80.62969804177443));

                    for (LatLng petHospitalLatLng : petHospitalLatLngList) {
                        petHospitalMarker = map.addMarker(new MarkerOptions()
                                .position(petHospitalLatLng)
                                .title("Pet Hospital")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }

                    // Add multiple pet shops
                    List<LatLng> petShopLatLngList = new ArrayList<>();
                    petShopLatLngList.add(new LatLng( 7.2952429234454845,   80.63783145097068));
                    petShopLatLngList.add(new LatLng( 7.2956238097472,   80.63780059423779));
                    petShopLatLngList.add(new LatLng( 7.300114379680823, 80.63647709432456));
                    petShopLatLngList.add(new LatLng( 7.306466148434335, 80.6356766291894));
                    petShopLatLngList.add(new LatLng( 7.321062659361685, 80.62847244297303));

                    for (LatLng petShopLatLng : petShopLatLngList) {
                        petShopMarker = map.addMarker(new MarkerOptions()
                                .position(petShopLatLng)
                                .title("Pet Shop")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }

                    // Animate camera to show all markers
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng latLng : petHospitalLatLngList) {
                        builder.include(latLng);
                    }
                    for (LatLng latLng : petShopLatLngList) {
                        builder.include(latLng);
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 100; // Padding around markers in pixels
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                } else {
                    Toast.makeText(this, "Unable to retrieve current location", Toast.LENGTH_SHORT).show();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }
}













