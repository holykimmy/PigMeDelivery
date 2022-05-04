package th.ac.kmutnb.myprojectapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.databinding.FragmentHomeBinding;
import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.categoryfood.TabmenuActivity;

public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    private FragmentHomeBinding binding;
    public Button btn;
    public CardView cvwChicken, cvwFastfood, cvwDessert, cvwBeverage;

    public static final String REQUEST_TAG = "myrequest";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn = binding.Allfoodbtn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListAllfood.class));
            }
        });
//---------------------------Slider---------------------------//
        imageSlider = root.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.brannerdessert, null));
        imageList.add(new SlideModel(R.drawable.branddrink, null));
        imageList.add(new SlideModel(R.drawable.brannerfastfood, null));
        imageList.add(new SlideModel(R.drawable.brannerchicken, null));
        imageSlider.setImageList(imageList);

//        ----------------------Card View to Tabmenu ---------------------//
        cvwChicken = binding.cvChicken;
        cvwFastfood = binding.cvFastfood;
        cvwDessert = binding.cvDesssert;
        cvwBeverage = binding.cvBeverage;
        cvwChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TabmenuActivity.class));
            }
        });
        cvwFastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TabmenuActivity.class));
            }
        });
        cvwDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TabmenuActivity.class));
            }
        });
        cvwBeverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TabmenuActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}