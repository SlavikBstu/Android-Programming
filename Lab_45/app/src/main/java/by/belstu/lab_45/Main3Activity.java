package by.belstu.lab_45;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Main3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void next3Click(View view){
        EditText age = (EditText)findViewById(R.id.editAge);
        Intent intent3 = new Intent(Main3Activity.this, Main4Activity.class);
        String surname = getIntent().getExtras().getString("surname");
        intent3.putExtra("surname", surname);
        String name = getIntent().getExtras().getString("name");
        intent3.putExtra("name", name);
        intent3.putExtra("age", age.getText().toString());
        startActivity(intent3);
    }

    public void back2Click(View view){
        Main3Activity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);
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
