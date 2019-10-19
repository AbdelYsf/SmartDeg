package com.example.abdelysf.mymanuelnavigationdrawar.controller.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.DoctorFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments.MedicamentFragment;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Medicament;

public class AddMedActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout linearLayoutContainer;
    private AppCompatEditText txtLabel,txtSpeciality,txtDescription;
    private TextInputLayout inputLayoutLabel,inputLayoutSpeciality,inputLayoutDescription;
   private DataBaseHandeler dataBaseHandeler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_med );
        // configuring the focus
        linearLayoutContainer =findViewById( R.id.linearLayoutContainerAddMed );
        linearLayoutContainer.setOnClickListener( null );
        // end

//---------------------------
        // configuring the toolBar
        toolbar=findViewById( R.id.toolbarAddMedActivity );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //end of the configuration of the toolbar

 //------------------------
        // recuperation des elements
        txtLabel =findViewById( R.id.addMedLabel_textFieled );inputLayoutLabel=findViewById( R.id.addMedLabel_textFieledLayout );
        txtSpeciality=findViewById( R.id.addMedSpeciality_textFieled );inputLayoutSpeciality=findViewById( R.id.addMedSpeciality_textFieledLayout );
        txtDescription=findViewById( R.id.addMedDescription_textFieled );inputLayoutDescription=findViewById( R.id.addMedDescription_textFieledLayout );

 //-----------------------





allIsCorrect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.toolbar_item_confirm,menu );
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();break;
            case R.id.menu_doctor_toolbar_ajouter:
                if(allIsValid()){
                    dataBaseHandeler=DataBaseHandeler.getInstance( this );
                    dataBaseHandeler.AddMedicamentToDb(fillMedi());
                       Toast.makeText( this, "bien enregistré", Toast.LENGTH_SHORT ).show();
                       finish();

                }
                else
                    Toast.makeText( this, "not okay", Toast.LENGTH_SHORT ).show();
                break;
        }
        return true;
    }


    private boolean allIsValid(){
        if(TextUtils.isEmpty( txtLabel.getText().toString().trim() ) || TextUtils.isEmpty( txtSpeciality.getText().toString().trim() )
        || TextUtils.isEmpty( txtDescription.getText().toString().trim() )
                )
            return  false;
        else return  true;

    }
    private  void allIsCorrect(){
        checkDescription();
        checkLabel();
        checkSpeciality();

    }

    private  void checkLabel(){

        txtLabel.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtLabel.getText().toString().isEmpty()){
                    inputLayoutLabel.setErrorEnabled( true );
                    inputLayoutLabel.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutLabel.setErrorEnabled( false );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtLabel.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtLabel.getText().toString().isEmpty()){
                    inputLayoutLabel.setErrorEnabled( true );
                    inputLayoutLabel.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutLabel.setErrorEnabled( false );

            }
        } );
    }
    private  void checkSpeciality(){

        txtSpeciality.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtSpeciality.getText().toString().isEmpty()){
                    inputLayoutSpeciality.setErrorEnabled( true );
                    inputLayoutSpeciality.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutSpeciality.setErrorEnabled( false );

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        txtSpeciality.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtSpeciality.getText().toString().isEmpty()){
                    inputLayoutSpeciality.setErrorEnabled( true );
                    inputLayoutSpeciality.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutSpeciality.setErrorEnabled( false );


            }
        } );

    }
    private  void  checkDescription(){
        txtDescription.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(txtDescription.getText().toString().isEmpty()){
                    inputLayoutDescription.setErrorEnabled( true );
                    inputLayoutDescription.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutDescription.setErrorEnabled( false );

            }
        } );
        txtDescription.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtDescription.getText().toString().isEmpty()){
                    inputLayoutDescription.setErrorEnabled( true );
                    inputLayoutDescription.setError( "SVP, Remplissez ce champ" );
                }
                else  inputLayoutDescription.setErrorEnabled( false );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }


    private Medicament fillMedi(){
         Medicament medicament= new Medicament(  );
         medicament.setLabel( txtLabel.getText().toString() );
        medicament.setSpeciality( txtSpeciality.getText().toString() );
        medicament.setDescription( txtDescription.getText().toString() );
        return medicament;

    }
}
