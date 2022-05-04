package th.ac.kmutnb.myprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import th.ac.kmutnb.myprojectapp.admin.AdminAllfood;
import th.ac.kmutnb.myprojectapp.admin.AdminEditFood;

public class EditAccountActivity extends AppCompatActivity {


    EditText edName,edPhone ;
    Button btnSubmit;
    TextView tvEmail,tvPassword;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Intent value = getIntent();
        String upname = value.getStringExtra("name");
        String upphone = value.getStringExtra("phone");
        Log.i("Edit ", "onCreate: "+upname+upphone);

        edName = findViewById(R.id.edFullname);
        edPhone = findViewById(R.id.edPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnSubmit = findViewById(R.id.editname_submit);

        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(EditAccountActivity.this);
        String myemail = preferences2.getString("EMAIL",null);
        String myname = preferences2.getString("NAME",null);
        String mytel = preferences2.getString("TEL",null);
        String mypassword = preferences2.getString("PASSWORD",null);
        Log.i("Account ", "onCreate: "+myemail);
        tvPassword.setText(mypassword);
        tvEmail.setText(myemail);
        edName.setText(myname);
        tvEmail.setText(myemail);
        edPhone.setText(mytel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myname2 = edName.getText().toString();
                final String myPhone2 = edPhone.getText().toString();
                final String myemail2 = tvEmail.getText().toString();
                if (TextUtils.isEmpty(myname2) || TextUtils.isEmpty(myPhone2)) {
                    Toast.makeText(EditAccountActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Submit", "onClick: "+myemail2+" "+myname2+" "+myPhone2);
                    Update(myname2, myPhone2,myemail2);
                }
            }
        });


    }
    private void Update(String name,String phone,String email) {
        final ProgressDialog progressDialog = new ProgressDialog(EditAccountActivity.this);
        progressDialog.setTitle("Updating your account");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String url = "http://www.mywebapp.lnw.mn/account_update.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Update Successfully")) {
                    Log.i("Update", "onResponse: " + " " + name + " " + phone);
                    Toast.makeText(EditAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditAccountActivity.this, AccountActivity.class));
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(EditAccountActivity.this, response, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("Email_User",email);
                param.put("Name_User", name);
                param.put("Tel_User",phone);
                Log.i("Map", "getParams: " + name + " " + phone);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditAccountActivity.this);
        queue.add(request);

    }

    public void openAppInfo(View v) {
        final String myemail2 = tvEmail.getText().toString();
        Intent itn = new Intent(this, LocationActivity.class);
        itn.putExtra("email",myemail2);
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