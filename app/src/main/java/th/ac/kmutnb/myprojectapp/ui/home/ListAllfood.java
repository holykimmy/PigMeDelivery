package th.ac.kmutnb.myprojectapp.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import th.ac.kmutnb.myprojectapp.AccountActivity;
import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.LocationActivity;
import th.ac.kmutnb.myprojectapp.LoginActivity;
import th.ac.kmutnb.myprojectapp.R;

public class ListAllfood extends AppCompatActivity {

    public static final String REQUEST_TAG = "myrequest";
    private static final String TAG = "my_app";
    ProgressDialog pDialog;
    private RequestQueue mQueue;

    private RecyclerView recyclerViewListitem;
    private ListFoodAdapter listFoodAdapter;

    private ArrayList<ItemModel> listitem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        fetch("http://www.mywebapp.lnw.mn/listfood.php");

    }
    public void fetch(String url){
        pDialog = new ProgressDialog(ListAllfood.this);
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
                                ItemModel dataitem = gson.fromJson(String.valueOf(jsObj), ItemModel.class);
                                listitem.add(dataitem);
                                Log.d(TAG,"gson "+ dataitem.getItemname());
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
                        Toast.makeText(ListAllfood.this,error.toString(),Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                });

        mQueue = Volley.newRequestQueue(ListAllfood.this);
        jsRequest.setTag(REQUEST_TAG);
        mQueue.add(jsRequest);
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

    public void displayListview(){
        recyclerViewListitem = findViewById(R.id.rv_listitemorder);
        listFoodAdapter = new ListFoodAdapter(listitem);
        recyclerViewListitem.setLayoutManager(new GridLayoutManager(ListAllfood.this, 2, GridLayoutManager.VERTICAL, false));
        recyclerViewListitem.setAdapter(listFoodAdapter);
    }
}