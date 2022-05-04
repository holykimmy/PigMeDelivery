package th.ac.kmutnb.myprojectapp.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.ui.order.OrderModel;
import th.ac.kmutnb.myprojectapp.ui.order.OrderStatusChange;
import th.ac.kmutnb.myprojectapp.ui.order.OrderStatusChangeAdmin;

public class AdminOrderListAdapter extends RecyclerView.Adapter<AdminOrderListAdapter.ViewHolder>{
    private static final String TAG = "Order";
    private List<OrderModel> listitems;

    public AdminOrderListAdapter(List<OrderModel> listitems){
        this.listitems = listitems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_listorder, parent, false);
        ViewHolder listitemViewHolder = new ViewHolder(view);
        return listitemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel currentItem = listitems.get(position);
        holder.textViewItem.setText((CharSequence) currentItem.getO_date());
        holder.priceViewItem.setText("Price : " + currentItem.getTotalPrice() + " Bath");
        holder.statusViewItem.setText("Status : "+currentItem.getStatus());

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
                                Intent del = new Intent(view.getContext(), OrderStatusChangeAdmin.class);
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

        holder.btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setTitle("Confirm Success..");
                builder1.setMessage("Are you sure to Success ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "success order "+currentItem.getID_Order());
                                Intent con = new Intent(view.getContext(), OrderStatusChangeAdmin.class);
                                con.putExtra("successId",currentItem.getID_Order());
                                view.getContext().startActivity(con);
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

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view ) {
                Log.d(TAG, "ID order "+currentItem.getID_Order());
                Intent itn = new Intent(view.getContext(), AdminDetail.class);
                itn.putExtra("ID_Order",currentItem.getID_Order());
                view.getContext().startActivity(itn);

            }
        });

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

        TextView textViewItem, priceViewItem, statusViewItem;
        CardView cardViewItem;
        Button btncancel;
        Button btnconfirm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btncancel = itemView.findViewById(R.id.btnCancel_adminorder);
            btnconfirm = itemView.findViewById(R.id.btnConfirm_adminorder);
            textViewItem = itemView.findViewById(R.id.tvadmindateOrder);
            priceViewItem = itemView.findViewById(R.id.tvpriceadminorder);
            statusViewItem = itemView.findViewById(R.id.tvadminstatus);
            cardViewItem = itemView.findViewById(R.id.cardviewlist);


        }
    }
}