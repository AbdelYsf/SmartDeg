package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Notification.MyAlarmReceiver;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Doctor;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Medicament;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Rdv;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;


public class AddRdvActivity extends AppCompatActivity {
    private TextView textChooseDoctor,textChooseMed,textChooseDate,textChooseTime,textViewHidden;
     private Toolbar toolbar;
    private CompactCalendarView calendarView;

     private AlertDialog dialogDtae,dialogTime,dialogMed,dialogDoctor,dialogNotification;
     private  AlertDialog.Builder builderDate,builderTime,builderMed,builderDoctor,builderNotification;

     private DatePicker datePicker;
     private TimePicker timePicker;
     DataBaseHandeler dataBaseHandeler;

     String[] notificationChoice={"a l'heur du RDV","avant 15 mins","avant 30 mins","avant 45 mins"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_rdv );


        dataBaseHandeler=DataBaseHandeler.getInstance( this );

        // recuperation des textView
        textChooseDoctor=findViewById( R.id.textChooseDoctor );
        textChooseMed=findViewById( R.id.textChooseMed );
        textChooseDate=findViewById( R.id.textChooseDate );
        textChooseTime=findViewById( R.id.textChooseTime );
        textViewHidden=findViewById( R.id.tesxtViewNotificationSelectedHedden );


        // configuring the toolBar
         toolbar=findViewById( R.id.toolbarAddRdvActivity );
         setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //end of the configuration of the toolbar

