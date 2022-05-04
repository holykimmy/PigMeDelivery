package th.ac.kmutnb.myprojectapp.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.R;

public class AdminAllfood extends AppCompatActivity {

    public static final String REQUEST_TAG = "myrequest";
    private static final String TAG = "admin";
    ProgressDialog pDialog;
    private RequestQueue mQueue;

    private RecyclerView recyclerViewListitem;
    private AdminListFoodAdapter AdminlistFoodAdapter;

    private ArrayList<ItemModel> listitem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listfood);

        fetch("http://www.mywebapp.lnw.mn/listfood.php");


    }

    public void fetch(String url){
        pDialog = new ProgressDialog(AdminAllfood.this);
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
                        Toast.makeText(AdminAllfood.this,error.toString(),Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                });  // Request

        mQueue = Volley.newRequestQueue(AdminAllfood.this);
        jsRequest.setTag(REQUEST_TAG);
        mQueue.add(jsRequest);
    }


    public void displayListview(){
        recyclerViewListitem = findViewById(R.id.rv_listitemorder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AdminlistFoodAdapter = new AdminListFoodAdapter(listitem);
        recyclerViewListitem.setLayoutManager(layoutManager);
        recyclerViewListitem.setAdapter(AdminlistFoodAdapter);
//


    }
}