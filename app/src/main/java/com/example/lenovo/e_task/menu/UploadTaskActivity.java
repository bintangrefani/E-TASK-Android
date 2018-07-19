package com.example.lenovo.e_task.menu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.UploadFile.UploadFileModel;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadTaskActivity extends AppCompatActivity {
    LinearLayout SelectPdf;
    ImageView backupload_btn, imagePreview;
    Button uploadcan_btn, upload_btn;
    ProgressDialog loading;
    private SessionManager sessionManager;
    HashMap<String,String> map;
    TextView Coba, Judul;

    public int PDF_REQ_CODE = 1;
    Uri uri;
    String PdfPathHolder;
    RequestBody title, task_id, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_task);
        loading = new ProgressDialog(this);
        sessionManager = new SessionManager(UploadTaskActivity.this);
        map = new HashMap<>();

        imagePreview = (ImageView)findViewById(R.id.gambarlallalla);
        Coba = (TextView)findViewById(R.id.coba);
        Judul=(TextView)findViewById(R.id.judul);

        Intent intent = getIntent();
        final String Task_id = intent.getStringExtra("id_task");
        final String Task_Name = intent.getStringExtra("name_task");
        Coba.setText(Task_Name);


        SelectPdf = (LinearLayout) findViewById(R.id.open_btn);
        SelectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (!checkIfAlreadyhavePermission()) {
                        ActivityCompat.requestPermissions(UploadTaskActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        selectPdf();
                    }
                }

            }
        });

        backupload_btn = (ImageView) findViewById(R.id.backupload_btn);
        backupload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadTaskActivity.this, TaskDetailActivity.class));
            }
        });

        uploadcan_btn = (Button) findViewById(R.id.uploadcan_btn);
        uploadcan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadTaskActivity.this, TaskDetailActivity.class));

            }
        });

        upload_btn=(Button)findViewById(R.id.btn_uploadFile);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPdf();
            }
        });
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(UploadTaskActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void selectPdf() {
//        //intent ambil pdf
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    private void UploadPdf() {
        loading = new ProgressDialog(UploadTaskActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Authenticating..");
        loading.show();

        map = sessionManager.getDetail();
        final String id_user = map.get(SessionManager.id_user);
        Intent intent = getIntent();
        final String Task_id = intent.getStringExtra("id_task");
        final String Task_Name = intent.getStringExtra("name_task");
//        // Map is used to multipart the file using okhttp3.RequestBody
        title = RequestBody.create(okhttp3.MultipartBody.FORM, Task_Name);
        task_id = RequestBody.create(okhttp3.MultipartBody.FORM, Task_id);
        user_id = RequestBody.create(okhttp3.MultipartBody.FORM, id_user);

        File file = new File(PdfPathHolder);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        ApiService.service_post.postUploadFile("application/json", title, task_id, user_id, fileToUpload).enqueue(new Callback<UploadFileModel>() {
            @Override
            public void onResponse(Call<UploadFileModel> call, Response<UploadFileModel> response) {
                if (response.code()==200)
                {
                    loading.dismiss();
                    Toast.makeText(UploadTaskActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadTaskActivity.this, SidebarActivity.class);
                    startActivity(intent);
                }
                else
                {
                    loading.dismiss();
                    Toast.makeText(UploadTaskActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadFileModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(UploadTaskActivity.this,"gak ada jaringan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION};
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex("_data");
                PdfPathHolder = cursor.getString(columnIndex);
                Judul.setText(PdfPathHolder);
//                // Set the Image in ImageView for Previewing the Media
                imagePreview.setImageBitmap(BitmapFactory.decodeFile(PdfPathHolder));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Fail", "onFailure: " + e.getMessage(), e);
            Toast.makeText(this, "Something went wrong : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            uri = data.getData();
//
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    Toast.makeText(UploadTaskActivity.this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
