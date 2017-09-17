package com.example.astrand.hangman.Gamemodel;

import java.util.concurrent.TimeUnit;


public class HangmanTimer {

    private long sum = 0L;
    private long start = 0L;
    private long end = 0L;
    private boolean isCounting = false;

    public HangmanTimer(){

    }

    public void start(){
        isCounting = true;
        start = System.currentTimeMillis();
    }

    public void pause(){
        end = System.currentTimeMillis();
        sum += end - start;
        end = 0L; start = 0L;
        isCounting = false;
    }

    public void stop(){
        end = System.currentTimeMillis();
        sum += end - start;
        isCounting = false;
    }

    public boolean isCounting(){
        return isCounting;
    }

    public boolean hasStarted(){
        return sum != 0;
    }

    public long getTime(){
        return sum;
    }

    public Long getTimeInSeconds(){
        if (isCounting) return (long)0;
        return TimeUnit.MILLISECONDS.toSeconds(sum);
    }
}
