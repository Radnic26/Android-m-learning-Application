package com.example.almostudemy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CoursesFragment extends Fragment {
    public DatabaseHelper db;
    public View view;
    public View row;
    public ListView listView;
    public String[] cName;
    public String[] cDescription;
    public String[] cContent;
    int[] images = { R.drawable.java2, R.drawable.csharp, R.drawable.c, R.drawable.react,
            R.drawable.html, R.drawable.php, R.drawable.python,
            R.drawable.sql, R.drawable.r };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_courses, container, false);
        listView = view.findViewById(R.id.customListViewMyCourses);

        db = new DatabaseHelper(getContext());

        try{
            List<String> listNames = db.getJoinCourseName();
            List<String> listDescriptions = db.getJoinCourseDescription();
            cName = new String[listNames.size()];
            cDescription = new String[listDescriptions.size()];
            images = new int[listNames.size()];
            int i = 0;
            for(String name : listNames) {
                cName[i] = name;
                images[i] = db.addImage(name);
                i++;
            }

            i = 0;
            for(String description : listDescriptions) {
                cDescription[i] = description;
                i++;
            }
        } catch(Throwable e){
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(getContext(), cName, cDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i = 0; i < images.length; i++) {
                    if (position == i) {
                        Intent intent = new Intent(getContext(), InsideMyCourse.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("image", images[i]);
                        intent.putExtras(bundle);
                        intent.putExtra("title", cName[i]);
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] rTitle;
        String[] rDescription;
        int[] rImages;

        MyAdapter(Context context, String[] title, String[] description, int[] images) {
            super(context, R.layout.row_catalog, R.id.rowTitle, title);
            this.context = context;
            this.rTitle = title;
            this.rDescription = description;
            this.rImages = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_course, parent, false);
            ImageView images = row.findViewById(R.id.imageCourse);
            TextView myTitle = row.findViewById(R.id.rowTitleCourse);
            TextView myDescription = row.findViewById(R.id.rowDescriptionCourse);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }


    }
}
