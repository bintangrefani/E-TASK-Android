package com.example.lenovo.e_task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.menu.EnrollActivity;
import com.example.lenovo.e_task.menu.EnrollOpenActivity;
import com.example.lenovo.e_task.model.MyClass.Kelas;

import java.util.ArrayList;

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.MyClassViewHolder> {

    private ArrayList<Kelas> dataList;
    Context context;

    public MyClassAdapter(ArrayList<Kelas> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view_myclass, parent, false);
        return new MyClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyClassViewHolder holder, final int position) {
//        Toast.makeText(context, "" + dataList.get(position).getId(), Toast.LENGTH_SHORT).show();
//        holder.tvIdKelas.setText(""+dataList.get(position).getId());
        holder.tvNamaKelas.setText(dataList.get(position).getName());
        holder.cvKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inteny = new Intent(context, EnrollOpenActivity.class);
                inteny.putExtra("id_kelas", String.valueOf(dataList.get(position).getId()));
                inteny.putExtra(EnrollActivity.NAME_CLASS, dataList.get(position).getName());
                context.startActivity(inteny);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyClassViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaKelas, tvIdKelas;
        CardView cvKelas;

        MyClassViewHolder(View itemView) {
            super(itemView);
            tvNamaKelas = (TextView) itemView.findViewById(R.id.tv_nameClass);
            cvKelas=(CardView) itemView.findViewById(R.id.myClass_Click);
        }
    }
}
