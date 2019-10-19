package com.example.abdelysf.mymanuelnavigationdrawar.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.activities.AddDoctorActivity;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters.RecyclerDoctorAdapter;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

        private FloatingActionButton fab;
        private RecyclerView recyclerView;
        private DataBaseHandeler dataBaseHandeler;



    public static DoctorFragment newInstance(){
       return  new DoctorFragment();
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate( R.layout.fragment_doctor, container, false );
        fab=view.findViewById( R.id.floatingActionButtonDoctorFragment );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getContext(), AddDoctorActivity.class );
                startActivity( intent );
            }
        } );



        //---------------------------
        // recyclerView traitement
        //----------------------------
        dataBaseHandeler=DataBaseHandeler.getInstance( getContext() );
         recyclerView=view.findViewById( R.id.recyclerViewDOcter );
        LinearLayoutManager layoutManager= new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        RecyclerDoctorAdapter doctorAdapter= new RecyclerDoctorAdapter(dataBaseHandeler.getAllDocters(),getContext() );
        recyclerView.setAdapter( doctorAdapter );
        doctorAdapter.notifyDataSetChanged();






        return  view;
    }




}
