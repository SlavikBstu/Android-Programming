package by.belstu.lab_05db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Владислав on 28.10.2016.
 */
public class DB_Controller extends SQLiteOpenHelper  {
    public DB_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Lab_05DB.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE INFO (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "F FLOAT NOT NULL, T TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS INFO;");
        onCreate(db);
    }

    public long insert_info(Integer id, Float f, String t){
        ContentValues contentValues = new ContentValues();
        if(id > 0)
        contentValues.put("ID", id);
        contentValues.put("F", f);
        contentValues.put("T", t);
        return this.getWritableDatabase().insertOrThrow("INFO", "", contentValues);
    }

    public void delete_info(Integer id){
        this.getWritableDatabase().delete("INFO", "ID='" + id + "'", null);
    }

    public void update_info(Float old_f, Float new_f, String old_t, String new_t){
        this.getWritableDatabase().execSQL("UPDATE INFO SET F='" + new_f + "'WHERE F='" + old_f + "'");
        this.getWritableDatabase().execSQL("UPDATE INFO SET T='" + new_t + "'WHERE T='" + old_t + "'");
    }

    public void select_raw_info(EditText id, EditText f, EditText t){
        int ID = 0;
        float F = 0;
        String T = "";
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM INFO WHERE ID = '" +
               id.getText() + "'" , null);
        if(cursor.moveToFirst()){
            ID = cursor.getInt(0);
            F = cursor.getFloat(
                    1);
            T = cursor.getString(2);
            id.setText(String.valueOf(ID));
            f.setText(String.valueOf(F));
            t.setText(String.valueOf(T));
        }
    }

    public void select_info(EditText id, EditText f, EditText t){
        int ID = 0;
        float F = 0;
        String T = "";
        Cursor cursor = this.getReadableDatabase().query("INFO", null, "ID=?",
                new String[]{id.getText().toString()}, null, null, null);
        if(cursor.moveToFirst()) {
            ID = cursor.getInt(0);
            F = cursor.getFloat(
                    1);
            T = cursor.getString(2);
            id.setText(String.valueOf(ID));
            f.setText(String.valueOf(F));
            t.setText(String.valueOf(T));
        }
    }

    public void list_all_students(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM INFO", null);
        while(cursor.moveToNext()){
            textView.append("ID: " + Integer.valueOf(cursor.getString(0)) +
                    " F: " + Float.valueOf(cursor.getString(1)) +
                    " T: " + cursor.getString(2) + "\n");
        }
    }
}
