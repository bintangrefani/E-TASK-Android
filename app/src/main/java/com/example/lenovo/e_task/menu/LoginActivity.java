package com.example.lenovo.e_task.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.UserConstant.UserConstant;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.LoginRegister.Data;
import com.example.lenovo.e_task.model.LoginRegister.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lenovo.e_task.UserConstant.UserConstant.nama;

public class LoginActivity extends AppCompatActivity {
    private EditText etlEmail, etlPassword;
    private Button btnlLogin;
    private TextView tvlSignUp;
    private String slEmail, slPassword;
    ProgressDialog loading ;

    //Deklarasi kode session manager ke dalam LoginActivity
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        etlEmail = (EditText)findViewById(R.id.etl_email);
        etlPassword = (EditText)findViewById(R.id.etl_password);


        btnlLogin = (Button)findViewById(R.id.btnl_login);
        btnlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slEmail = etlEmail.getText().toString();
                slPassword=etlPassword.getText().toString();
                if (!slEmail.isEmpty() && !slPassword.isEmpty())
                {
                    doLogin(slEmail, slPassword);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvlSignUp = (TextView)findViewById(R.id.tvl_signUp);
        tvlSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void doLogin(String email, String password) {
        loading = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Authenticating..");
        loading.show();

        ApiService.service_post.postLogin("application/json",email,password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.code() == 200 )
                {
                    loading.dismiss();
                    //mengambil data user di session manager
                    Data data = response.body().getData();
                    sessionManager.createSession(data.getName(),String.valueOf(data.getId()));
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent dashboard = new Intent(LoginActivity.this, SidebarActivity.class);
                    dashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    assignConstant(response.body().getData().getName());
                    startActivity(dashboard);
                    finish();
                }

                else if (response.code() == 404)
                {
                    Toast.makeText(LoginActivity.this, "Email Anda Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

                else
                {
                    Toast.makeText(LoginActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(LoginActivity.this, "Tidak ada jaringan, Periksa koneksi anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void assignConstant(String nama) {
        UserConstant.nama = nama;
    }
}

