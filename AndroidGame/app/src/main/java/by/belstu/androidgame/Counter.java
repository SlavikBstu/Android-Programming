package by.belstu.androidgame;

/**
 * Created by Владислав on 28.11.2016.
 */
public class Counter {

    private Integer wins;
    private Integer loses;
    private Integer draws;

    public Counter(){
        wins = 0;
        loses = 0;
        draws = 0;
    }

    public String getWins(){
        return wins.toString();
    }

    public String getLoses(){
        return loses.toString();
    }

    public String getDraws(){
        return draws.toString();
    }

    public void Reset(){
        wins = 0;
        loses = 0;
        draws = 0;
    }

    public void IncrementWins(){
        wins++;
    }

    public void IncrementLoses(){
        loses++;
    }

    public void IncrementDraws(){
        draws++;
    }
}
