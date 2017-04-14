package by.belstu.androidgame;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import java.util.Random;

public class MainActivity extends Activity {

    Button b_rock, b_paper, b_scissors;
    ImageView iv_cpu, iv_me;
    ScoreController sController;
    String myChoise, cpuChoise, result;
    Random r;
    ColorPickerDialog colord;
    Integer color;
    MediaPlayer mPlayer;
    RelativeLayout layout;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);

        sController = new ScoreController(this.getApplicationContext());

        iv_cpu = (ImageView)findViewById(R.id.iv_cpu);
        iv_me = (ImageView)findViewById(R.id.iv_me);

        b_rock = (Button)findViewById(R.id.b_rock);
        b_paper = (Button)findViewById(R.id.b_paper);
        b_scissors = (Button)findViewById(R.id.b_scissors);
        layout = (RelativeLayout)findViewById(R.id.layout);

        r = new Random();

        b_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myChoise = "rock";
                iv_me.setImageResource(R.drawable.rock);
                calculate();
            }
        });

        b_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myChoise = "paper";
                iv_me.setImageResource(R.drawable.paper);
                calculate();
            }
        });

        b_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myChoise = "scissors";
                iv_me.setImageResource(R.drawable.scissors);
                calculate();
            }
        });
    }

    public void calculate() {
        int cpu = r.nextInt(3);
        if(cpu == 0){
            cpuChoise = "rock";
            iv_cpu.setImageResource(R.drawable.rock);
        } else if(cpu == 1){
            cpuChoise = "paper";
            iv_cpu.setImageResource(R.drawable.paper);
        } else if(cpu == 2){
            cpuChoise = "scissors";
            iv_cpu.setImageResource(R.drawable.scissors);
        }
        result = sController.GameLogic(myChoise, cpuChoise);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       switch (id){
           case R.id.action_countwins:
           try{
               Toast.makeText(MainActivity.this, "You win " + sController.getWins() + " раз.", Toast.LENGTH_SHORT).show();
           }catch (Exception e){
               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           } break;
           case R.id.action_countloses:
           try{
               Toast.makeText(MainActivity.this, "You loser " + sController.getLoses() + " раз.", Toast.LENGTH_SHORT).show();
           }catch (Exception e){
               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }break;
           case R.id.action_countdraws:
           try{
               Toast.makeText(MainActivity.this, "You draw " + sController.getDraws() + " раз.", Toast.LENGTH_SHORT).show();
           }catch (Exception e){
               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }break;
           case R.id.action_reset:
               try{
                   sController.ResetAll();
                   Toast.makeText(MainActivity.this, "Your achievements are reset.", Toast.LENGTH_SHORT).show();
               }catch (Exception e){
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }break;
           case R.id.action_choose_color:
               try{
                   color = Color.parseColor("#005500");
                   colord = new ColorPickerDialog(MainActivity.this, color);
                   colord.setAlphaSliderVisible(true);

                   colord.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                       @Override
                       public void onColorChanged(int i) {
                           color = i;
                           layout.setBackgroundColor(color);
                       }
                   });
                   colord.show();
               }catch (Exception e){
               Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }break;
           case R.id.action_on_off_music:
               try{
                   if(flag == false){
                       mPlayer = MediaPlayer.create(MainActivity.this, R.raw.lezginka);
                       mPlayer.start();
                       flag = true;
                       return true;
                   }
                   if(flag == true)
                   {
                       mPlayer.stop();
                       flag = false;
                       return false;
                   }
               }catch (Exception e){
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }break;
       }
        return super.onOptionsItemSelected(item);
    }
}
