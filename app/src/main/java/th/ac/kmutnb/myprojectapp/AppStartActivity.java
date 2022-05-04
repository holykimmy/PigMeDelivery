package th.ac.kmutnb.myprojectapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.TimerTask;

public class AppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity( new Intent(AppStartActivity.this, MainActivity.class));
               finish();
            }
        } ,2500);

    }
}