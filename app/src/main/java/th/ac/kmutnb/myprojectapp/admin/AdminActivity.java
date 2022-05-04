package th.ac.kmutnb.myprojectapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import th.ac.kmutnb.myprojectapp.LoginActivity;
import th.ac.kmutnb.myprojectapp.R;

public class AdminActivity extends AppCompatActivity {
    Button btnedit,btnadd,btnorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnedit = findViewById(R.id.editfood);
        Intent itn = new Intent(this,AdminAllfood.class);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(itn);
            }
        });

        btnadd = findViewById(R.id.addfood);
        Intent itn2 = new Intent(this,AdminAddfood.class);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(itn2);
            }
        });

        btnorder = findViewById(R.id.orderfood);
        Intent itn3 = new Intent(this,AdminOrder.class);
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(itn3);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_admin, menu); //load item from xml
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_login:
                startActivity(new Intent(this, LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);

        }

    }

}