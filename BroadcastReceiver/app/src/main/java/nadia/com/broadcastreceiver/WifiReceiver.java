package nadia.com.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by ngarcia on 3/4/15.
 */
public class WifiReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo myInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(myInfo.isConnected()){
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Log.v("Wifi",manager.getConnectionInfo().getSSID().toString());
        }
    }
}
