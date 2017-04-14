package by.belstu.lab_89;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserAreaActivity extends Activity {
    DBController controller = new DBController(this, "Lab_891011DB.db", null, 1);

    static String name, surname, age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etAge = (EditText) findViewById(R.id.etAge);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        try {
            name = getIntent().getExtras().getString("name");
            etName.setText(name);

            surname = getIntent().getExtras().getString("surname");
            etSurname.setText(surname);

            age = getIntent().getExtras().getString("age");
            etAge.setText(age);

            tvWelcome.setText("You are " + name + " " + surname + ". Welcome!");
        } catch (Exception e) {
            Toast.makeText(UserAreaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_area, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_exit:
                try {
                    Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
                    UserAreaActivity.this.finish();
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(UserAreaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.action_change:
                try {
                    final EditText etName = (EditText) findViewById(R.id.etName);
                    final EditText etSurname = (EditText) findViewById(R.id.etSurname);
                    final EditText etAge = (EditText) findViewById(R.id.etAge);

                    controller.getWritableDatabase().execSQL("UPDATE STUDENTS SET NAME='" + etName.getText().toString() + "'");
                    controller.getWritableDatabase().execSQL("UPDATE STUDENTS SET SURNAME='" + etSurname.getText().toString() + "'");
                    controller.getWritableDatabase().execSQL("UPDATE STUDENTS SET Age='" + etAge.getText().toString() + "'");
                    Toast.makeText(UserAreaActivity.this, "Your info is changed!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(UserAreaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.action_copy:
                try {
                    TextView buff = (TextView) findViewById(R.id.tvWelcome);
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) UserAreaActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", buff.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(UserAreaActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                    return true;
                } catch (Exception e) {
                    Toast.makeText(UserAreaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_send:
                try {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Sending SMS");
                    alert.setMessage("Put number :");
                    final EditText message = new EditText(this);
                    final EditText number = new EditText(this);
                    alert.setView(number);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String toSms = "smsto:" + number.getText().toString();
                            String name = getIntent().getExtras().getString("name");
                            String surname = getIntent().getExtras().getString("surname");
                            String messageText = "Hey man " + " " + name + " " + surname;
                            Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));
                            sms.putExtra("sms_body", messageText);
                            startActivity(sms);
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(UserAreaActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.show();
                } catch (Exception e) {
                    Toast.makeText(UserAreaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
