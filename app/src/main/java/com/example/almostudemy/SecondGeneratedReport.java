package com.example.almostudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.List;

public class SecondGeneratedReport extends AppCompatActivity {
    DatabaseHelper db;
    TextView tvStudent, tvCourse;
    Button btnExport;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_generated_report);
        db =  new DatabaseHelper(getApplicationContext());
        tvStudent = findViewById(R.id.tvReport2Student);
        tvCourse = findViewById(R.id.tvrReport2Courses);
        btnExport = findViewById(R.id.btnExport);
        preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String firstName = db.getStudentName(username);
        List<String> listNames = db.getFinishedCourseName();
        StringBuilder sb = new StringBuilder();
        for(String name : listNames) {
            sb.append(name + ", ");
        }
        tvCourse.setText(sb.toString());

        tvStudent.setText(firstName);

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;
                try {
                    preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    String username = preferences.getString("username", "");
                    String firstName = db.getStudentName(username);
                    List<String> listNames = db.getFinishedCourseName();
                    StringBuilder sb = new StringBuilder();
                    for(String name : listNames) {
                        sb.append(name + ", ");
                    }
                    fos = openFileOutput("coursesFinished.txt", MODE_PRIVATE);
                    StringBuilder builder = new StringBuilder();

                    builder.append("Courses finished" + "\n\n" + "First Name: " +
                            firstName + "\n" + "Courses: " + sb.toString());

                    fos.write(builder.toString().getBytes());
                    Toast.makeText(getApplicationContext(), "Export Successful", Toast.LENGTH_SHORT).show();
                } catch(Throwable e) {
                    Toast.makeText(getApplicationContext(), "Export Error", Toast.LENGTH_SHORT).show();
                }
                try {
                    fos.close();
                } catch (Throwable e) {
                    Toast.makeText(getApplicationContext(), "Export Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
