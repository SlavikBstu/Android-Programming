package by.belstu.lab_89;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends Activity {

    DBController controller = new DBController(this, "Lab_891011DB.db", null, 1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        final ListView studentList = (ListView)findViewById(R.id.studentslistView);

        List<String> names = new ArrayList<String>();

        final Cursor cursor = controller.getReadableDatabase().rawQuery("SELECT USERNAME FROM STUDENTS ORDER BY USERNAME;", null);
        while (cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndex("USERNAME"));
            names.add(uname);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        studentList.setAdapter(adapter);

        studentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AdminActivity.this.onStop();
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminActivity.this);
                dialog.setTitle("Delete?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            controller.delete_info(adapter.getItem(position));
                            adapter.remove(adapter.getItem(position));
                            Toast.makeText(AdminActivity.this, "Deleted!", Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
                return false;
            }
        });


        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                try {
                    Intent intent = new Intent(AdminActivity.this, InfoDetailActivity.class);
                    intent.putExtra("username", adapter.getItem(position));
                    AdminActivity.this.closeContextMenu();
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void backButton(View view){
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        AdminActivity.this.finish();
        startActivity(intent);
    }
}
