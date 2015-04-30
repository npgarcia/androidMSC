package nadia.com.maps;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

public class ActivityMap extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener, LocationListener {

    private SupportMapFragment mMap; // Might be null if Google Play services APK is not available.
    private GoogleMap googleMap;
    HashMap<LatLng,Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status!= ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMapIfNeeded()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                googleMap = mMap.getMap();
                googleMap.setMyLocationEnabled(true);

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();
                // Getting the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);
                // Getting Current Location
                Location location = locationManager.getLastKnownLocation(provider);
                if(location != null){
                    onLocationChanged(location);
                }

                locationManager.requestLocationUpdates(provider, (long)20000, 0f, this);

                googleMap.setOnMapClickListener(this);
                mMap.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng gdl = new LatLng(20.666667, -103.333333);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gdl, 13));
        googleMap.addMarker(new MarkerOptions().title("Guadalajara").snippet("Hueles a pura tierra mojada").position(gdl));


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.map_view:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.map_hybrid:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.map_terrain:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.activity_map_polylines:
                showPolylines();
                break;
        }
    }

    public void showPolylines(){
        if(googleMap != null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(-18.142, 178.431), 2));
            // Polylines are useful for marking paths and routes on the map.
            googleMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(new LatLng(-33.866, 151.195)) // Sydney
                    .add(new LatLng(-18.142, 178.431)) // Fiji
                    .add(new LatLng(21.291, -157.821)) // Hawaii
                    .add(new LatLng(20.666667, -103.333333)) // Guadalajara
            );
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, 16));
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker))
                .title("Custom Marker")
                .snippet("My Custom Marker")
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(latLng));

        markers.put(latLng,marker);

    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting latitude of the current location
        double latitude = location.getLatitude();
        // Getting longitude of the current location
        double longitude = location.getLongitude();
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
