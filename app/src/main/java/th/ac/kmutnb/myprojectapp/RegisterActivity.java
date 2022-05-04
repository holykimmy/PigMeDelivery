package th.ac.kmutnb.myprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class RegisterActivity extends AppCompatActivity {

    EditText Name, emailAddress, password, mobile,password2;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.inputDetail);
        emailAddress = findViewById(R.id.Email);
        password = findViewById(R.id.Password1);
        password2 = findViewById(R.id.Password2);
        mobile = findViewById(R.id.Tel);

        register = findViewById(R.id.btnSubmit);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = Name.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_password2 = password2.getText().toString();
                final String txt_mobile = mobile.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(txt_password) ||
                        TextUtils.isEmpty(txt_mobile)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    register(name, email, txt_password,txt_password2, txt_mobile);
                }
            }
        });
    }

    private void register(final String username, final String email, final String password, final String password2,final String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String url = "http://www.mywebapp.lnw.mn/register_db.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Register Successfully")) {
                    Log.i("Reg", "onResponse: "+username);
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("name", username);
                param.put("email", email);
                param.put("password1", password);
                param.put("password2", password2);
                param.put("tel", mobile);
                Log.i("Map", "getParams: "+email);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(request);

    }
}