package by.belstu.lab_89;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {
    static String username, name, surname, password, age;
    DBController controller = new DBController(this, "Lab_891011DB.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*final EditText etName = (EditText)findViewById(R.id.etName);
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final EditText etAge = (EditText)findViewById(R.id.etAge);*/
    }

    public void Register(View view) {
        MD5 hash = new MD5();

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);

        Button bRegister = (Button) findViewById(R.id.bRegister);

        try {
            username = etUsername.getText().toString();
            surname = etSurname.getText().toString();
            name = etName.getText().toString();
            password = etPassword.getText().toString();
            age = etAge.getText().toString();


            Cursor cursor = controller.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS WHERE USERNAME='"+username+"'", null);
            cursor.getCount();
            if(cursor.moveToFirst())
            {
                Integer ID = cursor.getInt(0);
                String name_DB = cursor.getString(1);
                String surname_DB = cursor.getString(2);
                String username_DB = cursor.getString(3);
                String password_DB = cursor.getString(4);
                Integer age_DB = cursor.getInt(5);
            }
            cursor.close();
            if(name.equals(" ") || surname.equals(" ") || username.equals(" ") || password.equals(" ") || age.equals(""))
            {
                Toast.makeText(RegisterActivity.this, "One or more fields are empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(cursor.getCount()==0){
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", name);
                contentValues.put("SURNAME", surname);
                contentValues.put("USERNAME", username);
                contentValues.put("PASSWORD", password);
                contentValues.put("AGE", Integer.valueOf(age));
                controller.getWritableDatabase().insertOrThrow("STUDENTS", "", contentValues);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.finish();
                startActivity(intent);
            }
            else{
                Toast.makeText(RegisterActivity.this, "Already exist such user!", Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}