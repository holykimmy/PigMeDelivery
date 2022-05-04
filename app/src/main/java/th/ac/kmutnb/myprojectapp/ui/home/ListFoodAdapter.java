package th.ac.kmutnb.myprojectapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import th.ac.kmutnb.myprojectapp.CartActivity;
import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.LoginActivity;
import th.ac.kmutnb.myprojectapp.R;

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ViewHolder>{
    private static final String TAG = "my_app";
    private List<ItemModel> listitems;
    private String baseURL = "http://www.mywebapp.lnw.mn/img/";
    public ListFoodAdapter(List<ItemModel> listitems){
        this.listitems = listitems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_listfoodhomepage, parent, false);
        ViewHolder listitemViewHolder = new ViewHolder(view);
        return listitemViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel currentItem = listitems.get(position);
        holder.textViewItem.setText(currentItem.getItemname());
        holder.priceViewItem.setText("Price " + currentItem.getItemprice() + " Bath");
        String imgUrl = baseURL + currentItem.getItemimage();
        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, currentItem.getItemid());
                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                String myemail = preferences2.getString("EMAIL",null);
                if(myemail==null){
                    Toast.makeText(view.getContext(), "Please login first!!", Toast.LENGTH_SHORT).show();
                    Intent itn = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(itn);
                }
                else{
                    Intent itn2 = new Intent(view.getContext(), CartActivity.class);
                    itn2.putExtra("ID_Product",currentItem.getItemid());
                    view.getContext().startActivity(itn2);
                }
            }
        });
        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, currentItem.getItemid());
            }
        });
        Picasso.get()
                .load(imgUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.pigme_wait).fit()
                .error(R.drawable.pigme_wait)
                .into(holder.imageViewItem);
    }
    @Override
    public int getItemCount() {
        return listitems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewItem;
        TextView textViewItem;
        TextView priceViewItem;
        CardView cardViewItem;
        Button btnadd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnadd = itemView.findViewById(R.id.addcart);
            imageViewItem = itemView.findViewById(R.id.imgEdit);
            textViewItem = itemView.findViewById(R.id.tvadmindateOrder);
            priceViewItem = itemView.findViewById(R.id.tvpriceadminorder);
            cardViewItem = itemView.findViewById(R.id.cardviewlist);

        }
    }
}