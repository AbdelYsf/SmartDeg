package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap.MapsActivity;
import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.CalandarFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.DoctorFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.MedicamentFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Constant;

public class MainActivity extends AppCompatActivity  implements   NavigationView.OnNavigationItemSelectedListener {
    //  pour le design ... on va faire le lien entre les element du menu de navigation
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //pour les fragment
    //-------
    // 2.1 - declaration des fragments traité par NavigationView
    private Fragment fragmentCalandar;
    private Fragment fragmentDoctor;
    private Fragment fragmentMedicament;

    // 2.2 - Identify each fragment with a number
    private static final int FRAGMENT_calander = 0;
    private static final int FRAGMENT_medicament = 1;
    private static final int FRAGMENT_docotor = 2;


    // 3.2 gestion des click sur le menu de la toolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menu_activity_main_toolbar_map:
                Intent toMapIntent = new Intent( this, MapsActivity.class );
                startActivity( toMapIntent );
                break;
        }
        return true;
    }
    // 3.1configuration du menu de la toolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on associer le menu a la toolBar
        getMenuInflater().inflate( R.menu.activity_main_menu_toolbar,menu );
        return true;
    }
    //----------------//




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //--------------------------------------
        // 1.6 - appel a les methodes de configuration des vue;
        //--------------------------------------
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        //------------
        // fin d'appele
        //-----------

        // affichage du premier fragment:
        this.showFirstFragment();

    }

    @Override
    //redefinition de cette methode afin de fermer la NavigationView quand'on click sur le button de retoure
    public void onBackPressed() {
        // 1.5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen( GravityCompat.START )) {
            this.drawerLayout.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }
                                                                // ---------------------
                                                                // CONFIGURATION des composontes de la vue
                                                                // ---------------------

    // 1.1 configugation de ToolBar
    private void configureToolBar() {
        this.toolbar = findViewById( R.id.activity_main_toolbar );
        setSupportActionBar( toolbar );
        // Nous récupérons et configurons la Toolbar.
    }

    //  1.2 configuration du DrawerLayout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById( R.id.activity_main_drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        //Nous récupérons le DrawerLayout, et créons à partir de lui et de la toolbar, le  bouton "Hamburger".
    }

    // 1.3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById( R.id.activity_main_nav_view );
        // on ajoute le listner a les elements de la navigation view
        navigationView.setNavigationItemSelectedListener( this );
        // Nous récupérons la NavigationView afin qu'elle puisse s'enregistrer au listener de l'activité (via l'interfaceNavigationView.OnNavigationItemSelectedListener), nous permettant ainsi de récupérer les clics du menu.
    }

                                                                    // ---------------------
                                                                    //  fin de la CONFIGURATION
                                                                    // ---------------------


    @Override
    // 2.6 appelée à chaque fois qu'un utilisateur clique sur un élément du menu, afin d'afficher le fragment correspondant.
// on redefinie la methode proposé par l'interface que nous venons d'implementer( NavigationView.OnNavigationItemSelectedListener)
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // 1.4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_news:
                this.showFragment( FRAGMENT_calander );

                break;
            case R.id.activity_main_drawer_doctor:
                this.showFragment( FRAGMENT_docotor );
                break;
            case R.id.activity_main_drawer_medicament:
                this.showFragment( FRAGMENT_medicament );
                break;
            case R.id.activity_main_drawer_deconnect :
                desconnect();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer( GravityCompat.START );

        return true;
    }

                 //-------------------------------------------------------------------
                // 2.4  Pour chacun de nos fragments, cette méthode permet de les instancier pour ensuite les afficher.
                //---------------------------------------------------------------

    private void showCalandarFragment(){

        if (this.fragmentCalandar == null) this.fragmentCalandar = CalandarFragment.newInstance();
        // appele a la methode generique qui se charge de l'affichage
        this.startTransactionFragment(this.fragmentCalandar);
    }
    private void showDoctorFragment(){
        if (this.fragmentDoctor == null) this.fragmentDoctor = DoctorFragment.newInstance();
        this.startTransactionFragment(this.fragmentDoctor);
    }

    private void showMedicamentFragment(){
        if (this.fragmentMedicament == null) this.fragmentMedicament = MedicamentFragment.newInstance();
        this.startTransactionFragment(this.fragmentMedicament);
    }

    private void desconnect(){
        SharedPreferences MysharedPreferences=PreferenceManager.getDefaultSharedPreferences( this );;
        SharedPreferences.Editor Myeditor=MysharedPreferences.edit();
        Myeditor.putString( Constant.KEY_sharedPre_username,"" );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_password,"" );Myeditor.commit();
        Myeditor.putString( Constant.KEY_sharedPre_rememberMe,"false" );Myeditor.commit();


        Intent toLoginIntent= new Intent( getApplicationContext(),AuthoWithGoogleFbActivity.class );
        startActivity( toLoginIntent );
        finish();



    }
                                        //---------------
                                        // fin 2.4
                                        //-------------


    // 2.3 - methode generique sera appelée à chaque fois que nous souhaiterons que le FragmentManager affiche un de nos trois fragments dans le FrameLayout.
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace( R.id.activity_main_frame_layout, fragment ).commit();
        }

    }
    //------------------------------
    // 2.5- cette méthode (generique aussi ) permet de en fonction de l'identifiant passé en paramètre d'afficher le bon fragment.
            // Nous nous servirons dorénavant uniquement de cette méthode pour afficher nos différents fragments au sein de l'activité.
    //--------------------------------
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_calander :
                this.showCalandarFragment();
                break;
            case FRAGMENT_docotor:
                this.showDoctorFragment();
                break;
            case FRAGMENT_medicament:
                this.showMedicamentFragment();
                break;
            default:
                break;
        }
    }

    // 2.7- affichage du premier fragment lors du lencement de l'app
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_calander);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }



}
