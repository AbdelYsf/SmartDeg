package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Doctor;

import java.util.List;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class RecyclerDoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder>{
    private List<Doctor> doctorList;
    private Context context;
    private int currentSelected=0;
    private DataBaseHandeler dataBaseHandeler;

    public RecyclerDoctorAdapter(List<Doctor> doctors, Context context) {
        this.doctorList = doctors;
        this.context = context;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.recycler_view_doctor_item,parent,false );
        return  new DoctorViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final DoctorViewHolder holder, int position) {
        Doctor doctorSample= doctorList.get( position );
        holder.txtNomPrenom.setText( doctorSample.getNom()+", "+doctorSample.getPrenom() );
        holder.txtAdress.setText( "Adresse: "+doctorSample.getAdresse() );
        holder.linearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelected=holder.getAdapterPosition();
              showDetailsAlert( holder.getAdapterPosition() );

               // Toast.makeText( context, doctorList.get( holder.getAdapterPosition() ).getNom()+"", Toast.LENGTH_SHORT ).show();
            }
        } );





    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    private void showDetailsAlert(int position){
        AlertDialog alertDialog;
        AlertDialog.Builder builder= new AlertDialog.Builder(  context);
        builder.setCancelable( true );
        // on recupere ce service pour associer le layout a la vu

        LayoutInflater inflater= (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view= inflater.inflate( R.layout.doctor_popup,null);
        TextView txtnom,txttele,txtadr,txtmail;
        txtnom= view.findViewById( R.id.popDocTxtNomPrenom );
        //txttele = view.findViewById( R.id.popDocTxtTele );
        txtmail = view.findViewById( R.id.popDocTxtEmail );
        txtadr = view.findViewById( R.id.popDocTxtAdresse );

        //on remplir les champs
        txtadr.setText(  doctorList.get( position ).getAdresse());
        txtmail.setText( doctorList.get( position ).getEmail() );
        //txttele.setText( doctorList.get( position ).getTele() );
        txtnom.setText( doctorList.get( position ).getNom()+" "+doctorList.get( position ).getPrenom() );

        //
        builder.setView( view );
        builder.setPositiveButton( "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        } );

        builder.setNegativeButton( "supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( context, ""+currentSelected, Toast.LENGTH_SHORT ).show();
                dataBaseHandeler= DataBaseHandeler.getInstance( context );
               boolean b= dataBaseHandeler.deleteDoctor( doctorList.get( currentSelected ).getNom() );
               if(b) Toast.makeText( context, "bien supprimé", Toast.LENGTH_SHORT ).show();
               notifyItemRemoved( currentSelected );
               notifyItemRangeChanged( currentSelected,doctorList.size() );


            }
        } );
        alertDialog = builder.create();
        alertDialog.show();


    }
}
