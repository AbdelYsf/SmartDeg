package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelysf.mymanuelnavigationdrawar.R;

/**
 * Created by abdel ysf on 28/01/2018.
 */

public class RdvViewHolder extends RecyclerView.ViewHolder {
   public  TextView txtRdvDoctor,txtRdvTime,txtRdvMedi;
    public LinearLayout linearLayout;
    public RdvViewHolder(View itemView) {
        super( itemView );
        txtRdvDoctor=itemView.findViewById( R.id.recycler_item_rdv_doctorName );
        txtRdvTime=itemView.findViewById( R.id.recycler_item_rdv_time );
        txtRdvMedi=itemView.findViewById( R.id.recycler_item_rdv_medi );
        linearLayout = itemView.findViewById( R.id.recyclerItem_layout_rdv );
    }
}
