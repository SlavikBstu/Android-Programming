package by.belstu.lab_06db;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {


    ListView showStud;
    EditText StudentShow;
    DBController sqLiteHelper = new DBController(this,"Lab_06DB.db",null,7);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = sqLiteHelper.getReadableDatabase();
        showStud = (ListView)findViewById(R.id.showStudentsList);
        StudentShow = (EditText)findViewById(R.id.editShowIdGroup);

    }

    public void ShowStud(View view){
        try {
            final ArrayList<String> nameStudents = new ArrayList<String>();
            String idGr = StudentShow.getText().toString();
            Cursor cursor = db.rawQuery("SELECT IDGROUP,IDSTUDENT,NAME FROM STUDENTS WHERE IDGROUP = ?", new String[]{idGr});
            if (cursor.moveToFirst()) {
                do {
                    int idgroup = cursor.getInt(0);
                    int idstud = cursor.getInt(1);
                    String name = "IDStud: " + idstud + " \nNameStud: " + cursor.getString(2);
                    nameStudents.add(name);
                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameStudents);
            showStud.setAdapter(arrayAdapter);
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showGroup(View view){
        try {
            final ArrayList<String> groupST = new ArrayList<String>();
            String idGroup = StudentShow.getText().toString();
            Cursor cursor = db.rawQuery("SELECT IDGROUP,FACULTY,COURSE,NAME,HEAD FROM STUDGROUPS WHERE IDGROUP = ?", new String[]{idGroup});
            if (cursor.moveToFirst()) {
                do {
                    int idgroup = cursor.getInt(0);
                    String faculty = cursor.getString(1);
                    int course = cursor.getInt(2);
                    String name = cursor.getString(3);
                    String head = "Faculty: " + faculty + " \nCourse: " + course + " \nName: "+ name + " \nHead " + cursor.getString(4);
                    groupST.add(head);
                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupST);
            showStud.setAdapter(arrayAdapter);
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public  void Group(View view){
        Intent intent = new Intent(MainActivity.this,GroupActivity.class);
        startActivity(intent);
    }
    public void Student(View view){
        Intent intent = new Intent(MainActivity.this,StudentActivity.class);
        startActivity(intent);
    }

}
