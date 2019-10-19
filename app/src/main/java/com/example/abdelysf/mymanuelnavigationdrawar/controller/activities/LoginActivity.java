package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.User;
import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Constant;

public class LoginActivity extends AppCompatActivity {
    private DataBaseHandeler db;
    private EditText textNom,textPwd;
    private CheckBox checkBoxRemember;
    private SharedPreferences MysharedPreferences;
    private SharedPreferences.Editor Myeditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        textNom = findViewById( R.id.editTextLoginNom );
        textPwd=findViewById( R.id.editTextLoginPwd );
        checkBoxRemember=findViewById( R.id.checkBoxLoginRememberMe );

        MysharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );//on defini LE sharedPre "il y a une autre methode ms c la plus simple
        Myeditor =MysharedPreferences.edit();
        // on declare mon editeur qui va me permet de ajouter les elements a mon sharedPre par
        // la xfonction "myeditor.putXXXX(key,value)" suivé de myeditor.commit;
        if(checkSharedPreferences()){
           toMainActivity();
        }

    }



//    public void ToEnregistrerActivity(View view) {
//        Intent IntentRegisterActivity = new Intent( getApplicationContext(), registerActivity.class);
//        startActivity( IntentRegisterActivity );
//    }

    public void authenticated(View view) {
        // si tous les champs sont bien remplis
        if(allIsCorrect()){

            User user= new User(  );
            user.setNom( textNom.getText().toString() );
            user.setPwd( textPwd.getText().toString() );

            db=DataBaseHandeler.getInstance( getApplication() );
            // si l'auth se passe correctement on va aller a le MainActivity
            if(db.authentification( user ))
            {    if(checkBoxRemember.isChecked()){
                fillFeileds();// on va recuperé les champ si le chekbox est accoché
                // et puis on va entrer a le mainActivity
                 toMainActivity();
                  }
                else{
                // si non on va reinitialiser le charedPre
                ViderSharedPre();
                toMainActivity();
                 }
            }
            else{

                textNom.setError( "nom ou  mot de passe incorrect" );
                textPwd.setError( "nom ou  mot de passe incorrect" );
                Toast.makeText( this, " nom ou mot de passe incorrect", Toast.LENGTH_SHORT ).show();

            }


        }

    }

    private void fillFeileds() {
        // on va sauvegarder les fieleds
        String userNom=textNom.getText().toString();
        String pwd= textPwd.getText().toString();
        Myeditor.putString( Constant.KEY_sharedPre_rememberMe,"true" );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_username,userNom );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_password,pwd );Myeditor.commit();
    }

    private boolean allIsCorrect() {
        boolean state=true;
        if(TextUtils.isEmpty( textNom.getText().toString().trim() ))
        {
            textNom.setError( "tous les fieled doivent etre remplis" );
            state=false;
        }else if (TextUtils.isEmpty( textPwd.getText().toString().trim() ))
        {
            textPwd.setError( "tous les fieled doivent etre remplis" );
            state=false;
        }
     return state;
    }
     /**
      * cette methode va consulter le sharedPreferences
      */
    private  boolean checkSharedPreferences(){
        boolean state = false;
        // on va recuperer les element a partir du sharedPre
        String userNom=MysharedPreferences.getString( Constant.KEY_sharedPre_username,"" );
        String pwd=MysharedPreferences.getString( Constant.KEY_sharedPre_password,"" );
        String checkBox= MysharedPreferences.getString( Constant.KEY_sharedPre_rememberMe,"false" );
        textNom.setText( userNom );
        textPwd.setText( pwd );
        // si le utilisateur a ete choisi "rememberme durrant l'auth  ..cette methode va signler de ne pas lui afficherl'activity de l'athu a nouveau
        if(checkBox.equals( "true" ))
        {
            checkBoxRemember.setChecked( true );
            state=true;
        }
        else checkBoxRemember.setChecked( false );
       return  state;

    }
    private  void ViderSharedPre(){
        Myeditor.putString( Constant.KEY_sharedPre_username,"" );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_password,"" );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_rememberMe,"false" );Myeditor.commit();
    }
    private  void toMainActivity(){
        Intent toMain = new Intent( getApplicationContext(), MainActivity.class);
        startActivity( toMain );
        finish();
    }

}
