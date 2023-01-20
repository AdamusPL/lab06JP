package pl.edu.pwr.aczekalski.lab06.model.board;

import javax.swing.*;
import java.awt.*;

public class Field {
    public int x;
    public int y;
    public String sign;
    public JLabel label;

    public Field(int x, int y, boolean isOccupied, String sign){
        this.x=x;
        this.y=y;
        this.isOccupied=isOccupied;
        this.sign=sign;
        this.label=new JLabel(sign);
    }

    private boolean isOccupied = false; //czy pole jest zajmowane przez jakiegoś gracza

    public synchronized void initShip(){
        label.setForeground(Color.GREEN);
        label.setText("S");
    }

    public void setBuoys(){
        label.setForeground(Color.RED);
        label.setText("B");
    }

    public synchronized boolean set(){
        if(isOccupied) return false;

        label.setForeground(Color.GREEN);
        label.setText("S");
        isOccupied = true;
        return true;
    }

    public synchronized void unSet(){
        if(!isOccupied) return;

        label.setForeground(Color.BLACK);
        label.setText(sign);
        isOccupied = false;
    }
}
