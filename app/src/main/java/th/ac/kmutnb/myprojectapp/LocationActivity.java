package th.ac.kmutnb.myprojectapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.AccountActivity;
import th.ac.kmutnb.myprojectapp.MainActivity;
import th.ac.kmutnb.myprojectapp.R;

public class LocationActivity extends AppCompatActivity {
    EditText edHouse,edDetail ,edProvince,edZipcode ;
    Button btnSubmit ;
    SharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //sy;0tx;fmew';t;t//
        edHouse = findViewById(R.id.inputHouseNo);
        edDetail = findViewById(R.id.inputDetail);
        edProvince = findViewById(R.id.inputProvince);
        edZipcode = findViewById(R.id.inputZipcode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String adhouse = edHouse.getText().toString();
                final String addetail = edDetail.getText().toString();
                final String adprovince = edProvince.getText().toString();
                final String adzipcode = edZipcode.getText().toString();
                if (TextUtils.isEmpty(adhouse) || TextUtils.isEmpty(addetail)|| TextUtils.isEmpty(adprovince)|| TextUtils.isEmpty(adzipcode)) {
                    Toast.makeText(LocationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    Update( adhouse, addetail, adprovince , adzipcode);
                }
            }
        });
    }

    private void Update(String house ,String detail ,String province , String zipcode) {
        final ProgressDialog progressDialog = new ProgressDialog(LocationActivity.this);
        progressDialog.setTitle("Updating your Location");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        Intent itn = getIntent();
        String myemail = itn.getStringExtra("email");
        String url = "http://www.mywebapp.lnw.mn/addaddress.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Added Successfully")) {
                    Toast.makeText(LocationActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LocationActivity.this, AccountActivity.class));
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(LocationActivity.this, response, Toast.LENGTH_SHORT).show();
                    Log.i("ADD error", "onResponse: Error");
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(700);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
//                param.put("ID", id);
                param.put("email",myemail);
                param.put("detail", house + detail );
                param.put("province",province);
                param.put("zipcode",zipcode);
                Log.i("Map", "getParams: " + house + detail + " " + province + " " + zipcode);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(LocationActivity.this);
        queue.add(request);

    }

    public void openAppInfo(View v) {
        Intent itn = new Intent(this, MainActivity.class);
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