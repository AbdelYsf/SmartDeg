package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton fabPharmacyBtn;
    private final  int PROXIMITY_RADIUS=10000;
    Location userCurrentLocation;
    //double latitude,langitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        // on recupere les button
        findViewById( R.id.floatingActionButtonMapPharmacy).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             setPharmacies();
            }
        } );
        findViewById(R.id.floatingActionButtonMapDoctors).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDoctors();
            }
        } );




        findViewById( R.id.floatingActionButtonMapSearch ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlertSearch();
            }
        } );





        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }

        if(mGoogleApiClient==null){
            mGoogleApiClient=new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API)
                    .build();

        }
    }

    private void setPharmacies(){
        mMap.clear();
        String hopital="pharmacy";
        String url = getUrl(userCurrentLocation.getLatitude(),userCurrentLocation.getLongitude(),hopital);
        Object dataTransefer[]= new Object[2];
        dataTransefer[0]=mMap;
        dataTransefer[1]=url;
        GetNearbyPlacesData getNearbyPlacesData= new GetNearbyPlacesData();
        getNearbyPlacesData.execute( dataTransefer );

        Toast.makeText( MapsActivity.this, "les pharmacies", Toast.LENGTH_SHORT ).show();
    }
    private  void setDoctors(){
        mMap.clear();
        String hopital="doctor";
        String url = getUrl(userCurrentLocation.getLatitude(),userCurrentLocation.getLongitude(),hopital);
        Object dataTransefer[]= new Object[2];
        dataTransefer[0]=mMap;
        dataTransefer[1]=url;
        GetNearbyPlacesData getNearbyPlacesData= new GetNearbyPlacesData();
        getNearbyPlacesData.execute( dataTransefer );

        Toast.makeText( MapsActivity.this, "les docteurs", Toast.LENGTH_SHORT ).show();

    }
    // cette methode se charge de former un url sous la forme
    // https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters
    private  String getUrl( double latitude,double langitude,String nearbyPlace){
        StringBuilder googlePlaceUrl =new StringBuilder( "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" );
        googlePlaceUrl.append("location="+userCurrentLocation.getLatitude()+","+userCurrentLocation.getLongitude());//33.857665+","+-5.580027
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&language=ar");

        googlePlaceUrl.append("&key="+"AIzaSyBLsLDhNrFno-M5c9Rf7PMp-j_nIiB92UQ");

        Log.i("tag",googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }


    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission( this,Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED)
            {
                mMap.setMyLocationEnabled( true );
            }
        showThePlaceOfDoc();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if(ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions( MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_CHECKIN_PROPERTIES },1 );

        }else{
            userCurrentLocation =LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );
            if(userCurrentLocation!=null){
                MarkerOptions currentUserLoc=new MarkerOptions();
                LatLng CurrentUserLatLng= new LatLng( userCurrentLocation.getLatitude(),userCurrentLocation.getLongitude() );
                currentUserLoc.position( CurrentUserLatLng ).title( "you are here" )
                        .icon(  BitmapDescriptorFactory.fromResource( R.mipmap.myplace));


                mMap.addMarker( currentUserLoc );
                mMap.animateCamera( CameraUpdateFactory.newLatLngZoom( CurrentUserLatLng,10 ) );
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
     // cette methode s'occupe de verifier si la le services googlePlay est disponible ou pas
    private boolean CheckGooglePlayServices() {
        //GoogleApiAvailability is the Helper class for verifying that the Google
        // Play services APK is available and up-to-date on android device.
        // If result is ConnectionResult.SUCCESS then connection was successful otherwise, we will return false.
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    private void showThePlaceOfDoc(){
        Intent intent= getIntent();
        if(intent.hasExtra( "address" )){


            Geocoder geocoder = new Geocoder( getApplicationContext() );
            List<Address> addressList= null;
            MarkerOptions markerOptions = new MarkerOptions();

            try {
                Log.i( "adr", intent.getStringExtra( "address" ) );
                addressList= geocoder.getFromLocationName( intent.getStringExtra( "address" ),5 );

            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i=0;i<addressList.size();i++){
                Address myAddress = addressList.get( i );
                LatLng latLng = new LatLng( myAddress.getLatitude(),myAddress.getLongitude() );

                markerOptions.position( latLng );
                Log.i( "latlangrdv" ,latLng.toString()+" //"+markerOptions.toString()+"//"+mMap.toString());
                mMap.addMarker( markerOptions );
                mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );

            }

        }
    }
    private void showAlertSearch(){
        LayoutInflater inflater= (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view= inflater.inflate( R.layout.search_destination,null);
        final EditText editTextSearch ;
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder( this  );
        builder.setCancelable( true );
        builder.setView( view );
        editTextSearch = view.findViewById( R.id.searchDestinationMap );
        builder.setTitle( "choisissez une destination" );
        builder.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Geocoder geocoder = new Geocoder( getApplicationContext() );
                List<Address> addressList= null;
                MarkerOptions markerOptions = new MarkerOptions();

                if (!editTextSearch.getText().toString().isEmpty())
                {

                     try {


                    addressList= geocoder.getFromLocationName( editTextSearch.getText().toString(),5 );

                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<addressList.size();i++){
                    Address myAddress = addressList.get( i );
                    LatLng latLng = new LatLng( myAddress.getLatitude(),myAddress.getLongitude() );
                    markerOptions.position( latLng );
                    Log.i( "latlangclick" ,latLng.toString()+" //"+markerOptions.toString()+"//"+mMap.toString());
                    mMap.addMarker( markerOptions );
                    mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );


                }}
                else
                {
                    Toast.makeText( getApplicationContext(),"entrez une adress SVP",Toast.LENGTH_SHORT ).show();
                }

            }
        } );



        alertDialog=builder.create();
        alertDialog.show();

    }

}
