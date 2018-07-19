package com.example.lenovo.e_task.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.adapter.MyClassAdapter;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.MyClass.Kelas;
import com.example.lenovo.e_task.model.MyClass.MyClassModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MentorActivity extends AppCompatActivity {
    ImageView backmentor_btn;
    ProgressDialog loading;
    private SessionManager sessionManager;
    HashMap<String,String> map;

    private ArrayList<Kelas> kelasList;
    private RecyclerView recyclerView;
    private MyClassAdapter eAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        sessionManager = new SessionManager(MentorActivity.this);
        map = new HashMap<>();
        showMyClass();

        backmentor_btn = (ImageView) findViewById(R.id.backmentor_btn);
        backmentor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MentorActivity.this, SidebarActivity.class));
            }
        });
    }

    private void showMyClass() {
        loading = new ProgressDialog(MentorActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Loading..");
        loading.show();
        map = sessionManager.getDetail();
        String id_user = map.get(SessionManager.id_user);
        ApiService.service_post.postMyClass("application/json", id_user).enqueue(new Callback<MyClassModel>() {
            @Override
            public void onResponse(Call<MyClassModel> call, Response<MyClassModel> response) {
                if (response.code() == 200)
                {
                    loading.dismiss();
                    kelasList = response.body().getKelas();
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view_kelas_list);
                    eAdapter = new MyClassAdapter(kelasList,MentorActivity.this);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);
                }
                else
                {
                    Toast.makeText(MentorActivity.this, "gak bisaa masukk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyClassModel> call, Throwable t) {

            }
        });
    }
}
