package com.example.lenovo.e_task.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.EnrollClass.EnrollClassModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollActivity extends AppCompatActivity {
    private Button enroll_btn;
    private ImageView backenroll_btn;
    private ProgressDialog loading;
    private SessionManager sessionManager;
    private HashMap<String,String> map;
    public static String ID_CLASS = "id kelas";
    public static String NAME_CLASS = "nama kelas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        sessionManager = new SessionManager(EnrollActivity.this);
        map = new HashMap<>();

        enroll_btn = (Button)findViewById(R.id.enroll_btn);
        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnrollMyClass();
            }
        });

        backenroll_btn = (ImageView) findViewById(R.id.backenroll_btn);
        backenroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnrollActivity.this, MentorActivity.class));
            }
        });
    }

    private void EnrollMyClass() {
        loading = new ProgressDialog(EnrollActivity.this);
        loading.setMessage("Authenticating..");
        loading.show();

        map = sessionManager.getDetail();
        final String id_user = map.get(SessionManager.id_user);
        final String id_kelas = getIntent().getStringExtra("id_kelas");
        final String name_class = getIntent().getStringExtra(NAME_CLASS );

        ApiService.service_post.postEnroll("application/json", id_user,id_kelas).enqueue(new Callback<EnrollClassModel>() {
            @Override
            public void onResponse(Call<EnrollClassModel> call, Response<EnrollClassModel> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(EnrollActivity.this, "sukses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EnrollActivity.this, MentorActivity.class);
                    intent.putExtra("id_kelas", id_kelas);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(EnrollActivity.this, "coba lagi", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<EnrollClassModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EnrollActivity.this, "Periksa koneksi doong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
