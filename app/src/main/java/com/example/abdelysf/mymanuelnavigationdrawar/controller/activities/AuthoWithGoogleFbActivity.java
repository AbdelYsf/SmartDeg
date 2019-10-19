package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Constant;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;


public class AuthoWithGoogleFbActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    CallbackManager call;
    LoginButton login;//button de logine de facebook





    private static final String TAG = AuthoWithGoogleFbActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 420;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    SignInButton glogin;//botton de logine de google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        //Utilisation de facebook SDK pour initialiser le SDK facebook
        //et specifier le context d'application

        FacebookSdk.sdkInitialize( getApplicationContext() );

       // cette code est juste pour obtient the key hash

      /*  try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.abdelysf.mymanuelnavigationdrawar",
                    PackageManager.GET_SIGNATURES );
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                Log.i( "KeyHash:",android.util.Base64.encodeToString( md.digest(),android.util.Base64.DEFAULT ) );
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e)
        {}*/
        {

            AppEventsLogger.activateApp( this );
            setContentView( R.layout.activity_autho_with_google_fb );

            call = CallbackManager.Factory.create();
            final Intent mainActivity = new Intent( this,MainActivity.class );
            // Definer la button de connexion avec facebook
            login = (LoginButton) findViewById( R.id.loginFB );
            login.setReadPermissions( "email" );
            login.registerCallback( call,new FacebookCallback<LoginResult>() {
                @Override
                //c'est la connexion est etabli avec succes en facebook
                public void onSuccess(LoginResult loginResult) {

                    loginResult.getAccessToken().getToken();
                    //demarer l'activity d'acceui de projet
                    Intent intent = new Intent( AuthoWithGoogleFbActivity.this,MainActivity.class );

                    startActivity( intent );
                    finish();


                }
                //c'est en annule la connexion
                @Override
                public void onCancel() {


                }
                //C'est la connexion est echaoué
                @Override
                public void onError(FacebookException error) {
                    Toast.makeText( AuthoWithGoogleFbActivity.this, "la connecxion avec facebook est echoué", Toast.LENGTH_SHORT ).show();

                }
            } );


        }

        //configuration de google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //definire la button de connexion par google


        glogin = (SignInButton) findViewById( R.id.loginGoogle );
        glogin.setOnClickListener(this);



    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);


    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult( requestCode,resultCode,data );
        call.onActivityResult( requestCode,resultCode,data );


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result);
        }
    }
    //le choix de clique sur le bouton de connection ou deconnection de google
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loginGoogle:
                signIn();
                break;


        }
    }
    // la methode de connection de de google
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //la methode pour deconnexion par google
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        finish();
                    }
                });
    }

    //des traitement a faire c'est la connexion est etablie par  google
    //par exemple recuperer l'email et le nom d'utilisateure
    private void handleGPlusSignInResult(GoogleSignInResult result) {
        Log.d( TAG,"handleSignInResult:" + result.isSuccess() );
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            Intent MainAtivity = new Intent( this,MainActivity.class );
            startActivity( MainAtivity );
            finish();

        }

    }


    public void login(View view) {
        Intent login = new Intent( this,LoginActivity.class );
        startActivity( login );
      //  finish();
    }

    public void register(View view) {
        Intent register = new Intent( this,registerActivity.class );
        startActivity( register );
       // finish();
    }



}
