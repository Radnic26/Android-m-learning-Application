package com.example.almostudemy;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CatalogFragment extends Fragment {
    public DatabaseHelper db;
    public View view;
    public View row;
    public ListView listView;
    public String[] cTitle = new String[9];
    public String[] cDescription = new String[9];
    public String[] cLongDescription = new String[9];
    int[] images = { R.drawable.java2, R.drawable.csharp, R.drawable.c, R.drawable.react,
                     R.drawable.html, R.drawable.php, R.drawable.python,
                     R.drawable.sql, R.drawable.r };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        listView = view.findViewById(R.id.customListView);
        db = new DatabaseHelper(getContext());
        List<String> cNames = db.getCourseName("PLC");
        List<String> cShortDescription = db.getCourseShortDescription("PLC");
        List<String> longDescription = db.getCourseLongDescription("PLC");
        int i = 0;
        for(String name : cNames) {
            cTitle[i] = name;
            i++;
        }

        cNames = db.getCourseName("WTC");
        for(String name : cNames) {
            cTitle[i] = name;
            i++;
        }

        cNames = db.getCourseName("DSMLC");
        for(String name : cNames) {
            cTitle[i] = name;
            i++;
        }

        i = 0;
        for(String name : cShortDescription) {
            cDescription[i] = name;
            i++;
        }

        cShortDescription = db.getCourseShortDescription("WTC");
        for(String name : cShortDescription) {
            cDescription[i] = name;
            i++;
        }

        cShortDescription = db.getCourseShortDescription("DSMLC");
        for(String name : cShortDescription) {
            cDescription[i] = name;
            i++;
        }

        i = 0;
        for(String name : longDescription) {
            cLongDescription[i] = name;
            i++;
        }

        longDescription = db.getCourseLongDescription("WTC");
        for(String name : longDescription) {
            cLongDescription[i] = name;
            i++;
        }

        longDescription = db.getCourseLongDescription("DSMLC");
        for(String name : longDescription) {
            cLongDescription[i] = name;
            i++;
        }


        MyAdapter adapter = new MyAdapter(getContext(), cTitle, cDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i = 0; i < images.length; i++) {
                    if (position == i) {
                        Intent intent = new Intent(getContext(), InsideCourseActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("image", images[i]);
                        intent.putExtras(bundle);
                        intent.putExtra("title", cTitle[i]);
                        intent.putExtra("description", cDescription[i]);
                        intent.putExtra("longDescription", cLongDescription[i]);
                        intent.putExtra("position", "" + i);
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
            row = layoutInflater.inflate(R.layout.row_catalog, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.rowTitle);
            TextView myDescription = row.findViewById(R.id.rowDescription);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
}
