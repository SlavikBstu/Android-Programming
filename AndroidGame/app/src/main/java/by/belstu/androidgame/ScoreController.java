package by.belstu.androidgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Владислав on 29.11.2016.
 */
public class ScoreController {
    Integer[][] matrix;
    Counter counter;
    Context context;
    ImageView imgView;
    MediaPlayer mPlayer;

    public ScoreController(Context context) {
        matrix = new Integer[3][3];

        this.context = context;

        matrix[0][0] = 0;
        matrix[0][1] = -1;
        matrix[0][2] = 1;
        matrix[1][0] = 1;
        matrix[1][1] = 0;
        matrix[1][2] = -1;
        matrix[2][0] = -1;
        matrix[2][1] = 1;
        matrix[2][2] = 0;

        counter = new Counter();

    }

    private int encode(String str){
        if(str.compareTo("rock") == 0) return 0;
        if(str.compareTo("scissors") == 0) return 1;
        if(str.compareTo("paper") == 0) return 2;
        return -1;
    }

    private String decode(Integer i){
        mPlayer = new MediaPlayer();
        imgView = new ImageView(context);
        String result = "";
        if(i == 1) {
            counter.IncrementWins();
            imgView.setImageResource(R.drawable.happy);
            result = "You win! (wtf ваще?)";
            mPlayer = MediaPlayer.create(context, R.raw.win);
            mPlayer.start();
            showToast(result);
        }
        if(i == -1) {
            counter.IncrementLoses();
            imgView.setImageResource(R.drawable.sad);
            mPlayer = MediaPlayer.create(context, R.raw.lose);
            mPlayer.start();
            result = "You lose! (wtf ваще?)";
            showToast(result);
        }
        if(i == 0) {
            counter.IncrementDraws();
            imgView.setImageResource(R.drawable.nichya);
            mPlayer = MediaPlayer.create(context, R.raw.draw);
            mPlayer.start();
            result = "Draw (wtf ваще?)";
            showToast(result);
        }
        return result;

    }

    public String GameLogic(String str1, String str2){
        return decode(matrix[encode(str2)][encode(str1)]);
    }

    public String getWins(){
        return counter.getWins();
    }

    public String getLoses(){
        return counter.getLoses();
    }

    public String getDraws() {
        return counter.getDraws();
    }

    public void ResetAll(){
        counter.Reset();
    }

    public void showToast(String result){
        try {
            Toast toast = Toast.makeText(context,
                    result, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastContainer = (LinearLayout) toast.getView();
            toastContainer.addView(imgView, 0);
            toast.show();
        }catch (Exception e)
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
