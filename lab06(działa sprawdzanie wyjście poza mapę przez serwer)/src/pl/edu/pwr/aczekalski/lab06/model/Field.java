package pl.edu.pwr.aczekalski.lab06.model;

import javax.swing.*;

public class Field { //pole "."
    public int x;
    public int y;
    public JLabel label = new JLabel(".");

    public Field(int x, int y, boolean isOccupied){
        this.x=x;
        this.y=y;
        this.isOccupied=isOccupied;
    }


    private boolean isOccupied = false; //czy pole jest zajmowane przez jakiegoś gracza

    public synchronized boolean set(){
        if(isOccupied) return false;

        isOccupied = true;
        return true;
    }

    public synchronized void unSet(){
        if(!isOccupied) return;

        isOccupied = false;
    }
}
