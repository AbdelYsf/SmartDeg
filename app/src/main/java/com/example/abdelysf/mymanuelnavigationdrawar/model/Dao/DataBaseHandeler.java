package com.example.abdelysf.mymanuelnavigationdrawar.model.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.abdelysf.mymanuelnavigationdrawar.model.Doctor;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Medicament;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Rdv;
import com.example.abdelysf.mymanuelnavigationdrawar.model.User;
import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel ysf on 14/01/2018.
 */

public class DataBaseHandeler extends SQLiteOpenHelper {
    private static DataBaseHandeler instance;
    // le constructeur a ete declaré privé pour resdreindre l'instanciation des nouveaux objet avec le mot cle New
   private DataBaseHandeler(Context context) {
        super( context, Constant.DB_NAME, null  , Constant.DB_VERSION );
    }

    @Override // cette methode est appelé au moment  de la creation de la base de donnees
    public void onCreate(SQLiteDatabase db ) {
             //creation de la table utilisateur
       String CREATE_USER_TAB="create table if not exists  "+Constant.USER_TABLE +"("+
               Constant.KEY_USER_NOM +" text primary key,"+Constant.KEY_USER_PRENOM +" text,"+
               Constant.KEY_USER_MAIL +" text,"+Constant.KEY_USER_PASSWORD+" text )";
        //creation de la table docteur
       String CREATE_DOCTOR_TAB="create table if not exists "+Constant.DOCTOR_TABLE +"("+
               Constant.KEY_DOCTOR_NOM +" text ,"+Constant.KEY_DOCTOR_PRENOM +" text,"+
               Constant.KEY_DOCTOR_ADRESSE +" text,"+Constant.KEY_DOCTOR_TELE+" text, "+
               Constant.KEY_DOCTOR_EMAIL+" text)";
        //creation de la table medicament
       String CREATE_MED_TAB="create table if not exists "+Constant.MEDICAMENT_TABLE+"("+
               Constant.KEY_MED_LIBELLE +" text primary key,"+Constant.KEY_MED_SPECIALITE +" text,"+
               Constant.KEY_MED_DESCRIPTION+" text )";
       // creation de la table rdv
        String CREATE_RDV_TAB="create table if not exists "+Constant.RDV_TABLE+"("+
                Constant.KEY_RDV_DOCTOR_NAME +" text ,"+Constant.KEY_RDV_MED_LABLES +" text,"+
                Constant.KEY_RDV_DATE+" text,"+Constant.KEY_RDV_time+ " text )";

       db.execSQL( CREATE_USER_TAB );
       db.execSQL( CREATE_DOCTOR_TAB );
       db.execSQL( CREATE_MED_TAB );
       db.execSQL( CREATE_RDV_TAB );




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

                  //***************************************
                  // la mise en place du SINGLTON PATTERN *
                  //***************************************
    public static synchronized DataBaseHandeler getInstance(Context c){

       if(instance==null)
           instance=new DataBaseHandeler( c.getApplicationContext() );
       return instance;
       // cette va renvoyer le meme objet a chaque fois quand'on veut instancier un objet de cette classe.


    }

                    //******************************************
                    // enregistrement des nouveaux utilisateur *
                    //******************************************
    public  boolean register(User user){
        boolean state=false;
        // creation /ou ouverture de la base pour l'ecriture
        SQLiteDatabase db = this.getWritableDatabase();
        try {


            ContentValues values= new ContentValues(  );
            values.put( Constant.KEY_USER_NOM,user.getNom() );
            values.put( Constant.KEY_USER_PRENOM ,user.getPrenom());
            values.put( Constant.KEY_USER_MAIL ,user.getEmail());
            values.put( Constant.KEY_USER_PASSWORD ,user.getPwd());
            Log.i("registerCentent",values.toString());

            long rows= db.insert( Constant.USER_TABLE,null,values );
            Log.i("registerrows",rows+"");
            if(rows!=-1)
                state=true;

        }catch (Exception e ){
            e.printStackTrace();
            Log.i( "erreur","error while inserting a new user" );
        }finally {

        }
     return state;
    }
                    //******************************************
                    // authentification des utilisateurs *
                    //******************************************
    public  boolean authentification( User user){
        SQLiteDatabase db =getReadableDatabase();
        boolean state=false;
        try {
            // declartion de la requete pour selectionner le nom et le mot de passe
            String SelectQuerry= String.format( "select %s,%s from "+Constant.USER_TABLE +" where "+Constant.KEY_USER_NOM +" =? and "+
            Constant.KEY_USER_PASSWORD+" =?",Constant.KEY_USER_NOM,Constant.KEY_USER_PASSWORD);
                Cursor cursor= db.rawQuery( SelectQuerry,new String[]{user.getNom(), user.getPwd()} );
            if(cursor.moveToFirst())
            {
                state=true;
            }
        }catch (Exception e ){
            e.printStackTrace();
            Log.i("erreur","erruer durrant l'authentification");}
        return state;
    }

            //----------------------------------------------------------------------------------------------------
            //         traitement de la partie qui concerne les docteurs                                         |
            //----------------------------------------------------------------------------------------------------

    public boolean addDoctorToDb(Doctor doctor){
        SQLiteDatabase db =getWritableDatabase();

        boolean state=false;
        try {

            ContentValues values= new ContentValues(  );
            values.put( Constant.KEY_DOCTOR_ADRESSE,doctor.getAdresse() );
            values.put( Constant.KEY_DOCTOR_NOM,doctor.getNom() );
            values.put( Constant.KEY_DOCTOR_PRENOM,doctor.getPrenom() );
            values.put( Constant.KEY_DOCTOR_EMAIL,doctor.getEmail());

            long row= db.insert( Constant.DOCTOR_TABLE,null,values );
            Log.i( "row",""+row );
               if(row==1)
                   state=true;
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.i( "erreur","erreu while ading a dotor" );
        }

        return  state;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<Doctor> getAllDocters(){
        SQLiteDatabase db = getReadableDatabase();
       ArrayList<Doctor> doctorList = new ArrayList<>(  );
        try {
            Cursor cursor= db.rawQuery( "select * from "+Constant.DOCTOR_TABLE,null,null );
            if (cursor.moveToFirst()){

                do{
                    Doctor doctor= new Doctor(  );
                    doctor.setNom( cursor.getString( cursor.getColumnIndex( Constant.KEY_DOCTOR_NOM ) ) );
                    doctor.setPrenom( cursor.getString( cursor.getColumnIndex( Constant.KEY_DOCTOR_PRENOM ) ) );
                    doctor.setAdresse( cursor.getString( cursor.getColumnIndex( Constant.KEY_DOCTOR_ADRESSE ) ) );
                    doctor.setTele( cursor.getString( cursor.getColumnIndex( Constant.KEY_DOCTOR_TELE ) ) );
                    doctor.setEmail( cursor.getString( cursor.getColumnIndex( Constant.KEY_DOCTOR_EMAIL ) ) );
                    doctorList.add( doctor );

                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while getting all the doctors" );

        }
        return  doctorList;
    }

    public boolean deleteDoctor(String name){

        SQLiteDatabase db = getWritableDatabase();
        boolean state=false;
        Log.i( "tag",name );
                int row=db.delete( Constant.DOCTOR_TABLE,Constant.KEY_DOCTOR_NOM+" = '"+name+"'",null );
                if(row!=0)
                    state=true;
         return state;
    }
    public String getDoctorAddress(String name){
        SQLiteDatabase db = getReadableDatabase();Log.i( "suuur",name );
        String s="";

        Cursor cursor = db.rawQuery( "select "+Constant.KEY_DOCTOR_ADRESSE+" from "+Constant.DOCTOR_TABLE+" where "+Constant.KEY_DOCTOR_NOM+" = '"+name+"';",
               null,null );
        if(cursor.moveToFirst())
     s = cursor.getString( 0 );

      return s;
    }



                //-------------------------------------------------------------------------------------------------------
                //         traitement de la partie qui concerne les medicaments                                        ||
                //-------------------------------------------------------------------------------------------------------


    public boolean AddMedicamentToDb(Medicament medicament){

        SQLiteDatabase db = getWritableDatabase();
        boolean state=false;
        try {
            Log.i( "medddi",medicament.getLabel() );
            ContentValues values= new ContentValues(  );
            values.put( Constant.KEY_MED_LIBELLE,medicament.getLabel() );
            values.put( Constant.KEY_MED_DESCRIPTION,medicament.getDescription() );
            values.put( Constant.KEY_MED_SPECIALITE,medicament.getSpeciality() );

            long row = db.insert( Constant.MEDICAMENT_TABLE,null,values );
            Log.i( "row",""+row );
            if (row==1)
                state=true;
        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while adding a new drug" );
        }

        return  state;

    }
    public ArrayList<Medicament> getAllMedi(){
        SQLiteDatabase db= getWritableDatabase();
       ArrayList<Medicament> medicaments=new ArrayList<>(  );
        try {
            Cursor cursor=db.rawQuery( "select * from "+Constant.MEDICAMENT_TABLE,null,null );
            if(cursor.moveToFirst())
            {
                do {
                    Medicament medicament= new Medicament(  );
                    medicament.setLabel( cursor.getString( cursor.getColumnIndex( Constant.KEY_MED_LIBELLE ) ) );
                    medicament.setSpeciality( cursor.getString( cursor.getColumnIndex( Constant.KEY_MED_SPECIALITE ) ) );
                    medicament.setDescription( cursor.getString( cursor.getColumnIndex( Constant.KEY_MED_DESCRIPTION ) ) );
                    medicaments.add( medicament );

                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while getting all the medi" );
        }
        Log.i( "medi",""+medicaments.toString() );
       return medicaments;
    }
    public boolean deleteMedi(String name){

        SQLiteDatabase db = getWritableDatabase();
        boolean state=false;
        Log.i( "tag",name );
        int row=db.delete( Constant.MEDICAMENT_TABLE,Constant.KEY_MED_LIBELLE+" = '"+name+"'",null );
        if(row!=0)
            state=true;
        return state;
    }

                    //----------------------------------------------------------------------------------------------------
                    //         traitement de la partie qui concerne les RDV                                      |
                    //----------------------------------------------------------------------------------------------------
    public boolean addRdvToDb(Rdv rdv){
        SQLiteDatabase db = getWritableDatabase();
        boolean state =false;

        Log.i( "testDate",rdv.getDateRdv()+"//***//"+rdv.getTimeRdv() );

        try {
            ContentValues values= new ContentValues(  );
            values.put( Constant.KEY_RDV_DOCTOR_NAME,rdv.getDoctorName() );
            values.put( Constant.KEY_RDV_MED_LABLES,rdv.getMedLabels() );
            values.put( Constant.KEY_RDV_DATE,rdv.getDateRdv() );
            values.put( Constant.KEY_RDV_time,rdv.getTimeRdv() );

            long  row = db.insert( Constant.RDV_TABLE,null,values );
            Log.i("val",values+"");


            if(row!=-1)
                state=true;
        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while add an RDV" );
        }
        Log.i("added",state+"");
        return  state;
    }

    public List<Rdv> getAllRdv(String date){

        SQLiteDatabase db =getReadableDatabase();
        List<Rdv> rdvList=new ArrayList<>(  );
//+" where "+Constant.KEY_RDV_DATE+" = "+date
        try{
           Cursor cursor=db.rawQuery( "select * from "+Constant.RDV_TABLE +" where "+Constant.KEY_RDV_DATE+" = '"+date.trim()+"'",
                   null,null);

           Log.i("currrTOFirst",cursor.moveToFirst()+"");
           if(cursor.moveToFirst()){
               do {
                   Rdv rdvSample = new Rdv();
                   rdvSample.setDoctorName( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_DOCTOR_NAME ) ) );
                   rdvSample.setMedLabels( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_MED_LABLES ) ) );
                   rdvSample.setDateRdv( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_DATE ) ) );
                   rdvSample.setTimeRdv( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_time ) ) );
                   rdvList.add( rdvSample );
               }while (cursor.moveToNext());

           }

        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while getting all  rdv" );

        }
        return rdvList;

    }
    public List<Rdv> getAllRdv(){

        SQLiteDatabase db =getReadableDatabase();
        List<Rdv> rdvList=new ArrayList<>(  );
//+" where "+Constant.KEY_RDV_DATE+" = "+date
        try{
            Cursor cursor=db.rawQuery( "select * from "+Constant.RDV_TABLE ,
                    null,null);

            Log.i("currrTOFirst",cursor.moveToFirst()+"");
            if(cursor.moveToFirst()){
                do {
                    Rdv rdvSample = new Rdv();
                    rdvSample.setDoctorName( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_DOCTOR_NAME ) ) );
                    rdvSample.setMedLabels( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_MED_LABLES ) ) );
                    rdvSample.setDateRdv( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_DATE ) ) );
                    rdvSample.setTimeRdv( cursor.getString( cursor.getColumnIndex( Constant.KEY_RDV_time ) ) );
                    rdvList.add( rdvSample );
                }while (cursor.moveToNext());

            }

        }catch (Exception e){
            e.printStackTrace();
            Log.i( "erreur","error while getting all  rdv" );

        }
        return rdvList;

    }



}
