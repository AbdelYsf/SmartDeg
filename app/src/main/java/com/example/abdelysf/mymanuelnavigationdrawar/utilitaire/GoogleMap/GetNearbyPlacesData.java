package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap;

import android.os.AsyncTask;
import android.widget.CalendarView;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abdel ysf on 01/03/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
   private String googlePlaceData;
   private GoogleMap mMap;
   private String url;

   private boolean isDoctors=false;




    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        isDoctors=url.contains( "doctor" );


        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaceData=downloadUrl.readUrl( url );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaceData;
    }


    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlaces = null;
        DataParser dataParser = new DataParser();
        nearByPlaces = dataParser.parse( s );
        // et puis on va faire appel a la methode qui affiche les places dans le map;
        showNearByPlaces( nearByPlaces );

    }

    private  void showNearByPlaces(List<HashMap<String,String>> nearByPlaceList){
        // cette mathde va afficher tous les place dans le map
        for(int i =0;i<nearByPlaceList.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String> googlePlace = nearByPlaceList.get( i );
              String placeName= googlePlace.get( "place_name" );
              String vicinity = googlePlace.get("vicinity");
              double lat = Double.parseDouble( googlePlace.get( "lat" ) );
            double lng = Double.parseDouble( googlePlace.get( "lng" ) );

            LatLng latLng = new LatLng( lat,lng );

            markerOptions.position( latLng  )
                    .title( placeName+" : "+vicinity );

            if(isDoctors){
                markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_VIOLET ) );
            }else
            {
                markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_MAGENTA ) );
            }

            mMap.addMarker( markerOptions );
            mMap.moveCamera( CameraUpdateFactory.newLatLng( latLng ) );
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 15 ) );

        }
    }


}
