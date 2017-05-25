package android.myapplicationdev.com.taskmanager;

/**
 * Created by 15017569 on 5/25/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "taskmanager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_DESC = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_NAME + " TEXT, " + COLUMN_DESC + " TEXT) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, "Buy milk");
        values.put(COLUMN_DESC, "Low fat");
        db.insert(TABLE_TASK, null, values);
        Log.i("info", "dummy records inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public ArrayList<String> getAllTask() {
        ArrayList<String> task = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TASK_NAME + "," + COLUMN_DESC + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String desc = cursor.getString(2);
                task.add(id + " " + taskName + "\n" + desc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return task;
    }

    public long insertTask(Task content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, content.getTaskName());
        values.put(COLUMN_DESC, content.getDescription());
        long result = db.insert(TABLE_TASK, null, values);
        if (result != 1){
            Log.d("DBHelper", "Insert failed");
        } else {
            Log.d("SQL Insert",""+ result); //id returned, shouldn’t be -1
        }

        db.close();
        return result;
    }

}
