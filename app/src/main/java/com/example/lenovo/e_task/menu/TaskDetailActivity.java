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
import com.example.lenovo.e_task.model.DetailMyClass.DetailMyClassModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lenovo.e_task.menu.EnrollActivity.ID_CLASS;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView tvNameMentor, tvNameClass, tvDeskripsi, tvBatas, tvNameUpload;
    private ImageView backdetail_btn;
    Button btnUpload;
    private SessionManager sessionManager;
    HashMap<String,String> map;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        tvNameMentor=(TextView)findViewById(R.id.tv_nameMentor);
        tvNameClass=(TextView)findViewById(R.id.tv_nameClass);
        tvDeskripsi=(TextView)findViewById(R.id.tv_deskripsi);
        tvBatas=(TextView)findViewById(R.id.tv_batas);
        tvNameUpload=(TextView)findViewById(R.id.tv_NameUpload);

        loading = new ProgressDialog(this);
        sessionManager = new SessionManager(TaskDetailActivity.this);
        map = new HashMap<>();

        Intent intent = getIntent();
        final String Task_id = intent.getStringExtra("id");
        final String Nama_Mentor = intent.getStringExtra("mentor");
        tvNameMentor.setText(Nama_Mentor);
        final String Task_Name = intent.getStringExtra("name");
        tvNameClass.setText(Task_Name);
        final String deskripsi = intent.getStringExtra("deskripsi");
        tvDeskripsi.setText(deskripsi);
        final String Deadline = intent.getStringExtra("deadline");
        tvBatas.setText(Deadline);


        showDetailTask();

        backdetail_btn = (ImageView) findViewById(R.id.backdetail_btn);
        backdetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskDetailActivity.this, EnrollOpenActivity.class));
            }
        });

        btnUpload = (Button) findViewById(R.id.dirupload_btn);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDetailActivity.this, UploadTaskActivity.class);
                intent.putExtra("name_task", Task_Name);
                intent.putExtra("id_task",Task_id );
                startActivity(intent);
            }
        });
    }

    private void showDetailTask() {
        loading = new ProgressDialog(TaskDetailActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Loading..");
        loading.show();

        map = sessionManager.getDetail();
        String id_user = map.get(SessionManager.id_user);
        final String kelas_id = getIntent().getStringExtra("id_kelas");

        ApiService.service_post.postDetailmyClass("application/json",id_user,kelas_id).enqueue(new Callback<DetailMyClassModel>() {
            @Override
            public void onResponse(Call<DetailMyClassModel> call, Response<DetailMyClassModel> response) {
                if (response.code() == 200)
                {
                    loading.dismiss();
//                    ArrayList<Mentor> mentor = response.body().getMentor();
//                    String nama_mentor = mentor.get(0).getName();
//                    tvNamaMentor.setText(nama_mentor);
//                    Toast.makeText(TaskDetailActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loading.dismiss();
                    Toast.makeText(TaskDetailActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailMyClassModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(TaskDetailActivity.this, "gak ada jaringan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
