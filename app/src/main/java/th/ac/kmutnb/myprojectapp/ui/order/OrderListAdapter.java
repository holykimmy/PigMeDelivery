package th.ac.kmutnb.myprojectapp.ui.order;

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

import java.util.List;

import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.admin.AdminDetail;
import th.ac.kmutnb.myprojectapp.admin.AdminListfoodActivity;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    private static final String TAG = "Order";
    private List<OrderModel> listitems;

    public OrderListAdapter(List<OrderModel> listitems){
        this.listitems = listitems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_listorder, parent, false);
        ViewHolder listitemViewHolder = new ViewHolder(view);
        return listitemViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel currentItem = listitems.get(position);
        holder.textViewItem.setText((CharSequence) currentItem.getO_date());
        holder.priceViewItem.setText("Price : " + currentItem.getTotalPrice() + " Bath");
        holder.statusViewItem.setText("Status : "+currentItem.getStatus());

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Detail "+currentItem.getID_Order());
                Intent detail = new Intent(view.getContext(), OrderDetail.class);
                detail.putExtra("ID_Order",currentItem.getID_Order());
                view.getContext().startActivity(detail);
            }
        });
        holder.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setTitle("Confirm Cancel..");
                builder1.setMessage("Are you sure to Cancel ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "Cancel "+currentItem.getID_Order());
                                Intent del = new Intent(view.getContext(), OrderStatusChange.class);
                                del.putExtra("cancelId",currentItem.getID_Order());
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
    }
    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItem, priceViewItem, statusViewItem;
        CardView cardViewItem;
        Button btncancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btncancel = itemView.findViewById(R.id.btnCancel_adminorder);
            imageViewItem = itemView.findViewById(R.id.imgEdit);
            textViewItem = itemView.findViewById(R.id.tvadmindateOrder);
            priceViewItem = itemView.findViewById(R.id.tvpriceadminorder);
            statusViewItem = itemView.findViewById(R.id.tvstatusorder);
            cardViewItem = itemView.findViewById(R.id.cardviewlist);


        }
    }
}