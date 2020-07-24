package com.example.almostudemy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InsideMyCourse extends AppCompatActivity {
    ImageView imageView;
    TextView tvContent, tvTitle;
    Button btnFinishCourse;
    int position;
    DatabaseHelper db;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_my_course);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageView = findViewById(R.id.inside_my_course_image);
        tvContent = findViewById(R.id.tvContent);
        tvTitle = findViewById(R.id.tvMyTitleText);
        btnFinishCourse = findViewById(R.id.btnFinished);
        db = new DatabaseHelper(getApplicationContext());

        if(position == 0) {
            Intent intent = getIntent();

            Bundle bundle = this.getIntent().getExtras();
            int pic = bundle.getInt("image");
            String aTitle = intent.getStringExtra("title");

            imageView.setImageResource(pic);
            tvTitle.setText(aTitle);

            actionBar.setTitle(aTitle);
        }

        btnFinishCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                int studentId = db.getStudentId(username);
                int courseId = db.getCourseId(tvTitle.getText().toString().trim());
                db.setCourseFinished(studentId, courseId);

                finish();
            }
        });
    }
}
