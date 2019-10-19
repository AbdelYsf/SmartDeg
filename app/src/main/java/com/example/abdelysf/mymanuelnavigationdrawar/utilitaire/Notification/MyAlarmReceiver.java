package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import com.example.abdelysf.mymanuelnavigationdrawar.R;
import com.example.abdelysf.mymanuelnavigationdrawar.controller.activities.MainActivity;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // on recupere le ringTon par defaut du tele
        MediaPlayer mp = MediaPlayer.create( context, Settings.System.DEFAULT_NOTIFICATION_URI );

        Intent intentToMainActivity= new Intent( context.getApplicationContext(), MainActivity.class );
        PendingIntent pendingIntent= PendingIntent.getActivity( context.getApplicationContext(),1,intentToMainActivity,0 );

        Notification notification = new Notification.Builder( context).setContentTitle( intent.getStringExtra( "title" )).
                setStyle(new Notification.BigTextStyle().bigText( intent.getStringExtra( "content" ) ))
                .setContentText( intent.getStringExtra( "content" ))
                .setSmallIcon( R.drawable.ic_date )
                .setContentIntent( pendingIntent )
                .build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify( 2,notification );

        mp.start();




    }
}
