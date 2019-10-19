package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abdel ysf on 01/03/2018.
 */

public class DataParser {

    private HashMap<String,String> getPlace(JSONObject googlePlaceJson){
        HashMap<String,String> googlePlacesMmap = new HashMap<>(  );
        String placeName = "-NA-";
        String vicinity ="-NA-";
        String latitude ="";
        String langitude="";
        String reference="";
        try {
            //si il n'est pas null
                if(!googlePlaceJson.isNull( "name" )){

                        placeName = googlePlaceJson.getString( "name" );


                }
                if(!googlePlaceJson.isNull( "vicinity" )){
                    vicinity =googlePlaceJson.getString( "vicinity" );
                }
                 latitude = googlePlaceJson.getJSONObject( "geometry" ).getJSONObject( "location" ).getString( "lat" );
                 langitude = googlePlaceJson.getJSONObject( "geometry" ).getJSONObject( "location" ).getString( "lng" );
                 reference =googlePlaceJson.getString( "reference" );
             googlePlacesMmap .put( "place_name",placeName );
            googlePlacesMmap .put( "vicinity",vicinity );
            googlePlacesMmap .put( "lat",latitude );
            googlePlacesMmap .put( "lng",langitude );
            googlePlacesMmap .put( "reference",reference );



        } catch (JSONException e) {
        e.printStackTrace();
    }
    return googlePlacesMmap;
    }

    private List<HashMap<String,String>> getAllPlaces(JSONArray jsonArray){

        int count = jsonArray.length();// pour stocker le nomber d'element de ce tableau
        List<HashMap<String,String>> placesList = new ArrayList<>(  );

        HashMap<String,String>  placeMap=null;

        for(int i =0;i<count;i++){
            try {
                placeMap =getPlace( (JSONObject)jsonArray.get( i ) );
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return  placesList;
    }

public List<HashMap<String,String>> parse(String jsonData){

        JSONObject jsonObject;
        JSONArray jsonArray = null;

    try {
        jsonObject = new JSONObject( jsonData );
        jsonArray = jsonObject.getJSONArray( "results" );
    } catch (JSONException e) {
        e.printStackTrace();
    }


    return  getAllPlaces( jsonArray );
}



}
