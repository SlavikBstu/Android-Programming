package by.belstu.lab_45;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends Activity {

    static ArrayList<Student> studentArrayList = new ArrayList<> ();
    static String surname, name, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TextView Surname = (TextView)findViewById(R.id.Surname);
        TextView Name = (TextView)findViewById(R.id.Name);
        TextView Age = (TextView)findViewById(R.id.Age);

        surname = getIntent().getExtras().getString("surname");
        Surname.setText(Surname.getText() + surname);

        name = getIntent().getExtras().getString("name");
        Name.setText(Name.getText() + name);

        age = getIntent().getExtras().getString("age");
        Age.setText(Age.getText() + age);

    }

    public void back3Click(View view){
        Main4Activity.this.finish();
    }

    public void saveClick(View view){
        Student student = new Student (surname, name, age);
        studentArrayList.add (student);
        Toast.makeText(Main4Activity.this,"Данные сохранены", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
