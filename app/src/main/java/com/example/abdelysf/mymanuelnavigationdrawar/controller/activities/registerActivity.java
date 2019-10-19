package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.User;

public class registerActivity extends AppCompatActivity {
    EditText textNom,textPrenom,textEmail,textPwd,textConfirmPwd;
    DataBaseHandeler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        textConfirmPwd= findViewById( R.id.txtRegisterConfirmPassword );
        textEmail=findViewById( R.id.txtRigisterEmail );
        textNom=findViewById( R.id.txtRegisterNom );
        textPrenom= findViewById( R.id.txtRegisterPrenom );
        textPwd=findViewById( R.id.txtRegisterPassword );


    }

    public void regiterMe(View view) {
        if(allIsCorrect())
        {
        db= DataBaseHandeler.getInstance( this );
        User user= new User(  );
        user.setNom( textNom.getText().toString() );
        user.setPrenom( textPrenom.getText().toString() );
        user.setEmail(  textEmail.getText().toString());
        user.setPwd( textPwd.getText().toString() );
            if(db.register(  user)){
                Toast.makeText( this, "bien enregistré", Toast.LENGTH_SHORT ).show();
                Intent intent= new Intent( this,LoginActivity.class );
                startActivity( intent );
                finish();
            }

        }

    }

    private boolean allIsCorrect() {
        boolean correct= true;
              if     (
                              TextUtils.isEmpty( textNom.getText().toString().trim()) ||TextUtils.isEmpty( textPrenom.getText().toString().trim()  )
                              ||TextUtils.isEmpty( textEmail.getText().toString().trim())||TextUtils.isEmpty( textPwd.getText().toString().trim())
                              || TextUtils.isEmpty( textConfirmPwd.getText().toString().trim())
                      )  {


                  textConfirmPwd.setError( "tous les fieleds doit etre remplis" );
                  textPwd.setError( "tous les fieleds doit etre remplis" );
                  textNom.setError( "tous les fieleds doit etre remplis" );
                  textPrenom.setError( "tous les fieleds doit etre remplis" );
                  textEmail.setError( "tous les fieleds doit etre remplis" );
                  correct=false;


              }

              else if (!TextUtils.equals( textConfirmPwd.getText().toString(),textPwd.getText().toString() )){
                  correct=false;
                  Toast.makeText( this, "les mot de passe ne sont pas identique ", Toast.LENGTH_SHORT ).show();
                  textConfirmPwd.setError( "ce mot de passe n'est pas identique" );

              }
              else if(!isValidEmail( textEmail.getText().toString() ))
              {
                  correct=false;
                  Toast.makeText( this, "email incorrect ", Toast.LENGTH_SHORT ).show();
                  textEmail.setError( " Ca ne semble pas a un email !!" );

              }
           return  correct;
    }
    private boolean isValidEmail(String email){

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
}
