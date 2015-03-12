package nadia.com.broadcastreceiver;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ngarcia on 3/4/15.
 */
public class MyPhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.v("PHONE", state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneno = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("PHONE", phoneno);
                Toast.makeText(context, "Llamada entrante " + phoneno, Toast.LENGTH_LONG).show();

                sendNotification(context);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentTitle("Nadia dice que tienes una llamada!");
        builder.setContentText("Details");

        Intent intent = new Intent(context, ActivityMain.class);
        TaskStackBuilder stackBuilder =
                TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ActivityMain.class);

        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(1, builder.build());
    }
}
