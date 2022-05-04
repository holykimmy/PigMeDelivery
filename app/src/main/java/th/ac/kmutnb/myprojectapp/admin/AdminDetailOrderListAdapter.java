package th.ac.kmutnb.myprojectapp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.order.OrderDetailModel;

public class AdminDetailOrderListAdapter extends RecyclerView.Adapter<AdminDetailOrderListAdapter.ViewHolder>{
    private static final String TAG = "Order detail";
    private List<OrderDetailModel> listitems;
    private String baseURL = "http://www.mywebapp.lnw.mn/img/";
    public AdminDetailOrderListAdapter(List<OrderDetailModel> listitems){
        this.listitems = listitems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_order, parent, false);
        ViewHolder listitemViewHolder = new ViewHolder(view);
        return listitemViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailModel currentItem = listitems.get(position);
        holder.textViewItem.setText((CharSequence) currentItem.getFname());
        holder.priceViewItem.setText("Price : " + currentItem.getPrice() + " Bath");
        holder.amountViewItem.setText("Amount : "+currentItem.getAmount());
        String imgUrl = baseURL + currentItem.getPicFood();
        Picasso.get()
                .load(imgUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.pigme_wait).fit()
                .error(R.drawable.pigme_wait)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItem, priceViewItem,amountViewItem;
        CardView cardViewItem;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.tvFname);
            priceViewItem = itemView.findViewById(R.id.tvpriceadminorder);
            img = itemView.findViewById(R.id.imgDetailFood);
            amountViewItem = itemView.findViewById(R.id.tvAmount);
            cardViewItem = itemView.findViewById(R.id.cardviewlist);
        }
    }
}