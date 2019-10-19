package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abdel ysf on 01/03/2018.
 */

public class DownloadUrl {

    public  String readUrl(String myUrl) throws  IOException{
        String data="";
        InputStream inputStream=null;
        HttpURLConnection urlConnection=null;
        try {
            URL url= new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            // puis ,on va lire les donnee a partir du URL
            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
            StringBuffer sb = new StringBuffer(  );

            String line="";
             while((line =bufferedReader.readLine()) != null){
                 sb.append( line );
             }

             // puis on convertie le StringBuffer en String;
            data =sb.toString();

             // on ferme le stream du bufferedReader
            bufferedReader.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // on ferme les objet;
            inputStream.close();
            urlConnection.disconnect();

        }
        Log.i("tag1",data);
        return  data;
    }
}
