package by.belstu.lab_06db;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends Activity {

    DBController sqLiteHelper = new DBController(this,"Lab_06DB.db",null,7);
    SQLiteDatabase db;
    EditText IdGroup;
    EditText IdStudent;
    EditText NameStudent;
    ListView showStudcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        IdGroup = (EditText)findViewById(R.id.editIDgroup);
        IdStudent = (EditText)findViewById(R.id.editID_student);
        NameStudent = (EditText)findViewById(R.id.editName_student);
        showStudcount = (ListView)findViewById(R.id.StudCountlistView);
        db = sqLiteHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_key=ON");
        db.execSQL(
                "DROP TRIGGER IF EXISTS I_TRIGGER;"
        );
        db.execSQL(
                "DROP TRIGGER IF EXISTS D_TRIGGER;"
        );
        db.execSQL(
                "CREATE TRIGGER IF NOT EXISTS I_TRIGGER " +
                        "BEFORE INSERT ON STUDENTS " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "SELECT " +
                        "CASE " +
                        "WHEN (SELECT COUNT(STUDENTS.IDSTUDENT) FROM STUDENTS WHERE STUDENTS.IDGROUP = NEW.IDGROUP) >= 6 " +
                        "THEN RAISE(ABORT, 'Too much students') " +
                        "END; " +
                        "END;"
        );
        db.execSQL(
                "CREATE TRIGGER IF NOT EXISTS D_TRIGGER " +
                        "BEFORE DELETE ON STUDENTS " +
                        "WHEN (SELECT COUNT(*) FROM STUDENTS STD WHERE STD.IDGROUP = OLD.IDGROUP) < 3 AND " +
                        "(SELECT COUNT(*) " +
                        "FROM STUDGROUPS WHERE STUDGROUPS.IDGROUP = OLD.IDGROUP) != 0 " +
                        "BEGIN " +
                        "SELECT RAISE(ABORT, 'DELETE TRIGGER ERROR'); " +
                        "END;"
        );
        /*db.execSQL("CREATE VIEW countStudents as SELECT Tgroups.IDGROUP, Tgroups.HEAD, COALESCE(Tstudents.csn,0)" +
                "From STUDGROUPS as Tgroups left join (" +
                " SELECT s.IDGROUP, Count(s.NAME)csn" +
                " From STUDENTS as s" +
                " group by s.IDGROUP" +
                ") as Tstudents ON Tstudents.IDGROUP = Tgroups.IDGROUP;");*/
    }

    public void CountStud(View view){
        try {
            final ArrayList<String> CountStudents = new ArrayList<String>();
            Cursor cursor = db.rawQuery("SELECT * FROM countStudents;", null);
            if (cursor.moveToFirst()) {
                do {
                    String str = "IDGroup: " + cursor.getInt(0) + ", Head: " + cursor.getString(1) + ", Count: " + cursor.getInt(2);
                    CountStudents.add(str);
                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CountStudents);
            showStudcount.setAdapter(arrayAdapter);
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void DelStud(View view){
        try{
            String idgr = IdStudent.getText().toString();
            int del = db.delete("STUDENTS","IDSTUDENT =?",new String[]{idgr});
            if(del == 0){
                Toast.makeText(StudentActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void AddStudent(View view){
        try {
                String nameStud = NameStudent.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("IDGROUP", IdGroup.getText().toString());
                contentValues.put("IDSTUDENT", IdStudent.getText().toString());
                contentValues.put("NAME", nameStud);
                db.insertOrThrow("STUDENTS", null, contentValues);

                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
        }catch (Exception e ){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(StudentActivity.this);
            dialog.setTitle("Enter new info");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText id = new EditText(StudentActivity.this);
            //id.setText(IdGroup.getText().toString());
            id.setHint("IDGroup: ");
            layout.addView(id);

            final EditText faculty = new EditText(StudentActivity.this);
            faculty.setHint("Faculty: ");
            layout.addView(faculty);

            final EditText course = new EditText(StudentActivity.this);
            course.setHint("Course: ");
            layout.addView(course);

            final EditText name = new EditText(StudentActivity.this);
            name.setHint("Name: ");
            layout.addView(name);

            final EditText head = new EditText(StudentActivity.this);
            head.setHint("Head: ");
            layout.addView(head);

            dialog.setView(layout);

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("IDGROUP",id.getText().toString());
                        contentValues.put("FACULTY",faculty.getText().toString());
                        contentValues.put("COURSE",course.getText().toString());
                        contentValues.put("NAME",name.getText().toString());
                        contentValues.put("HEAD",head.getText().toString());
                        db.insertOrThrow("STUDGROUPS", null, contentValues);

                    } catch (SQLiteException e) {
                        Toast.makeText(StudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }
    }
    public  void MainBack(View view){
        Intent intent = new Intent(StudentActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
