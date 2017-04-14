package by.belstu.lab_05db;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText editiID, editF, editT;
    DB_Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Database info");

        editiID = (EditText)findViewById(R.id.editID);
        editF = (EditText)findViewById(R.id.editF);
        editT = (EditText)findViewById(R.id.editT);

        controller = new DB_Controller(this, "", null, 1);

    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().length() > 0)
            return false;

        return true;
    }

    public void btn_click(View view){
        switch (view.getId()){
            case R.id.insertbutton:
                try{
                    long id = editiID.getText().toString().equals("") ? -1 : Integer.valueOf(editiID.getText().toString()) ;
                    id = controller.insert_info((int)id, Float.valueOf(editF.getText().toString()), editT.getText().toString());
                    Toast.makeText(MainActivity.this, "Inserting successful", Toast.LENGTH_SHORT).show();
                    editiID.setText(String.valueOf(id));
                }catch (SQLiteException e){
                    Toast.makeText(MainActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deletebutton:
                if(isEmpty(editiID)) {
                    Toast.makeText(MainActivity.this, "Edit ID field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    try {
                        controller.delete_info(Integer.valueOf(editiID.getText().toString()));
                        Toast.makeText(MainActivity.this, "Deleting is successful!", Toast.LENGTH_SHORT).show();
                        editiID.setText("");
                        editF.setText("");
                        editT.setText("");
                    }catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Not found ID", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.updatebutton:
                if(isEmpty(editiID)) {
                    Toast.makeText(MainActivity.this, "Edit ID field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Enter new info");

                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final EditText new_f = new EditText(MainActivity.this);
                    new_f.setHint("F: ");
                    new_f.setInputType(0x00002002);
                    layout.addView(new_f);

                    final EditText new_t = new EditText(MainActivity.this);
                    new_t.setHint("T: ");
                    layout.addView(new_t);
                    dialog.setView(layout);

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                controller.update_info(Float.valueOf(editF.getText().toString()),
                                        Float.valueOf(new_f.getText().toString()),
                                        editT.getText().toString(),
                                        new_t.getText().toString());

                                Toast.makeText(MainActivity.this, "Updating is succsesful!", Toast.LENGTH_SHORT).show();
                                editF.setText(new_f.getText());
                                editT.setText(new_t.getText());
                            } catch (SQLiteException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                }

                break;
            case R.id.selectrawbutton:
                if(isEmpty(editiID)) {
                    Toast.makeText(MainActivity.this, "Edit ID field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    try {
                        controller.select_raw_info(editiID, editF, editT);
                    }catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Not found ID", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.selectbutton:
                if(isEmpty(editiID)) {
                    Toast.makeText(MainActivity.this, "Edit ID field!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                   try {
                       controller.select_info(editiID, editF, editT);
                   }catch (Exception e)
                   {
                       Toast.makeText(MainActivity.this, "Not found ID", Toast.LENGTH_SHORT).show();
                   }
                break;
            case R.id.selectallbutton:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

        }
    }

}
