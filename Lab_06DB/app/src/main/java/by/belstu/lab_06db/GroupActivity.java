package by.belstu.lab_06db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class GroupActivity extends Activity {


    DBController sqLiteHelper  = new DBController(this,"Lab_06DB.db",null,7);
    SQLiteDatabase db;
    EditText ID_group;
    EditText Faculty;
    EditText Course;
    EditText Name_group;
    EditText Head;
    EditText NewIDGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        ID_group = (EditText)findViewById(R.id.editID_group);
        Faculty=(EditText)findViewById(R.id.editFaculty);
        Course = (EditText)findViewById(R.id.editCourse);
        Name_group = (EditText)findViewById(R.id.editName_group);
        Head = (EditText)findViewById(R.id.editHead);
        NewIDGroup = (EditText)findViewById(R.id.editNewIDgroup);

        db = sqLiteHelper.getWritableDatabase();

    }
    public  void AddGroup(View view){

        try{
            String faculty = Faculty.getText().toString();
            String name_gr = Name_group.getText().toString();
            String head = Head.getText().toString();

            int course = Integer.parseInt(Course.getText().toString());

            ContentValues contentValues = new ContentValues();
            contentValues.put("IDGROUP",ID_group.getText().toString());
            contentValues.put("FACULTY",faculty);
            contentValues.put("COURSE",course);
            contentValues.put("NAME",name_gr);
            contentValues.put("HEAD",head);
            db.insert("STUDGROUPS",null,contentValues);

            Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(GroupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public  void BackToMain(View view){
        Intent intent = new Intent(GroupActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void Delete(View view){
        try {
            String idgr = ID_group.getText().toString();
            int del = db.delete("STUDGROUPS","IDGROUP =?",new String[]{idgr});
            if(del == 0){
                Toast.makeText(GroupActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLiteConstraintException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Update(View view){
        try {
            String idGroup = ID_group.getText().toString();
            String fac = Faculty.getText().toString();
            String cour = Course.getText().toString();
            String name_gro = Name_group.getText().toString();
            String head_ = Head.getText().toString();
            String newIdGroup = NewIDGroup.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put("FACULTY",fac);
            contentValues.put("COURSE",cour);
            contentValues.put("NAME",name_gro);
            contentValues.put("HEAD",head_);
            contentValues.put("IDGROUP",newIdGroup);
            int upd = db.update("STUDGROUPS",contentValues,"IDGROUP = ?", new String[]{idGroup});

            NewIDGroup.setText("");
            Faculty.setText("");
            Course.setText("");
            Name_group.setText("");
            Head.setText("");
            if (upd == 0) {
                Toast.makeText(GroupActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLiteConstraintException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
