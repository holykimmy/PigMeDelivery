package th.ac.kmutnb.myprojectapp.ui.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.MainActivity;
import th.ac.kmutnb.myprojectapp.admin.AdminAllfood;
import th.ac.kmutnb.myprojectapp.admin.AdminEditFood;
import th.ac.kmutnb.myprojectapp.admin.AdminOrder;

public class OrderStatusChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent itn = getIntent();
        String IDcancel = itn.getStringExtra("cancelId");
        String IDsuccess = itn.getStringExtra("successId");

        if(IDcancel!=null){
            CancelStatus(IDcancel);
        }
        if(IDsuccess!=null){
            SuccessStatus(IDsuccess);
        }
    }
    public void CancelStatus(String ID_Order){
        final String url = "http://www.mywebapp.lnw.mn/changecuscancel.php?ID_Order="+ ID_Order;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Cancel Successfully")) {
                    Log.i("Cancel", "onResponse: "+ID_Order);
                    Toast.makeText(OrderStatusChange.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OrderStatusChange.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(OrderStatusChange.this, response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderStatusChange.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("ID_Order", ID_Order);
                Log.i("Map", "getParams: "+ID_Order);
                return param;

            }
         };
        RequestQueue queue = Volley.newRequestQueue(OrderStatusChange.this);
        queue.add(request);
    }
    public void SuccessStatus(String ID_Order){
        final String url = "http://www.mywebapp.lnw.mn/changecussuccess.php?ID_Order="+ ID_Order;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Order Successfully")) {
                    Log.i("Success", "onResponse: "+ID_Order);
                    Toast.makeText(OrderStatusChange.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OrderStatusChange.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(OrderStatusChange.this, response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderStatusChange.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("ID_Order", ID_Order);
                Log.i("Map", "getParams: "+ID_Order);
                return param;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(OrderStatusChange.this);
        queue.add(request);
    }
}