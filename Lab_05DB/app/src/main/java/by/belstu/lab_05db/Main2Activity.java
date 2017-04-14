package by.belstu.lab_05db;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("Info of all students");
        TextView txt = (TextView)findViewById(R.id.textView);
        DB_Controller controller = new DB_Controller(this, "", null, 1);
        controller.list_all_students(txt);

    }
}
