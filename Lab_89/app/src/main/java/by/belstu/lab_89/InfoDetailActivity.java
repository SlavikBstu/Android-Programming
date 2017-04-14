package by.belstu.lab_89;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDetailActivity extends Activity {
    DBController controller = new DBController(this, "Lab_891011DB.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        try {
            TextView infoText = (TextView) findViewById(R.id.infoTextView);
            String username = getIntent().getExtras().getString("username");

            final Cursor cursor = controller.getReadableDatabase().rawQuery("SELECT * FROM STUDENTS WHERE USERNAME = '" + username + "'", null);
            while (cursor.moveToNext()) {
                infoText.append("\n Name: " + cursor.getString(1) + "\n Surname: " + cursor.getString(2) + "\n Age: " + cursor.getString(5));
            }
        } catch (Exception e) {
            Toast.makeText(InfoDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    public void back(View v) {
        Intent intent = new Intent(InfoDetailActivity.this, AdminActivity.class);
        InfoDetailActivity.this.finish();
        startActivity(intent);
    }
}
