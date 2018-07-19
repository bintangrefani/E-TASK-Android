package com.example.lenovo.e_task.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.e_task.R;
import com.example.lenovo.e_task.model.DetailMyClass.Task;

public class TaskListActivity extends AppCompatActivity {
    TextView textTitle;
    LinearLayout child_expand;
    ImageView backlist_btn;
    Button taskdetail_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        backlist_btn = (ImageView) findViewById(R.id.backlist_btn);
        backlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskListActivity.this, SidebarActivity.class));
            }
        });

        taskdetail_btn = (Button) findViewById(R.id.taskdetail_btn);
        taskdetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(TaskListActivity.this, TaskDetailActivity.class));
                Intent intent = new Intent(TaskListActivity.this , TaskDetailActivity.class);
                intent.putExtra("mentor", "Muarifin");
                intent.putExtra("name", "Project Android");
                intent.putExtra("deskripsi" , "Tugas dikumpulkan dalam bentuk png/jpg");
                intent.putExtra("deadline", "2018-06-13 12:00:00");
                startActivity(intent);

            }
        });

        textTitle = (TextView) findViewById(R.id.textTitle);
        child_expand = (LinearLayout) findViewById(R.id.child_expand);
        child_expand.setVisibility(View.GONE);

        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (child_expand.isShown()) {
                    child_expand.setVisibility(View.GONE);
                    child_expand.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                } else {
                    child_expand.setVisibility(View.VISIBLE);
                    child_expand.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                }
            }
        });
    }
}
