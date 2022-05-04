package th.ac.kmutnb.myprojectapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;


import java.util.UUID;

import th.ac.kmutnb.myprojectapp.R;

public class AdminAddfood extends AppCompatActivity {
    private static final String[] items = {"Chicken", "Fast Food", "Dessert","Beverage"};
    static final String TAG = "select";
    int catf ;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private  static  final String UPLOAD_URL = "http://www.mywebapp.lnw.mn/addfood_db.php";
    private Uri filepath;
    private Bitmap bitmap;
    TextView tv;
    ImageView imgview;
    EditText edname,edprice;
    Spinner mydropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addfood);
        requestStoragePermission();
        imgview = findViewById(R.id.previewimg);
        mydropdown = findViewById(R.id.catfdrop);
        tv = findViewById(R.id.textViewPreview);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AdminAddfood.this,
                android.R.layout.simple_spinner_item,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mydropdown.setAdapter(adapter);
        mydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Log.i(TAG, "onItemSelected: chicken");
                        catf=3;
                        break;
                    case 1:
                        Log.i(TAG, "onItemSelected: fast food");
                        catf=2;
                        break;
                    case 2:
                        Log.i(TAG, "onItemSelected: Dessert");
                        catf=1;
                        break;
                    case 3:
                        Log.i(TAG, "onItemSelected: Beverage");
                        catf=4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String pic="";
//        Button btnadd = findViewById(R.id.btn_addfood);
//        btnadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = edname.getText().toString();
//                String price = edprice.getText().toString();
//                Double newprice = Double.parseDouble(price);
//                addFood(name,newprice,pic,catf);
//            }
//        });
    }
    public void addFood(String name,Double price,String pic,int ID_CATF){
        Log.i(TAG, "addFood: "+name+price+ID_CATF);
    }
    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imgview.setImageBitmap(bitmap);
                tv.setText(filepath.toString());
                // Toast.makeText(getApplicationContext(),getPath(filepath),Toast.LENGTH_LONG).show();
            } catch (Exception ex) {

            }
        }
    }
    public void selectImage(View view)
    {
        ShowFileChooser();
    }

    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void uploadImage() {
        edname = findViewById(R.id.addfood_name);
        edprice = findViewById(R.id.addfood_price);
        String name = edname.getText().toString();
        String price = edprice.getText().toString();
        String idcatf = String.valueOf(catf);
        Log.i(TAG, "uploadImage nopath: "+name + price+idcatf);
        String path = getPath(filepath);
        Log.i(TAG, "uploadImage outside: "+name + price+idcatf+" "+path);
        try {
            String uploadId = UUID.randomUUID().toString();

            new MultipartUploadRequest(this, uploadId, UPLOAD_URL).addFileToUpload(path, "pic").addParameter("name", name).addParameter("price", price).addParameter("catf",idcatf)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3).setMethod("POST")
                    .startUpload();
            Log.i(TAG, "uploadImage: "+name + price+idcatf+" "+path);
        } catch (Exception ex) {


        }

    }


    public void saveData(View view)
    {
        uploadImage();
    }
}