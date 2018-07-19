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
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.model.LoginRegister.RegisterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etrNama, etrEmail, etrPassword;
    private TextView btnSignUp, btnCancelSignUp;
    private String srnama, sremail, srpassword;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etrNama=(EditText)findViewById(R.id.etr_name);
        etrEmail=(EditText)findViewById(R.id.etr_email);
        etrPassword=(EditText)findViewById(R.id.etr_password);


        btnSignUp = (TextView) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                srnama= etrNama.getText().toString();
                sremail = etrEmail.getText().toString();
                srpassword= etrPassword.getText().toString();
                if (!srnama.isEmpty() && !sremail.isEmpty() && !srpassword.isEmpty())
                {
                    doRegister(srnama, sremail, srpassword);
                }else {
                    Toast.makeText(RegisterActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelSignUp = (TextView) findViewById(R.id.btn_cancelsignup);
        btnCancelSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void doRegister(String nama, String email, String password) {
        loading = new ProgressDialog(RegisterActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        loading.setMessage("Authenticating..");
        loading.show();

        ApiService.service_post.postRegister("Application/json", nama, email, password).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.code() == 201 )
                {
                    loading.dismiss();
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent dashboard = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(dashboard);
                    finish();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Data tidak valid", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, "Tidak ada jaringan, Periksa koneksi anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
