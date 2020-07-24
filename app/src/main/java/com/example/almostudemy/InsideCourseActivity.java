package com.example.almostudemy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InsideCourseActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvTitle, tvDescription, tvLongDescription;
    Button btnEnroll;
    int position;
    DatabaseHelper db;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_course);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageView = findViewById(R.id.inside_course_image);
        tvTitle = findViewById(R.id.tvTitleText);
        tvDescription = findViewById(R.id.tvDescriptionText);
        tvLongDescription = findViewById(R.id.tvLongDescriptionText);
        btnEnroll = findViewById(R.id.btnEnroll);
        db = new DatabaseHelper(getApplicationContext());
        preferences = getSharedPreferences("checkbox", MODE_PRIVATE);

        if(position == 0) {
            Intent intent = getIntent();

            Bundle bundle = this.getIntent().getExtras();
            int pic = bundle.getInt("image");
            String aTitle = intent.getStringExtra("title");
            String aDescription = intent.getStringExtra("description");
            String aLongDescription = intent.getStringExtra("longDescription");

            imageView.setImageResource(pic);
            tvTitle.setText(aTitle);
            tvDescription.setText(aDescription);
            tvLongDescription.setText(aLongDescription);

            actionBar.setTitle(aTitle);
        }

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = preferences.getString("username", "");
                int studentId = db.getStudentId(username);
                int courseId = db.getCourseId(tvTitle.getText().toString());
                db.addLink(studentId, courseId);

                finish();
            }
        });

    }
}
