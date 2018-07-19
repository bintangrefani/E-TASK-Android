package com.example.lenovo.e_task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.SessionManager.SessionManager;
import com.example.lenovo.e_task.api.ApiService;
import com.example.lenovo.e_task.menu.EnrollActivity;
import com.example.lenovo.e_task.menu.EnrollOpenActivity;
import com.example.lenovo.e_task.menu.MentorActivity;
import com.example.lenovo.e_task.menu.SidebarActivity;
import com.example.lenovo.e_task.model.DaftarSemuaKelas.DaftarSemuaKelas;
import com.example.lenovo.e_task.model.ToEnroll.ToEnroll;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.lenovo.e_task.SessionManager.SessionManager.id_user;

public class SemuaKelasAdapter extends RecyclerView.Adapter<SemuaKelasAdapter.SemuaKelasViewHolder> {

    private ArrayList<DaftarSemuaKelas> SemuaKelasList;
    Context context;

    public SemuaKelasAdapter(ArrayList<DaftarSemuaKelas> SemuaKelasList, Context context) {
        this.SemuaKelasList = SemuaKelasList;
        this.context = context;
    }

    @Override
    public SemuaKelasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view_all_class, parent, false);
        return new SemuaKelasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SemuaKelasViewHolder holder, final int position) {
//        Toast.makeText(context, "" + dataList.get(position).getId(), Toast.LENGTH_SHORT).show();
//        holder.tvIdKelas.setText(""+SemuaKelasList.get(position).getId());
        holder.tvNamaKelas.setText(SemuaKelasList.get(position).getName());
        holder.sessionManager = new SessionManager(context);
        holder.map = new HashMap<>();
        holder.map = holder.sessionManager.getDetail();
        final String id_user = holder.map.get(SessionManager.id_user);
        holder.cvKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                cekEnroll();

                final String id_kelas= String.valueOf(SemuaKelasList.get(position).getId());
                ApiService.service_post.postToEnroll("application/json", id_user, id_kelas).enqueue(new Callback<ToEnroll>() {
                    @Override
                    public void onResponse(Call<ToEnroll> call, Response<ToEnroll> response) {
                        if (response.body().getTest() == null)
                        {
                            Intent intent = new Intent(context, EnrollActivity.class);
                            intent.putExtra("id_kelas", id_kelas);
                            context.startActivity(intent);
                            Toast.makeText(context, "Anda Belum enrol", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent = new Intent(context, MentorActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Anda Sudah Enroll", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ToEnroll> call, Throwable t) {
                        Toast.makeText(context, "Jaringamu bos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
//
//    private void cekEnroll() {
//        String id_kelas= String.valueOf(SemuaKelasList.get(0).getId());
//        ApiService.service_post.postToEnroll("application/json", "11", "1").enqueue(new Callback<ToEnroll>() {
//            @Override
//            public void onResponse(Call<ToEnroll> call, Response<ToEnroll> response) {
//                if (response.body().getTest() == null)
//                {
//                    Intent intent = new Intent(context, EnrollActivity.class);
//                    intent.putExtra("id_kelas", String.valueOf(SemuaKelasList.get(0).getId()));
//                    context.startActivity(intent);
//                    Toast.makeText(context, "Anda Belum Enrol", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Intent intent = new Intent(context, MentorActivity.class);
//                    context.startActivity(intent);
//                    Toast.makeText(context, "Anda Sudah Enroll", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ToEnroll> call, Throwable t) {
//                Toast.makeText(context, "Jaringamu bos", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return SemuaKelasList.size();
    }

    class SemuaKelasViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKelas;
        CardView cvKelas;
        SessionManager sessionManager;
        HashMap<String,String> map;

        SemuaKelasViewHolder(View itemView) {
            super(itemView);
            tvNamaKelas = (TextView) itemView.findViewById(R.id.tv_nama_all_class);
            cvKelas=(CardView) itemView.findViewById(R.id.allClass_click);
            sessionManager = new SessionManager(context);
            map = new HashMap<>();
//            map = sessionManager.getDetail();
//            String id_user = map.get(SessionManager.id_user);
        }
    }
}

