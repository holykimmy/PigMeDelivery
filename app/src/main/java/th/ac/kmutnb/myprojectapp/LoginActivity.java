package th.ac.kmutnb.myprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.admin.AdminActivity;


public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button register,btnlogin;
    SharedPreferences sharedPreferences;

    public void openAppInfo(View v) {
        Intent itn = new Intent(this, MainActivity.class);
        startActivity(itn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passInput);
        btnlogin = (Button)findViewById(R.id.Loginbtn);
        sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myemail = email.getText().toString();
                String mypassword = password.getText().toString();

                if (TextUtils.isEmpty(myemail) || TextUtils.isEmpty(mypassword)){
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(myemail,mypassword);
                }
            }
        });
        register = findViewById(R.id.Registerbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }

    public void login(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);

        progressDialog.show();
        String url ="http://www.mywebapp.lnw.mn/login_db.php";
        StringRequest stringrequest=new StringRequest(Request.Method.POST, url, response -> {
           Log.i("response ", "onResponse: " + response);
               try {
                    JSONObject obj = new JSONObject(response);
                    int role = obj.getInt("role");
                    if(role==1){
                        String myemail = obj.getString("email");
                        String myname = obj.getString("name");
                        String mytel = obj.getString("tel");
                        sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("EMAIL",myemail);
                        editor.putString("NAME",myname);
                        editor.putString("TEL",mytel);
                        editor.putString("PASSWORD",password);

                        editor.apply();
                        Toast.makeText(LoginActivity.this,"Information Saved",Toast.LENGTH_SHORT).show();

                        Log.i("Loginsuccess member", "onResponse: " + myemail + myname + mytel);
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }else if(role==0){
                        String myemail = obj.getString("email");
                        String myname = obj.getString("name");
                        Log.i("Loginsuccess admin ", "onResponse: " + myemail + myname );
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
               } catch (JSONException e) {
                   e.printStackTrace();
                   Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                   new Thread(new Runnable() {
                       public void run() {
                           try {
                               Thread.sleep(500);
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                           progressDialog.dismiss();
                       }
                   }).start();
               }
       }, error -> {
           Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
           progressDialog.dismiss();
       }) {
            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> param = new HashMap<>();
                Log.i("Map", "getParams: "+email+" "+password);
                param.put("email", email);
                param.put("password", password);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringrequest);
    }
}