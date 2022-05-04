package th.ac.kmutnb.myprojectapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import th.ac.kmutnb.myprojectapp.R;

public class AdminListfoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listfood);
        Intent value = getIntent();
        String delvalue = value.getStringExtra("delId");
        if(delvalue!=null){
            DeleteFood(delvalue);
        }
    }
    public void DeleteFood(String id){
        final ProgressDialog progressDialog = new ProgressDialog(AdminListfoodActivity.this);
        progressDialog.setTitle("Deleting ...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String url ="http://www.mywebapp.lnw.mn/delfood.php";
        StringRequest stringrequest=new StringRequest(Request.Method.POST, url, response -> {
//            progressDialog.dismiss();
            Log.i("response ", "onResponse: " + response);
            if (response.equals("Delete successfully")) {
                Log.i("Reg", "onResponse: "+id);
                Toast.makeText(AdminListfoodActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminListfoodActivity.this, AdminActivity.class));
                progressDialog.dismiss();
                finish();
            } else {
                Toast.makeText(AdminListfoodActivity.this, response, Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(700);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();
            }

        }, error -> {
            Toast.makeText(AdminListfoodActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> param = new HashMap<>();
                Log.i("Map", "getParams: "+id);
                param.put("ID", id);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AdminListfoodActivity.this);
        queue.add(stringrequest);
    }
}