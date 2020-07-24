package com.example.almostudemy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "almostUdemy.db";

    private static final String TABLE_NAME = "register";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String COURSE_TABLE_NAME = "course";
    private static final String COURSE_COLUMN_ID = "id";
    private static final String COURSE_COLUMN_TOPIC = "course_topic";
    private static final String COURSE_COLUMN_TOPIC_ID = "topic_id";
    private static final String COURSE_COLUMN_COURSE_NAME = "course_name";
    private static final String COURSE_COLUMN_SHORT_DESCRIPTION = "short_description";
    private static final String COURSE_COLUMN_LONG_DESCRIPTION = "long_description";
    private static final String COURSE_COLUMN_CONTENT = "course_content";

    private static final String JOIN_TABLE_NAME = "join_table";
    private static final String JOIN_COLUMN_STUDENT_ID = "student_id";
    private static final String JOIN_COLUMN_COURSE_ID = "course_id";

    private static final String FINISH_TABLE_NAME = "finished_courses";
    private static final String FINISH_COLUMN_STUDENT_ID = "student_id";
    private static final String FINISH_COLUMN_COURSE_ID = "course_id";


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL, " +
            COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
            COLUMN_PASSWORD + " TEXT NOT NULL " + ")";

    private static final String SQL_CREATE_ENTRIES_COURSE = "CREATE TABLE " + COURSE_TABLE_NAME + " (" +
            COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSE_COLUMN_TOPIC + " TEXT NOT NULL, " +
            COURSE_COLUMN_TOPIC_ID + " TEXT NOT NULL, " +
            COURSE_COLUMN_COURSE_NAME + " TEXT NOT NULL UNIQUE, " +
            COURSE_COLUMN_SHORT_DESCRIPTION + " TEXT NOT NULL UNIQUE, " +
            COURSE_COLUMN_LONG_DESCRIPTION + " TEXT NOT NULL UNIQUE, " +
            COURSE_COLUMN_CONTENT + " TEXT " + ")";

    private static final String SQL_CREATE_ENTRIES_JOIN = "CREATE TABLE " + JOIN_TABLE_NAME + "(" +
            JOIN_COLUMN_STUDENT_ID + " INTEGER NOT NULL, " +
            JOIN_COLUMN_COURSE_ID + " INTEGER NOT NULL)";

    private static final String SQL_CREATE_ENTRIES_FINISHED = "CREATE TABLE " + FINISH_TABLE_NAME + "(" +
            FINISH_COLUMN_STUDENT_ID + " INTEGER NOT NULL, " +
            FINISH_COLUMN_COURSE_ID + " INTEGER NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_COURSE = "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_JOIN = "DROP TABLE IF EXISTS " + JOIN_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_FINISHED = "DROP TABLE IF EXISTS " + FINISH_TABLE_NAME;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_COURSE);
        db.execSQL(SQL_CREATE_ENTRIES_JOIN);
        db.execSQL(SQL_CREATE_ENTRIES_FINISHED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_COURSE);
        db.execSQL(SQL_DELETE_ENTRIES_JOIN);
        db.execSQL(SQL_DELETE_ENTRIES_FINISHED);
        onCreate(db);
    }

    //add a student to the register table
    public long addStudent(String firstName, String lastName, String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);

        long newRow = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return newRow;
    }

    //add a course to the course table
    public long addCourse(String courseTopic, String courseId, String courseName, String shortDescription, String longDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COURSE_COLUMN_TOPIC, courseTopic);
        contentValues.put(COURSE_COLUMN_TOPIC_ID, courseId);
        contentValues.put(COURSE_COLUMN_COURSE_NAME, courseName);
        contentValues.put(COURSE_COLUMN_SHORT_DESCRIPTION, shortDescription);
        contentValues.put(COURSE_COLUMN_LONG_DESCRIPTION, longDescription);

        long newRow = db.insert(COURSE_TABLE_NAME, null, contentValues);
        db.close();
        return newRow;
    }

    //check if user pressed remember ma
    public boolean checkUser(String username, String password) {
        String[] projection = { COLUMN_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " and " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    //delete a student from the register table
    public int deleteStudent(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
        return deletedRows;
    }

    //update the password column from the register table
    public int resetPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, password);

        String selection = COLUMN_USERNAME + " LIKE ?";
        String[] selectionArgs = { username };

        int count = db.update(TABLE_NAME, values, selection, selectionArgs);

        db.close();
        return count;
    }

    //get the name of a course from course table using topicId
    public List<String> getCourseName(String topicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COURSE_COLUMN_COURSE_NAME };
        String selection = COURSE_COLUMN_TOPIC_ID + " = ?";
        String[] selectionArgs = { topicId };

        Cursor cursor = db.query(COURSE_TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_COURSE_NAME));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //get the short description of a course from course table using topicId
    public List<String> getCourseShortDescription(String topicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COURSE_COLUMN_SHORT_DESCRIPTION };
        String selection = COURSE_COLUMN_TOPIC_ID + " = ?";
        String[] selectionArgs = { topicId };

        Cursor cursor = db.query(COURSE_TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_SHORT_DESCRIPTION));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //get the long description of a course from course table using topicId
    public List<String> getCourseLongDescription(String topicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COURSE_COLUMN_LONG_DESCRIPTION };
        String selection = COURSE_COLUMN_TOPIC_ID + " = ?";
        String[] selectionArgs = { topicId };

        Cursor cursor = db.query(COURSE_TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_LONG_DESCRIPTION));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //get a studentId form register table using the username from shared pref
    public int getStudentId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COLUMN_ID };
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        int id = 0;
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    //get a courseId from course table using the course name
    public int getCourseId(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COURSE_COLUMN_ID };
        String selection = COURSE_COLUMN_COURSE_NAME + " = ?";
        String[] selectionArgs = { courseName };

        Cursor cursor = db.query(COURSE_TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        int id = 0;
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COURSE_COLUMN_ID));
        }
        cursor.close();
        db.close();

        return id;
    }

    //get first name from register table with username
    public String getStudentName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { COLUMN_FIRST_NAME };
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs,
                null, null, null);

        String name = "";
        while(cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
        }
        cursor.close();
        db.close();

        return name;
    }

    //add the courseId and studentId the join_table
    public long addLink(int studentId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(JOIN_COLUMN_STUDENT_ID, studentId);
        contentValues.put(JOIN_COLUMN_COURSE_ID, courseId);

        long newRow = db.insert(JOIN_TABLE_NAME, null, contentValues);
        db.close();
        return newRow;
    }

    //get the courseName from course table with a join query on join_table
    public List<String> getJoinCourseName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT course.course_name" +
                " FROM course" +
                " JOIN join_table on (course.id=join_table.course_id)" +
                " JOIN register on (join_table.student_id=register.id)";
        Cursor cursor = db.rawQuery(query, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_COURSE_NAME));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //get the shortDescription from course table with a join query
    public List<String> getJoinCourseDescription() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT course.short_description" +
                " FROM course" +
                " JOIN join_table on (course.id=join_table.course_id)" +
                " JOIN register on (join_table.student_id=register.id)";
        Cursor cursor = db.rawQuery(query, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_SHORT_DESCRIPTION));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //add the courseId and studentId the finished_courses table
    public long setCourseFinished(int studentId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FINISH_COLUMN_STUDENT_ID, studentId);
        contentValues.put(FINISH_COLUMN_COURSE_ID, courseId);

        long newRow = db.insert(FINISH_TABLE_NAME, null, contentValues);
        db.close();
        return newRow;
    }

    //get the courseName from course table with join query on finished table
    public List<String> getFinishedCourseName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT course.course_name" +
                " FROM course" +
                " JOIN finished_courses on (course.id=finished_courses.course_id)" +
                " JOIN register on (finished_courses.student_id=register.id)";
        Cursor cursor = db.rawQuery(query, null);

        List<String> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(COURSE_COLUMN_COURSE_NAME));
            names.add(name);
        }
        cursor.close();
        db.close();

        return names;
    }

    //add specific image to inside_my_course layout
    public int addImage(String courseName) {
        if(courseName.equals("Java")) {
            return R.drawable.java;
        }
        else if(courseName.equals("C#")) {
            return R.drawable.csharp;
        }
        else if(courseName.equals("C")) {
            return R.drawable.c;
        }
        else if(courseName.equals("React")) {
            return R.drawable.react;
        }
        else if(courseName.equals("HTML")) {
            return R.drawable.html;
        }
        else if(courseName.equals("PHP")) {
            return R.drawable.php;
        }
        else if(courseName.equals("Python")) {
            return R.drawable.python;
        }
        else if(courseName.equals("SQL")) {
            return R.drawable.sql;
        }
        else {
            return R.drawable.r;
        }
    }
}
