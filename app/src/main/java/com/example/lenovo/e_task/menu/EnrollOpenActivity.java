package com.example.lenovo.e_task.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.adapter.DetailMyClassAdapter;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.DetailMyClass.DetailMyClassModel;
import com.example.lenovo.e_task.model.DetailMyClass.Mentor;
import com.example.lenovo.e_task.model.DetailMyClass.Task;
import com.example.lenovo.e_task.model.MyClass.Kelas;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollOpenActivity extends AppCompatActivity {
    private ImageView backopen_btn;
    private SessionManager sessionManager;
    private HashMap<String,String> map;
    private ArrayList<Task> taskList;
    private ArrayList<Mentor> mentorList;
    private ArrayList<Kelas> kelas;
    private RecyclerView recyclerView;
    private DetailMyClassAdapter eAdapter;
    ProgressDialog loading;
    public static String ID_CLASS = "id kelas";
    public static String NAME_CLASS = "nama kelas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_open);
        loading = new ProgressDialog(this);
        sessionManager = new SessionManager(EnrollOpenActivity.this);
        map = new HashMap<>();
        showDetailMyClass();


        backopen_btn = (ImageView) findViewById(R.id.backopen_btn);
        backopen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnrollOpenActivity.this, MentorActivity.class));
            }
        });
    }

    private void showDetailMyClass() {
        loading = new ProgressDialog(EnrollOpenActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Loading..");
        loading.show();

        map = sessionManager.getDetail();
        String id_user = map.get(SessionManager.id_user);
//        final int kelas_id = getIntent().getIntExtra(ID_CLASS, 0);
        final String kelas_id = getIntent().getStringExtra("id_kelas");
        ApiService.service_post.postDetailmyClass("application/json",id_user, kelas_id).enqueue(new Callback<DetailMyClassModel>() {
            @Override
            public void onResponse(Call<DetailMyClassModel> call, Response<DetailMyClassModel> response) {
                if (response.code() == 200)
                {
                    loading.dismiss();
                    taskList = response.body().getTask();
                    mentorList=response.body().getMentor();
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail_kelas_list);
                    eAdapter = new DetailMyClassAdapter(taskList,mentorList, EnrollOpenActivity.this);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);
                }

                else {
                    Toast.makeText(EnrollOpenActivity.this, "gagal andaaa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailMyClassModel> call, Throwable t) {

            }
        });

    }

}
