package nadia.com.preferences;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class ActivityPrivacyPolicy extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        WebView mWebView = null;
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.loadUrl("file:///android_asset/PrivacyPolicy.html");
    }
}
