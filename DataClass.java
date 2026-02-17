package com.example;

/**
 * "Class X" container that stores a word and a number
 */
public class DataClass{
    private final String word;
    private int number;
    public DataClass(String word, int number)
    {
        this.word = word;
        this.number = number;
    }
    public String getWord(){
        return this.word;
    }
    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }
    @Override
    public String toString() {
        return "Word: " + word + " Number: " + number + "\n";
    }
}
