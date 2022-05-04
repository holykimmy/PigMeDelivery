package th.ac.kmutnb.myprojectapp.ui.categoryfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
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

public class FastfoodFragment extends Fragment {


    public static final String REQUEST_TAG = "myrequest";
    private RecyclerView recyclerViewListitem;
    private ListFoodCategoryAdapter listFoodCategoryAdapter;
    private final String url = "http://www.mywebapp.lnw.mn/listfood.php?ID=2";
    private ArrayList<ItemModel> listitem = new ArrayList<>();

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fastfood, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (listitem.size() > 0){
                            displayListview();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(getActivity()).add(jsRequest.setTag(REQUEST_TAG));
    }
    public void displayListview(){
        recyclerViewListitem = getView().findViewById(R.id.rv_listitemorder);
        listFoodCategoryAdapter = new ListFoodCategoryAdapter(listitem);
        recyclerViewListitem.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        recyclerViewListitem.setAdapter(listFoodCategoryAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        displayListview();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}