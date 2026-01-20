package edu.southalabama.dicegame.model;

public class Die {

    private int faceValue;
    private int numSides;

    public Die(int numSides){
        this.numSides = numSides;
        this.faceValue = 0;
    }

    public int getFaceValue(){
        return faceValue;
    }

    public void roll(){
        this.faceValue = (int)(Math.ceil(Math.random() * (this.numSides)));
    }
}
