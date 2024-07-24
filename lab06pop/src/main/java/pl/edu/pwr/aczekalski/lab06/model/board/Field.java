package pl.edu.pwr.aczekalski.lab06.model.board;

import javax.swing.*;
import java.awt.*;

public class Field {
    private int x;
    private int y;
    private String sign;
    private JLabel label;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSign() {
        return sign;
    }

    public JLabel getLabel() {
        return label;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Field(int x, int y, boolean isOccupied, String sign) {
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
        this.sign = sign;
        this.label = new JLabel(sign);
    }

    private boolean isOccupied; //if field is occupied by another player

    public void setBuoys() { //show buoys on the board when they connect
        label.setForeground(Color.RED);
        label.setText("0");
    }

    public synchronized boolean set() { //set the ship on the board
        if (isOccupied) return false;

        label.setForeground(Color.GREEN);
        label.setText("S");
        isOccupied = true;
        return true;
    }

    public synchronized void unSet() { //free up the place on the board
        if (!isOccupied) return;

        label.setForeground(Color.BLACK);
        label.setText(sign);
        isOccupied = false;
    }
}
