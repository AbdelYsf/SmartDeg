package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelysf.mymanuelnavigationdrawar.R;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class MedicamentViewHolder extends RecyclerView.ViewHolder {
    public TextView txtLabel,txtSpeciality;
    public LinearLayout linearLayout;

    public MedicamentViewHolder(View itemView) {
        super( itemView );
        txtLabel=itemView.findViewById( R.id.recycler_item_medicament_libelle );
        txtSpeciality=itemView.findViewById( R.id.recycler_item_medicament_speciality );
        linearLayout=itemView.findViewById( R.id.recyclerItem_layout_medicament );
    }
}
