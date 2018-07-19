package com.example.lenovo.e_task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.menu.TaskDetailActivity;
import com.example.lenovo.e_task.model.DetailMyClass.Mentor;
import com.example.lenovo.e_task.model.DetailMyClass.Task;
import com.example.lenovo.e_task.model.MyClass.Kelas;

public class DetailMyClassAdapter extends RecyclerView.Adapter<DetailMyClassAdapter.DetailMyClassViewHolder> {

    private ArrayList<Task> taskList;
    private ArrayList<Mentor> mentorList;
    private ArrayList<Kelas> kelasList;
    Context context;

    public DetailMyClassAdapter(ArrayList<Task> taskList,ArrayList<Mentor> mentorList, Context context) {
        this.mentorList = mentorList;
        this.taskList = taskList;
        this.context = context;
    }

    @Override
    public DetailMyClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view_detail_myclass, parent, false);
        return new DetailMyClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailMyClassViewHolder holder, final int position) {
//        Toast.makeText(context, "" + dataList.get(position).getId(), Toast.LENGTH_SHORT).show();
        holder.tvDeadline.setText(taskList.get(position).getDeadline());
        holder.tvDeskripsi.setText(taskList.get(position).getDescription());
        holder.tvNameTask.setText(taskList.get(position).getName());
        holder.btnToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("id", String.valueOf(taskList.get(position).getId()));
                intent.putExtra("name", taskList.get(position).getName());
                intent.putExtra("mentor", mentorList.get(position).getName());
                intent.putExtra("deskripsi", taskList.get(position).getDescription());
                intent.putExtra("create", taskList.get(position).getCreatedAt());
                intent.putExtra("deadline", taskList.get(position).getDeadline());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class DetailMyClassViewHolder extends RecyclerView.ViewHolder {

        TextView tvDeadline, tvDeskripsi, tvNameTask;
        CardView cvKelas;
        Button btnToDetail;

        DetailMyClassViewHolder(View itemView) {
            super(itemView);
            tvDeadline = (TextView) itemView.findViewById(R.id.tv_deadline);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.tv_desc);
            tvNameTask = (TextView) itemView.findViewById(R.id.tv_nametask);
            cvKelas=(CardView) itemView.findViewById(R.id.detail_Class_click);
            btnToDetail=(Button) itemView.findViewById(R.id.btn_menujudetail);
        }
    }

}
