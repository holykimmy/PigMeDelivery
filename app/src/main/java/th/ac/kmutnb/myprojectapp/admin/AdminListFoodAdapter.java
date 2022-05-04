package th.ac.kmutnb.myprojectapp.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import th.ac.kmutnb.myprojectapp.ItemModel;
import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.order.OrderStatusChange;

public class AdminListFoodAdapter extends RecyclerView.Adapter<AdminListFoodAdapter.ViewHolder>{
    private static final String TAG = "Admin";
    private List<ItemModel> listitems;
    private String baseURL = "http://www.mywebapp.lnw.mn/img/";

    public AdminListFoodAdapter(List<ItemModel> listitems){
        this.listitems = listitems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_listfood, parent, false);
        ViewHolder listitemViewHolder = new ViewHolder(view);
        return listitemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel currentItem = listitems.get(position);
        holder.textViewItem.setText(currentItem.getItemname());
        holder.priceViewItem.setText("Price: " + currentItem.getItemprice() + " Bath");
        String imgUrl = baseURL + currentItem.getItemimage();

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setTitle("Confirm Delete..");
                builder1.setMessage("Are you sure to Delete ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "Delete "+currentItem.getItemid());
                                Intent del = new Intent(view.getContext(),AdminListfoodActivity.class);
                                del.putExtra("delId",currentItem.getItemid());
                                view.getContext().startActivity(del);
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
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Update "+currentItem.getItemid());
                Intent up = new Intent(view.getContext(),AdminEditFood.class);
                up.putExtra("name",currentItem.getItemname());
                up.putExtra("upId",currentItem.getItemid());
                up.putExtra("price",currentItem.getItemprice());
                up.putExtra("img",currentItem.getItemimage());
                view.getContext().startActivity(up);
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

//    private int doToPx(int dp){
//        float px = dp *
//    }
    public void DeleteFood(){

    }
    public void UpdateFood(){

    }
    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItem;
        TextView priceViewItem;
        CardView cardViewItem;
        Button btndelete,btnupdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btndelete = itemView.findViewById(R.id.btnConfirm_adminorder);
            btnupdate = itemView.findViewById(R.id.btnCancel_adminorder);
            imageViewItem = itemView.findViewById(R.id.imgEdit);
            textViewItem = itemView.findViewById(R.id.tvadmindateOrder);
            priceViewItem = itemView.findViewById(R.id.tvpriceadminorder);
            cardViewItem = itemView.findViewById(R.id.cardviewlist);


        }
    }
}