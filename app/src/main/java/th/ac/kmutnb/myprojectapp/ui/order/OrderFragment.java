package th.ac.kmutnb.myprojectapp.ui.order;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.R;
//import th.ac.kmutnb.myprojectapp.databinding.FragmentOrderBinding;

import th.ac.kmutnb.myprojectapp.ui.categoryfood.ListFoodCategoryAdapter;

public class OrderFragment extends Fragment {


    public static final String REQUEST_TAG = "myrequest";
    private RecyclerView recyclerViewListitem;
    private OrderListAdapter orderAdapter;

    private ArrayList<OrderModel> listitem = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_order, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String myemail = preferences2.getString("EMAIL", null);
        String url = "http://www.mywebapp.lnw.mn/listorder.php?email="+myemail;
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

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> param = new HashMap<>();
                param.put("email", myemail);
                Log.i("Map", "getParams: "+myemail);
                return param;

            }
        };

        Volley.newRequestQueue(getActivity()).add(jsRequest.setTag(REQUEST_TAG));
    }
    public void displayListview(){
        recyclerViewListitem = getView().findViewById(R.id.rv_listitemorder);
        orderAdapter = new OrderListAdapter(listitem);
        recyclerViewListitem.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        recyclerViewListitem.setAdapter(orderAdapter);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}