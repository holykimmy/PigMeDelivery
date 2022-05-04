package th.ac.kmutnb.myprojectapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.AccountActivity;
import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.LocationActivity;
import th.ac.kmutnb.myprojectapp.LoginActivity;
import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.home.ListAllfood;
import th.ac.kmutnb.myprojectapp.ui.home.ListFoodAdapter;
import th.ac.kmutnb.myprojectapp.ui.order.OrderListAdapter;
import th.ac.kmutnb.myprojectapp.ui.order.OrderModel;

public class AdminOrder extends AppCompatActivity {

    public static final String REQUEST_TAG = "myrequest";
    private static final String TAG = "admin";
    ProgressDialog pDialog;
    private RecyclerView recyclerViewListitem;
    private AdminOrderListAdapter adminOrderListAdapter;

    private RequestQueue mQueue;


    private ArrayList<OrderModel> listitem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        fetch("http://www.mywebapp.lnw.mn/listorderadmin.php");
    }

    private void fetch(String url) {

        pDialog = new ProgressDialog(AdminOrder.this);
        pDialog.setMessage("Loading..");
        pDialog.show();

        listitem.clear();

        JsonArrayRequest jsRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();

                        JSONObject jsObj;   // = null;
                        for (int i=0; i < response.length(); i++ ) {
                            try {
                                jsObj = response.getJSONObject(i);
                                OrderModel dataitem = gson.fromJson(String.valueOf(jsObj), OrderModel.class);
                                listitem.add(dataitem);
                                Log.d(TAG,"gson "+ dataitem.getID_User());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (listitem.size() > 0){
                            displayListview();
                        }

                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.toString());
                        Toast.makeText(AdminOrder.this,error.toString(),Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }

                }){

        };  // Request

        mQueue = Volley.newRequestQueue(AdminOrder.this);
        jsRequest.setTag(REQUEST_TAG);
        mQueue.add(jsRequest);
    }

    public void displayListview(){
        recyclerViewListitem = findViewById(R.id.rv_listitemorder);
        adminOrderListAdapter = new AdminOrderListAdapter(listitem);
        recyclerViewListitem.setLayoutManager(new GridLayoutManager(AdminOrder.this, 1, GridLayoutManager.VERTICAL, false));
        recyclerViewListitem.setAdapter(adminOrderListAdapter);
//

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


//

}