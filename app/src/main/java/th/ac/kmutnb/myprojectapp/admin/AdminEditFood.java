package th.ac.kmutnb.myprojectapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.order.OrderStatusChange;


public class AdminEditFood extends AppCompatActivity {
    EditText edname,edprice;
    ImageView img;
    Button btnSubmit;
    private String baseURL = "http://www.mywebapp.lnw.mn/img/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_food);
        Intent value = getIntent();
        String upname = value.getStringExtra("name");
        Double upprice = value.getDoubleExtra("price",-1);
        String upId = value.getStringExtra("upId");
        String upimg = value.getStringExtra("img");
        Log.i("Edit ", "onCreate: "+upId+upname+upprice+" "+upimg);
        edname = findViewById(R.id.tvadmindateOrder);
        edprice = findViewById(R.id.tvpriceadminorder);
        edprice.setText(String.valueOf(upprice));
        edname.setText(upname);
        img = findViewById(R.id.imgEdit);
        String imgUrl = baseURL + upimg;
        Log.i("test img", "onCreate: "+ imgUrl);
        Picasso.get().load(imgUrl).error(R.drawable.pigme_wait).into(img);
        btnSubmit = findViewById(R.id.addcart);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setTitle("Confirm Update..");
                builder1.setMessage("Are you sure to Update ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String myname = edname.getText().toString();
                                final String myprice = edprice.getText().toString();
                                if (TextUtils.isEmpty(myname) || TextUtils.isEmpty(myprice)) {
                                    Toast.makeText(AdminEditFood.this, "All fields are required", Toast.LENGTH_SHORT).show();
                                } else {
                                    Update(upId,myname, myprice);
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }
    private void Update(String id,String name,String price){
        final ProgressDialog progressDialog = new ProgressDialog(AdminEditFood.this);
        progressDialog.setTitle("Updating your food");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String url = "http://www.mywebapp.lnw.mn/listfood_update.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Update Successfully")) {
                    Log.i("Update", "onResponse: "+id+" "+name+" "+ price);
                    Toast.makeText(AdminEditFood.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminEditFood.this, AdminAllfood.class));
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(AdminEditFood.this, response, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AdminEditFood.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("ID", id);
                param.put("Food_Name", name);
                param.put("price", price);
                Log.i("Map", "getParams: "+name + " "+price);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(AdminEditFood.this);
        queue.add(request);

    }
}