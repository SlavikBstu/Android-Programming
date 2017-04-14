package by.belstu.lab_89;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class LoginActivity extends Activity {
    DBController controller = new DBController(this, "Lab_891011DB.db", null, 1);
    static String name, surname, username, password, age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MD5 hash = new MD5();
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);

        final Button bLogin = (Button)findViewById(R.id.bLogin);

        final TextView registerLink = (TextView)findViewById(R.id.tvRegisterHere);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(etUsername.getText().toString().equals("") || etPassword.getText().toString().equals(""))
                    {
                        Toast.makeText(LoginActivity.this, "Login or password field is empty!", Toast.LENGTH_LONG).show();
                        return;

                    }
                    Cursor cursor = controller.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS WHERE USERNAME='"
                            +etUsername.getText().toString()+"'", null);
                    int count = cursor.getCount();
                    if (cursor.moveToFirst()) {
                        Integer ID = cursor.getInt(0);
                        String name_DB = cursor.getString(1);
                        String surname_DB = cursor.getString(2);
                        String username_DB = cursor.getString(3);
                        String password_DB = cursor.getString(4);
                        String age_DB = cursor.getString(5);

                        name = name_DB;
                        surname = surname_DB;
                        username = username_DB;
                        password = password_DB;
                        age = age_DB;


                    }
                    if(count!=0 && etUsername.getText().toString().equals(username)
                            && etUsername.getText().toString().equals("admin")==false) {
                        if (etPassword.getText().toString().equals(password)) {
                            Intent user = new Intent(LoginActivity.this, UserAreaActivity.class);
                            user.putExtra("name", name);
                            user.putExtra("surname", surname);
                            user.putExtra("age", age);
                            startActivity(user);
                            finish();
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(etUsername.getText().toString().equals("admin")) {
                            if (etPassword.getText().toString().equals("admin") == true) {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                LoginActivity.this.closeContextMenu();
                                startActivity(intent);
                            }
                            else
                            {Toast.makeText(LoginActivity.this, "Password is wrong!", Toast.LENGTH_SHORT).show();}
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "No such user!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e)
                {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.closeContextMenu();
                startActivity(registerIntent);
            }
        });
    }
}
