package com.example.lenovo.e_task.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.UserConstant.UserConstant;
import com.example.lenovo.e_task.adapter.MyClassAdapter;
import com.example.lenovo.e_task.adapter.SemuaKelasAdapter;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.DaftarSemuaKelas.DaftarSemuaKelas;
import com.example.lenovo.e_task.model.MyClass.Kelas;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SidebarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private TextView tvAllClass;

        SessionManager sessionManager;
        HashMap<String,String> map;
        LinearLayout llTaskName;

        private ArrayList<DaftarSemuaKelas> SemuaKelasList;
        private RecyclerView recyclerView;
        private SemuaKelasAdapter eAdapter;
        ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(SidebarActivity.this);
        map = new HashMap<>();
//        llTaskName=(LinearLayout)findViewById(R.id.ll_taskName);
//        llTaskName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SidebarActivity.this, TaskListActivity.class);
//                intent.putExtra("mentor", "muarifin");
//                startActivity(intent);
//            }
//        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sessionManager = new SessionManager(this);
        //Untuk membuat nama profil dinamis
        View header = navigationView.getHeaderView(0);
        TextView tv = (TextView) header.findViewById(R.id.tv_username);
        tv.setText(UserConstant.nama);

        showSemuaKelas();
    }

    private void showSemuaKelas() {
        loading = new ProgressDialog(SidebarActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Loading..");
        loading.show();
        map = sessionManager.getDetail();
        String id_user = map.get(SessionManager.id_user);
        ApiService.service_get.getDaftarKelas("application/json").enqueue(new Callback<ArrayList<DaftarSemuaKelas>>() {
            @Override
            public void onResponse(Call<ArrayList<DaftarSemuaKelas>> call, Response<ArrayList<DaftarSemuaKelas>> response) {
                if (response.isSuccessful())
                {loading.dismiss();
                    SemuaKelasList = response.body();
                    recyclerView = findViewById(R.id.recycler_view_allkelas_list);
                    eAdapter = new SemuaKelasAdapter(SemuaKelasList,SidebarActivity.this);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);}
                    else {
                    Toast.makeText(SidebarActivity.this, "Coba lagiiiiii", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DaftarSemuaKelas>> call, Throwable t) {

            }
        });
//        ApiService.service_post.postMyClass("application/json", id_uses


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.sidebar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mentor) {
            Intent drawable = new Intent(SidebarActivity.this, MentorActivity.class);
            startActivity(drawable);
            finish();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
    }

    private void logout(){
        startActivity(new Intent(SidebarActivity.this, LoginActivity.class));
        finish();
        sessionManager.logout();
        UserConstant.clear();
    }
}
