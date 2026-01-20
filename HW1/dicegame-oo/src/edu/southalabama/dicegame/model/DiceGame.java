package edu.southalabama.dicegame.model;

import java.util.ArrayList;
import java.util.List;


public class DiceGame {
    private Die die1;
    private Die die2;

    public DiceGame() {
        this.die1 = new Die(6);
        this.die2 = new Die(6);
    }

    public void play(){
        this.die1.roll();
        this.die2.roll();
    }

    public List<Integer> getDieValues(){
        return List.of(die1.getFaceValue(), die2.getFaceValue());
    }

    public boolean getOutcome(){
        int sum = this.getResult();
        return sum % 7 == 0 || sum % 12 == 0;
    }

    public int getResult(){
        return this.die1.getFaceValue() + this.die2.getFaceValue();
    }
}
