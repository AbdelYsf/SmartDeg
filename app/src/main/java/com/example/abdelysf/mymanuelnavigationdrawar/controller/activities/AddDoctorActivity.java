package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.DoctorFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDoctorActivity extends AppCompatActivity {

    DataBaseHandeler dataBaseHandeler;
        private Toolbar toolbar;
        private LinearLayout linearLayoutContainer;
     private AppCompatEditText txtNom,txtPrenom,txtTele,txtAdress,txtEmail;
    private TextInputLayout inputLayoutNom,inputLayoutPrenom,inputLayoutTele,inputLayoutAdress,inputLayoutEmail;

    private boolean nomIsEmpty,prenomIsEmpty,teleIsEmpty,adressIsEmpty,emailIsEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_doctor );
        dataBaseHandeler=DataBaseHandeler.getInstance( this );
                 // recuperation des elements
        txtNom=  findViewById( R.id.addDoctorNom_textFieled );
        inputLayoutNom=findViewById( R.id.addDoctorNom_textFieledLayout );
        txtPrenom=findViewById( R.id.addDoctorPrenom_textFieled );inputLayoutPrenom=findViewById( R.id.addDoctorPrenom_textFieledLayout );
        txtTele=findViewById( R.id.addDoctorPhone_textFieled );inputLayoutTele=findViewById( R.id.addDoctorPhone_textFieledLayout );
        txtAdress=findViewById( R.id.addDoctorAdress_textFieled );inputLayoutAdress=findViewById( R.id.addDoctorAdress_textFieledLayout );
        txtEmail=findViewById( R.id.addDoctorEmail_textFieled );inputLayoutEmail=findViewById( R.id.addDoctorEmail_textFieledLayout );
        linearLayoutContainer= findViewById( R.id.linearLayoutContainer );
               // fin d recuperation
        linearLayoutContainer.setOnClickListener( null );// to desable focus
                 // configuring the toolBar
        toolbar=findViewById( R.id.toolbarAddDoctorActivity );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled(true);
                 //end of the configuration of the toolbar

        allIsCorrect();

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
                if(allIsValid()  )
                {   Log.i( "tr",checkIfAlreadyExists()+"" );
                  if(checkIfAlreadyExists()){
                      //  Toast.makeText( this, ""+(isValidEmail(txtEmail.getText().toString())), Toast.LENGTH_SHORT ).show();
                      dataBaseHandeler.addDoctorToDb( fillDoctor() )  ;
//                      Intent intent = new Intent( this, DoctorFragment.class );
//                      startActivity( intent );
                      finish();
                  }else {
                      View view =findViewById( R.id.docView );
                      Snackbar.make( view,"ce nom et prenom deja existe!",Snackbar.LENGTH_LONG ).show();
                  }
                }

                else
                    Toast.makeText( this, "not ok", Toast.LENGTH_SHORT ).show();
                break;
        }
        return true;
    }



                                                            //++++++++++++++++++++++++++++++++++++++++++++++++++
                                                            // traitement de validité des entrées               +
                                                            //+++++++++++++++++++++++++++++++++++++++++++++++++++

    private  void allIsCorrect(){

     checkAdress();
     checkEmail();
     checkNom();
     checkPrenom();
     checkTele();

    }
    private void checkNom(){

        txtNom.addTextChangedListener( new TextWatcher() {
            boolean b;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtNom.getText().toString().isEmpty())
                {
                    inputLayoutNom.setErrorEnabled( true );
                    inputLayoutNom.setError( "SVP,remplissez ce champ " );

                }
                else inputLayoutNom.setErrorEnabled( false );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtNom.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtNom.getText().toString().isEmpty())
                {
                    inputLayoutNom.setErrorEnabled( true );
                    inputLayoutNom.setError( "SVP,remplissez ce champ " );

                }
                else  {
                    inputLayoutNom.setErrorEnabled( false );

                }

            }
        } );

    }
    private void checkPrenom(){

        txtPrenom.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPrenom.getText().toString().isEmpty())
                {
                    inputLayoutPrenom.setErrorEnabled( true );
                    inputLayoutPrenom.setError( "SVP,remplissez ce champ " );
                }
                else inputLayoutPrenom.setErrorEnabled( false );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtPrenom.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtPrenom.getText().toString().isEmpty())
                {
                    inputLayoutPrenom.setErrorEnabled( true );
                    inputLayoutPrenom.setError( "SVP,remplissez ce champ " );

                }
                else  {
                    inputLayoutPrenom.setErrorEnabled( false );

                }

            }
        } );

    }
    private void checkAdress(){

        txtAdress.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtAdress.getText().toString().isEmpty())
                {
                    inputLayoutAdress.setErrorEnabled( true );
                    inputLayoutAdress.setError( "SVP,remplissez ce champ " );
                }
                else inputLayoutAdress.setErrorEnabled( false );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtAdress.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtAdress.getText().toString().isEmpty())
                {
                    inputLayoutAdress.setErrorEnabled( true );
                    inputLayoutAdress.setError( "SVP,remplissez ce champ " );

                }
                else  {
                    inputLayoutAdress.setErrorEnabled( false );

                }

            }
        } );

    }
    private void checkTele(){

        txtTele.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtTele.getText().toString().isEmpty())
                {
                    inputLayoutTele.setErrorEnabled( true );
                    inputLayoutTele.setError( "SVP,remplissez ce champ " );

                }
                else if(!isValidMobile( txtTele.getText().toString()  ))  {
                    inputLayoutTele.setErrorEnabled( true );
                    inputLayoutTele.setError( "ce numero n'est pas valide  " );

                }else {
                    inputLayoutTele.setErrorEnabled( false );

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtTele.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtTele.getText().toString().isEmpty())
                {
                    inputLayoutTele.setErrorEnabled( true );
                    inputLayoutTele.setError( "SVP,remplissez ce champ " );

                } else if(!isValidMobile( txtTele.getText().toString()  ))  {
                    inputLayoutTele.setErrorEnabled( true );
                    inputLayoutTele.setError( "ce numero n'est pas valide  " );

                }
                else  {
                    inputLayoutTele.setErrorEnabled( false );

                }

            }
        } );

    }
    private void checkEmail(){

        txtEmail.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtEmail.getText().toString().isEmpty())
                {
                    inputLayoutEmail.setErrorEnabled( true );
                    inputLayoutEmail.setError( "SVP,remplissez ce champ " );


                }
                else  {
                    inputLayoutEmail.setErrorEnabled( false );

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isValidEmail( txtEmail.getText().toString() ))
                    inputLayoutEmail.setError( "cet email est invalide " );




            }
        } );
        txtEmail.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtTele.getText().toString().isEmpty())
                {
                    inputLayoutEmail.setErrorEnabled( true );
                    inputLayoutEmail.setError( "SVP,remplissez ce champ " );

                }
                else  {
                    inputLayoutEmail.setErrorEnabled( false );

                }

            }
        } );








    }
    public boolean allIsValid(){

        boolean check=
                txtNom.getText().toString().isEmpty()|| txtPrenom.getText().toString().isEmpty()
                ||  txtAdress.getText().toString().isEmpty() || txtTele.getText().toString().isEmpty() ||
                 txtEmail.getText().toString().isEmpty();

        boolean etat=!check && isValidEmail(txtEmail.getText().toString()) && isValidMobile( txtTele.getText().toString() );
        Log.i("etat1",(!check)+"");
        Log.i("etat2",(isValidEmail(txtEmail.getText().toString()) && isValidMobile( txtTele.getText().toString() ))+"");
        if(  etat /*&& isValidName( txtNom.getText().toString() )*/ ){
            return  true;
        }


            return  false;

    }

    private boolean isValidEmail(String email){

        /*String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();*/

            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private boolean isValidMobile(String phone) {
       // return android.util.Patterns.PHONE.matcher(phone).matches();

        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();


    }
    private  boolean isValidName(String nom){
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcherNom = pattern.matcher(nom);
        return matcherNom.matches();



    }


  //---------------------------------------------------
    // traitement de la base de donnée
    //--------------------------------------------------

    private Doctor fillDoctor(){
        Doctor doctor=new Doctor(  );
        doctor.setNom( txtNom.getText().toString() );
        doctor.setPrenom(txtPrenom.getText().toString());
        doctor.setTele( txtTele.getText().toString() );
        doctor.setEmail( txtEmail.getText().toString() );
        doctor.setAdresse( txtAdress.getText().toString() );
        return  doctor;


    }
    private boolean checkIfAlreadyExists(){
        List<Doctor> doctorList= new ArrayList<>(  );
      doctorList=  dataBaseHandeler.getAllDocters();
      Doctor doctor = fillDoctor();
      for (Doctor d :doctorList){
          if(d.getNom().trim().equals( doctor.getNom().trim()) && d.getPrenom().trim().equals(  doctor.getPrenom().trim()))
               return  false;
      }
      return  true;
    }



}
