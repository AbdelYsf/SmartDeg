package com.example.abdelysf.mymanuelnavigationdrawar.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.GoogleMap.MapsActivity;
import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Dao.DataBaseHandeler;
import com.example.abdelysf.mymanuelnavigationdrawar.model.Rdv;

import java.util.List;

/**
 * Created by abdel ysf on 28/01/2018.
 */

public class RecyclerRdvAdapter extends RecyclerView.Adapter<RdvViewHolder> {
    private List<Rdv> rdvList;
    private Context context;
    private DataBaseHandeler dataBaseHandeler;

    public RecyclerRdvAdapter(List<Rdv> rdvList, Context context) {
        this.rdvList = rdvList;
        this.context = context;
    }

    @Override
    public RdvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.recycler_view_rdv_item,parent,false );
        return  new RdvViewHolder( view );

    }

    @Override
    public void onBindViewHolder(RdvViewHolder holder, final int position) {
        Rdv rdvSample=rdvList.get( position );

      holder.txtRdvDoctor.setText( rdvSample.getDoctorName() );
      holder.txtRdvTime.setText("temps : "+ rdvSample.getTimeRdv() );
      holder.txtRdvMedi.setText( "produits a presenter: "+rdvSample.getMedLabels() );
      holder.linearLayout.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (isNetworkAvailable()){


                  dataBaseHandeler =DataBaseHandeler.getInstance( context );
                  String[] stringsTab= rdvList.get( position ).getDoctorName().split( "," );
                  String address= dataBaseHandeler.getDoctorAddress(  stringsTab[0]);
                  Intent intentToMap = new Intent( context,MapsActivity.class );
                  intentToMap.putExtra( "address",address );
                  context.startActivity( intentToMap );
              }else
                  Toast.makeText( context, "pas d'acces a l'internet", Toast.LENGTH_LONG ).show();

          }
      } );

    }

    @Override
    public int getItemCount() {
        return rdvList.size();

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE  );
        if (connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null){
                return info.isConnected();
            }
        }
        return false;
    }
}
