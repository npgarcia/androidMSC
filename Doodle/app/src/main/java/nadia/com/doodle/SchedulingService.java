package nadia.com.doodle;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ngarcia on 4/14/15.
 */

public class SchedulingService extends IntentService {
    public static final String TAG = "Scheduling	Demo";
    //	An	ID	used	to	post	the	notification.
    public static final int NOTIFICATION_ID = 1;
    //	The	string	the	app	searches	for	in	the	Google	home	page	content.	If	the	app	finds
    //	the	string,	it	indicates	the	presence	of	a	doodle.
    public static final String SEARCH_STRING = "doodle";
    //	The	Google	home	page	URL	from	which	the	app	fetches	content.
    //	You	can	find	a	list	of	other	Google	domains	with	possible	doodles	here:
    //	http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public SchedulingService() {
        super("SchedulingService");
    }

    private InputStream downloadUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    private String readIt(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for (String line = reader.readLine(); line != null; line = reader.readLine())
            builder.append(line);
        reader.close();
        return builder.toString();
    }

    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str = "";
        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ActivityMain.class), 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.doodle_alert))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String urlString = URL;
        String result = "";
        // Try to connect to the Google homepage and download content.
        try {
            result = loadFromNetwork(urlString);
        } catch (IOException e) {
            Log.i(TAG, getString(R.string.connection_error));
        }
        // If the app finds the string "doodle" in the Google home page content, it
        // indicates the presence of a doodle. Post a "Doodle Alert" notification.
        if (result.indexOf(SEARCH_STRING) != -1) {
            sendNotification(getString(R.string.doodle_found));
        } else {
            sendNotification(getString(R.string.no_doodle));
        }
        // Release the wake lock provided by the BroadcastReceiver.
        AlarmReceiver.completeWakefulIntent(intent);
    }

}
