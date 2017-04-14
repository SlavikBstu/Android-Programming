package by.belstu.lab_89;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by Владислав on 04.11.2016.
 */
public class DBController extends SQLiteOpenHelper {

    public DBController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Lab_891011DB.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STUDENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "NAME TEXT NOT NULL," +
                "SURNAME TEXT NOT NULL," +
                "USERNAME TEXT NOT NULL," +
                "PASSWORD TEXT NOT NULL," +
                "AGE INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENTS;");
        onCreate(db);
    }

    /*public void list_all_students(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS", null);
        while(cursor.moveToNext()){
            textView.append(cursor.getString(1) + cursor.getString(2) + Integer.valueOf(cursor.getString(5)));
        }
    }*/

    public void delete_info(String username){
        this.getWritableDatabase().delete("STUDENTS", "USERNAME='" + username + "'", null);
    }
}
