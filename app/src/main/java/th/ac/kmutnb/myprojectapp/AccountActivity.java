package th.ac.kmutnb.myprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    TextView tvEmail,tvPassword,tvName,tvTel;
    SharedPreferences preferences;
    Button btnLogout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        tvName = findViewById(R.id.tvFullname);
        tvTel = findViewById(R.id.tvTel);
        btnLogout = findViewById(R.id.btnLogout);
        preferences = getSharedPreferences("SHERED_PREF",MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AccountActivity.this);

        String myemail = sharedPreferences.getString("EMAIL",null);
        String myname = sharedPreferences.getString("NAME",null);
        String mytel = sharedPreferences.getString("TEL",null);
        String mypassword = sharedPreferences.getString("PASSWORD",null);
        Log.i("Account ", "onCreate: "+myemail);
        tvPassword.setText(mypassword);
        tvEmail.setText(myemail);
        tvName.setText(myname);
        tvEmail.setText(myemail);
        tvTel.setText(mytel);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                String url = "http://www.mywebapp.lnw.mn/logout.php?logout=1";
                StringRequest request =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("isLogout")){
                            
                            Log.i("Account", "onResponse: Logout Success");
//                            Intent itn = new Intent(AccountActivity.this, LoginActivity.class);
//                            startActivity(itn);

                            AccountActivity.this.startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                            finish();
                            Toast.makeText(AccountActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(AccountActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("logout", "1");
                        return param;

                    }
                };
                RequestQueue queue = Volley.newRequestQueue(AccountActivity.this);
                queue.add(request);

            }
        });

    }

    public void openEditAccount(View v) {
        Intent itn = new Intent(this, EditAccountActivity.class);
        startActivity(itn);
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