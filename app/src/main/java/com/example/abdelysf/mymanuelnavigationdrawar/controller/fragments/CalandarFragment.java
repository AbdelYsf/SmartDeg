package com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.activities.AddRdvActivity;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters.RecyclerRdvAdapter;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Rdv;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class CalandarFragment extends Fragment  {
    private FloatingActionButton fabToAddRdv;
   private CompactCalendarView calendarView;
   private RecyclerView recyclerViewRdv;
   private DataBaseHandeler dataBaseHandeler;




    public static CalandarFragment newInstance() {
        return (new CalandarFragment());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate( R.layout.fragment_calandar, container, false );
       fabToAddRdv= v.findViewById( R.id.fabToAddRdvActivity );
       fabToAddRdv.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               toAddRdvActivity();
           }
       } );

       dataBaseHandeler = DataBaseHandeler.getInstance( getContext() );


//       Log.i("hereA",(dataBaseHandeler.getAllRdv( "24/2/2018" ))+"");


       // recyclerview traitement
       recyclerViewRdv = v.findViewById( R.id.recyclerView_Rdv );
       LinearLayoutManager layoutManager= new LinearLayoutManager(getContext() );
        recyclerViewRdv.setLayoutManager(layoutManager  );
        recyclerViewRdv.setHasFixedSize( true );




       // traitement de calendrier

        calendarView = v.findViewById(R.id.compactcalendar_view);
        calendarView.setLocale( TimeZone.getDefault(),Locale.FRENCH);

        final SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/M/yyyy",Locale.ENGLISH  );


        calendarView.setListener( new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Rdv> rdvList =dataBaseHandeler.getAllRdv( simpleDateFormat.format( dateClicked ) );
                Log.i("abdelllllah",simpleDateFormat.format( dateClicked ));
                if(rdvList.isEmpty()){
                    Snackbar.make( calendarView,"aucun RDV pour cette date",Snackbar.LENGTH_SHORT ).show();  ;
                }else{
                    RecyclerRdvAdapter rdvAdapter = new RecyclerRdvAdapter( rdvList,getContext() );
                    recyclerViewRdv.setAdapter( rdvAdapter );
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        } );




// traitement du indicateur dans le calendrier

List<Long> listMiliseconds = new ArrayList<>(  );
listMiliseconds= fitchDateInMilisecondsForIndicator();

List<Event> eventList = new ArrayList<>(  );
for(int i=0;i<listMiliseconds.size();i++){
   eventList.add( new Event( Color.YELLOW,listMiliseconds.get( i ) ) ) ;
}
calendarView.addEvents( eventList );




       return v;
    }

    private void toAddRdvActivity(){
        Intent intent = new Intent(getContext(),AddRdvActivity.class);
        startActivity( intent );
    }

    private List<Long> fitchDateInMilisecondsForIndicator(){
        Calendar calendar=Calendar.getInstance();
        List<Rdv> rdvList = new ArrayList<>(  );
        rdvList= dataBaseHandeler.getAllRdv();

        List<String > stringList = new ArrayList<>(  );
       for(int i=0;i<rdvList.size();i++){
           stringList.add(rdvList.get( i ).getDateRdv());
       }

       Log.i("taaaag",stringList.toString());


        List<Long> dateMiliSeconds = new ArrayList<>(  );
        for(int i=0;i<stringList.size();i++){
            String s=stringList.get( i );
            String[] stringTab=s.split( "/" );

            calendar.set( Integer.parseInt( stringTab[2] ),Integer.parseInt( stringTab[1] )-1,Integer.parseInt( stringTab[0] ) );
            dateMiliSeconds.add( calendar.getTimeInMillis() )    ;
        }
        return dateMiliSeconds;
    }





}
