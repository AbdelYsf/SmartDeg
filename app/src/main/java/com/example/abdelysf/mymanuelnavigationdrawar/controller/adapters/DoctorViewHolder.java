package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelysf.mymanuelnavigationdrawar.R;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class DoctorViewHolder extends RecyclerView.ViewHolder {

   public TextView txtNomPrenom,txtAdress;
    public LinearLayout linearLayout;

    public DoctorViewHolder(View itemView) {
        super( itemView );
        txtNomPrenom=itemView.findViewById( R.id.recycler_item_doctor_name );
        txtAdress=itemView.findViewById( R.id.recycler_item_doctor_adresse );
        linearLayout=itemView.findViewById( R.id.recyclerItem_layout_doctor );
    }
}
