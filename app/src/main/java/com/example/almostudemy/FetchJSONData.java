package com.example.almostudemy;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchJSONData extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    DatabaseHelper db;

    public FetchJSONData(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = new DatabaseHelper(mContext);
        HttpURLConnection connection = null;
        try {
            URL u = new URL("https://api.myjson.com/bins/b1q8s");
            connection = (HttpURLConnection) u.openConnection();
            connection.connect();


            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                result.append(line + "\n");
            }
            buffer.close();

            for(int i = 0; i < 3; i++) {

                JSONObject jsonObject = new JSONObject(result.toString());

                JSONArray courseTopics = jsonObject.getJSONArray("CoursesTopics");
                JSONObject jsonObject2 = courseTopics.getJSONObject(i);
                String courseTopic = jsonObject2.getString("topicName");
                String courseId = jsonObject2.getString("topicId");

                JSONArray courses = jsonObject2.getJSONArray("courses");

                JSONObject jsonObject3 = courses.getJSONObject(0);
                String courseName = jsonObject3.getString("courseName");
                String courseShortDescription = jsonObject3.getString("courseShortDescription");
                String courseLongDescription = jsonObject3.getString("courseLongDescription");

                db.addCourse(courseTopic, courseId, courseName, courseShortDescription, courseLongDescription);
            }

            for(int i = 0; i < 3; i++) {

                JSONObject jsonObject = new JSONObject(result.toString());

                JSONArray courseTopics = jsonObject.getJSONArray("CoursesTopics");
                JSONObject jsonObject2 = courseTopics.getJSONObject(i);
                String courseTopic = jsonObject2.getString("topicName");
                String courseId = jsonObject2.getString("topicId");

                JSONArray courses = jsonObject2.getJSONArray("courses");

                JSONObject jsonObject3 = courses.getJSONObject(1);
                String courseName = jsonObject3.getString("courseName");
                String courseShortDescription = jsonObject3.getString("courseShortDescription");
                String courseLongDescription = jsonObject3.getString("courseLongDescription");

                db.addCourse(courseTopic, courseId, courseName, courseShortDescription, courseLongDescription);
            }

            for(int i = 0; i < 3; i++) {

                JSONObject jsonObject = new JSONObject(result.toString());

                JSONArray courseTopics = jsonObject.getJSONArray("CoursesTopics");
                JSONObject jsonObject2 = courseTopics.getJSONObject(i);
                String courseTopic = jsonObject2.getString("topicName");
                String courseId = jsonObject2.getString("topicId");

                JSONArray courses = jsonObject2.getJSONArray("courses");

                JSONObject jsonObject3 = courses.getJSONObject(2);
                String courseName = jsonObject3.getString("courseName");
                String courseShortDescription = jsonObject3.getString("courseShortDescription");
                String courseLongDescription = jsonObject3.getString("courseLongDescription");

                db.addCourse(courseTopic, courseId, courseName, courseShortDescription, courseLongDescription);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
