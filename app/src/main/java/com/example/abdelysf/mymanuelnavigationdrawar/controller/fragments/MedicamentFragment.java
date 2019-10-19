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
import com.example.abdelysf.mymanuelnavigationdrawar.controller.activities.AddMedActivity;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters.RecyclerMediAdapter;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentFragment extends Fragment {
    DataBaseHandeler dataBaseHandeler;
    RecyclerView recyclerView;


      FloatingActionButton fab;
    public  static  MedicamentFragment newInstance(){
        return (new MedicamentFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate( R.layout.fragment_medicament, container, false );
        fab=view.findViewById( R.id.floatingActionButtonMedicamentFragment );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getContext(), AddMedActivity.class );
                startActivity( intent );
            }
        } );


        //---------------------------
        // recyclerView traitement
        //-------------------------
        dataBaseHandeler=DataBaseHandeler.getInstance( getContext() );
        LinearLayoutManager layoutManager= new LinearLayoutManager( getContext());
        recyclerView=view.findViewById( R.id.recyclerViewMedicament );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        RecyclerMediAdapter mediAdapter= new RecyclerMediAdapter( dataBaseHandeler.getAllMedi(),getContext() );
        recyclerView.setAdapter( mediAdapter );
        mediAdapter.notifyDataSetChanged();

























        return view;
    }

}
