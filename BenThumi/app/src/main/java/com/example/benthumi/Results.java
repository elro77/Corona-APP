package com.example.benthumi;

public class Results {
    private static Results INSTANCE = null;

    // other instance variables can be here
    private boolean sick;

    private Results() {};

    public static Results getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Results();
        }
        return(INSTANCE);
    }

    public void setStatus(boolean sick){ this.sick=sick;}
    public boolean getStatus(){ return sick;}

    // other instance methods can follow
}
