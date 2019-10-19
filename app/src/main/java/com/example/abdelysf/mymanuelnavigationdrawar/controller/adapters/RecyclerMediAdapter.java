package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Medicament;

import java.util.List;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class RecyclerMediAdapter extends RecyclerView.Adapter<MedicamentViewHolder> {
    private List<Medicament> medicamentList;
    private Context context;
    private int currentSelected=0;
    private DataBaseHandeler dataBaseHandeler;
    public RecyclerMediAdapter(List<Medicament> medicamentList, Context context) {
        this.medicamentList = medicamentList;
        this.context = context;
    }

    @Override
    public MedicamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.recycler_view_medi_item,parent,false );
        return new MedicamentViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final MedicamentViewHolder holder, int position) {
        Medicament medicamentSample =medicamentList.get( position );
        holder.txtLabel.setText( medicamentSample.getLabel() );
        holder.txtSpeciality.setText( "Specialité :"+medicamentSample.getSpeciality() );
        holder.linearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelected=holder.getAdapterPosition();
                showDetailsAlert( holder.getAdapterPosition() );

              //  Toast.makeText( context, medicamentList.get( holder.getAdapterPosition() ).getSpeciality()+"", Toast.LENGTH_SHORT ).show();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return medicamentList.size();
    }

    private void showDetailsAlert(int position) {


        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setCancelable( true );
        // on recupere ce service pour associer le layout a la vu
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.medi_popup, null );
        TextView txtLabel, txtSpe, txtDesc;
        txtLabel = view.findViewById( R.id.popmediLabel );
        txtSpe = view.findViewById( R.id.popmediSpecialie );
        txtDesc = view.findViewById( R.id.popmediDescription );
        builder.setView( view );

        txtDesc.setText( medicamentList.get( position ).getDescription() );
        txtLabel.setText( medicamentList.get( position ).getLabel() );
        txtSpe.setText( medicamentList.get( position ).getSpeciality() );

        builder.setPositiveButton( "ok", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            }

        } );
        builder.setNegativeButton( "supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( context, "" + currentSelected, Toast.LENGTH_SHORT ).show();
                dataBaseHandeler = DataBaseHandeler.getInstance( context );
                boolean b = dataBaseHandeler.deleteMedi( medicamentList.get( currentSelected ).getLabel() );
                if (b) Toast.makeText( context, "bien supprimé", Toast.LENGTH_SHORT ).show();
                notifyItemRemoved( currentSelected );
                notifyItemRangeChanged( currentSelected, medicamentList.size() );


            }
        } );
        alertDialog = builder.create();
        alertDialog.show();

    }


}
