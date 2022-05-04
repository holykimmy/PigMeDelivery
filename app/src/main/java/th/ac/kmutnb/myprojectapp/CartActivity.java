package th.ac.kmutnb.myprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent itn = getIntent();
        String myid = itn.getStringExtra("ID_Product");
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(CartActivity.this);

        String myemail = preferences2.getString("EMAIL", null);
        WebView wv = findViewById(R.id.webview_cart);
        String urlStr = "http://www.mywebapp.lnw.mn/cart.php?ID_Product=" + myid + "&op=add&email=" + myemail;
        wv.setWebViewClient(new CallBack());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDefaultFontSize(14);
        wv.getSettings().setBuiltInZoomControls(false);
        wv.getSettings().setSupportZoom(false);
        wv.loadUrl(urlStr);

    }

    public class CallBack extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://exitcart")) {
                finish();
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu); //load item from xml
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_proflie:
                Intent itn = new Intent(this, AccountActivity.class);
                startActivity(itn);
                return true;
            case R.id.nav_location:
                Intent itn2 = new Intent(this, LocationActivity.class);
                startActivity(itn2);
                return true;
            case R.id.nav_login:
                startActivity(new Intent(this, LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}