                                //--------------------------------------------
                                // mise en place des listner sur les TestView
                                //--------------------------------------------
        textChooseDate.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDate();
                    }
                } );
        textChooseTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertTime();
            }
        } );
        textChooseDoctor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDoctor();
            }
        } );
        textChooseMed.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMed();
            }
        } );
        findViewById( R.id.fabNotivicationTime).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertNotificationTime();
            }
        } );

        //test






    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++
    // redefinition des methode qui concerne la toolBar +
    //+++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.toolbar_item_confirm,menu );
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_doctor_toolbar_ajouter:
                if(allIsCorrect()){
                    boolean db = dataBaseHandeler.addRdvToDb( fillRdv() );
                    Log.i("db",db+"");
                    if(db){
                        Toast.makeText( this, "bien ajouté", Toast.LENGTH_SHORT ).show();
//                    addEventToCalendar();
                    setNotification(); Log.i("db","am here ");
                  Intent intent = new Intent( this, MainActivity.class );
                        startActivity( intent );
                 }
                 //

                }

                else
                    Toast.makeText( this, "essayer de remplire tous les champs SVP", Toast.LENGTH_SHORT ).show();



                break;
        }
        return true;
    }


    //--------------------------------------------------
    // traitements des AlertDialog
    //---------------------------------------------------
    private void alertDoctor(){
        ArrayList<Doctor> doctorArrayList=dataBaseHandeler.getAllDocters();
        final String[] doctorNameArray =new String[doctorArrayList.size()];
        // ici on convertis le ArrayList a un tableau qui va contenir les nom des docteur;
        for(int i=0;i<doctorArrayList.size();i++)
        {
            doctorNameArray[i]=doctorArrayList.get( i ).getNom()+","+doctorArrayList.get(i).getPrenom();
        }
       builderDoctor =new AlertDialog.Builder( AddRdvActivity.this );

       builderDoctor.setCancelable( true );
       builderDoctor.setSingleChoiceItems( doctorNameArray, 0, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               String selectedDoctor="";
               selectedDoctor=doctorNameArray[which];
               textChooseDoctor.setText( selectedDoctor );
           }
       } );
       builderDoctor.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       } );
       builderDoctor.setNeutralButton( "vider", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               textChooseDoctor.setText( "" );

           }
       } );

       dialogDoctor=builderDoctor.create();
       dialogDoctor.show();





    }
    private  void alertMed(){
      final ArrayList<Medicament> medicamentArrayList= dataBaseHandeler.getAllMedi();
      builderMed=new AlertDialog.Builder( AddRdvActivity.this );
      final ArrayList<Integer> positionSelected=new ArrayList<>(  );

      final String[] mediLibelle=new String[medicamentArrayList.size()];
      boolean[] checkedItem=new boolean[medicamentArrayList.size()];

      for(int i=0;i<medicamentArrayList.size();i++)
      {
        mediLibelle[i]=medicamentArrayList.get(i).getLabel();
      }
      builderMed.setCancelable( true );
      builderMed.setMultiChoiceItems( mediLibelle, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int position, boolean isChecked) {
              if(isChecked)
              {
                  if(!positionSelected.contains( position ))
                  {
                      positionSelected.add( position );
                  }

              }
          }
      } );
      builderMed.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              String selectedMedi="";
              for(int i=0;i<positionSelected.size();i++){
                  if(i==0)
                      selectedMedi=mediLibelle[i];
                  else
                  selectedMedi=selectedMedi+","+mediLibelle[i];
              }
              textChooseMed.setText( selectedMedi );


          }
      } );
      builderMed.setNeutralButton( "vider tous", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              textChooseMed.setText( "" );
          }
      } );
      dialogMed=builderMed.create();
      dialogMed.show();




    }
    private void alertDate(){

        builderDate = new AlertDialog.Builder( AddRdvActivity.this );
        builderDate.setCancelable( true );
        View viewDate = getLayoutInflater().inflate( R.layout.alert_dialog_date_layout,null );
        datePicker=viewDate.findViewById( R.id.datePickerAlertDialogLayout );
        builderDate.setView( viewDate );
        builderDate.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s=""+datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();

              textChooseDate.setText( s );
            }
        } );
        dialogDtae=builderDate.create();
        dialogDtae.show();

    }
    private void alertTime(){
        builderTime = new AlertDialog.Builder( AddRdvActivity.this );
        builderTime.setCancelable( true );
        View viewTime=getLayoutInflater().inflate( R.layout.alert_dialog_time_layout,null );
        timePicker=viewTime.findViewById( R.id.timePickerAlertDialog );
        builderTime.setView( viewTime );
        builderTime.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s=timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
                textChooseTime.setText( s );

            }
        } );
        dialogTime=builderTime.create();
        dialogTime.show();


    }
    private  void alertNotificationTime(){

        builderNotification=new AlertDialog.Builder( this );
        builderNotification.setTitle( "recevoir une Notification a:" );
        builderNotification.setCancelable( true );
        builderNotification.setSingleChoiceItems( notificationChoice, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( AddRdvActivity.this, ""+which, Toast.LENGTH_SHORT ).show();
                textViewHidden.setText( ""+which );
            }
        } );
        builderNotification.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        } );
        dialogNotification=builderNotification.create();
        dialogNotification.show();


    }
    private  long getTimeInMiliseconds(){
        Calendar calendar=Calendar.getInstance();
        if(Build.VERSION.SDK_INT < 23)
        {
            calendar.set( datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()
                    ,timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0);
        }
        else
        {
            calendar.set( datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()
                    ,timePicker.getHour(),timePicker.getMinute(),0);
        }

       // test du choix du reception de la notification

        if(!textViewHidden.getText().toString().isEmpty()){
            int t =Integer.parseInt(  textViewHidden.getText().toString());
            switch (t){
                case 0: return  calendar.getTimeInMillis();
                case 1: return  calendar.getTimeInMillis() - (15*60*1000);
                case 2: return  calendar.getTimeInMillis() - (30*60*1000);
                case 3: return  calendar.getTimeInMillis() - (45*60*1000);
                default: return  calendar.getTimeInMillis();

            }

        }

        return  calendar.getTimeInMillis();
    }
    private void setNotification() {
        //Toast.makeText( this, "notification has set", Toast.LENGTH_SHORT ).show();

        String notificationTitle="vous avez un RDV a "+textChooseTime.getText();
        String notificationContent="Ne oubliez pas votre RENDEZ-VOUS avec mr/mme \n"+
                textChooseDoctor.getText()+" a "+textChooseTime.getText();

        Intent intentToMyAlarmReceiver= new Intent( this, MyAlarmReceiver.class );
        intentToMyAlarmReceiver.putExtra( "title",notificationTitle );
        intentToMyAlarmReceiver.putExtra( "content",notificationContent );
        PendingIntent pendingIntent=PendingIntent.
                getBroadcast( this,0,intentToMyAlarmReceiver,PendingIntent.FLAG_UPDATE_CURRENT );

        AlarmManager alarmManager= (AlarmManager) getSystemService( ALARM_SERVICE );

        alarmManager.set( AlarmManager.RTC_WAKEUP,getTimeInMiliseconds(),pendingIntent );


    }
    private  Rdv fillRdv(){

        Rdv rdv =new Rdv();
        rdv.setDoctorName( textChooseDoctor.getText().toString() );
        rdv.setMedLabels( textChooseMed.getText().toString() );
        rdv.setDateRdv( textChooseDate.getText().toString() );

        rdv.setTimeRdv( textChooseTime.getText().toString() );



        return  rdv;
    }
    private boolean allIsCorrect(){
        if(
                                 (textChooseMed.getText().toString().isEmpty())
                                 ||
                                 (textChooseDoctor.getText().toString().isEmpty())
                                 ||
                                 ( textChooseDate.getText().toString().isEmpty())
                                 ||
                                 (textChooseTime.getText().toString().isEmpty())
                ) return false;
        else return  true;



    }
//    private void addEventToCalendar(){
//
//        Toast.makeText( this, "evennnnnnnt", Toast.LENGTH_SHORT ).show();
//        Event event = new Event( Color.YELLOW,getTimeInMiliseconds(),"bela bela bela " );
//        Log.i( "eventnn",calendarView+"" );
//
//        calendarView.addEvent( event);
//    }
















}
