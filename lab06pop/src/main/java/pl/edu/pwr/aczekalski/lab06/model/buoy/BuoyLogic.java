package pl.edu.pwr.aczekalski.lab06.model.buoy;

import pl.edu.pwr.aczekalski.lab06.model.board.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class BuoyLogic {
    private ArrayList<ArrayList<Integer>> waveValues;
    public BuoyLogic(){
        waveValues = fillWave();
    }

    private ArrayList<ArrayList<Integer>> fillWave() { //fill the arraylist with wave values
        ArrayList<ArrayList<Integer>> waveValues = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(0, 1, 2, 1, 0));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 1));
        ArrayList<Integer> row3 = new ArrayList<>(Arrays.asList(2, 3, 4, 3, 2));
        waveValues.add(row1);
        waveValues.add(row2);
        waveValues.add(row3);
        waveValues.add(row2);
        waveValues.add(row1);
        return waveValues;
    }

    protected void resetWave(int shipPositionX, int shipPositionY, Board board) {
        int x = -2, y = -2;
        for (ArrayList<Integer> rowWave : waveValues) {
            for (Integer field : rowWave) {
                try {
                    board.getFieldLabelsArray().get(shipPositionY + y).get(shipPositionX + x).getLabel().setText("0");

                } catch (IndexOutOfBoundsException e) {

                }
                x++;
            }
            y++;
            x = -2;
        }
    }

    protected void makeWave(int buoyPositionX, int buoyPositionY, int shipPositionX, int shipPositionY, Board board) { //make wave (square 5x5 around the ship)
        int x = -2, y = -2;
        int numberPosX = shipPositionX + x;
        int numberPosY = shipPositionY + y;
        for (ArrayList<Integer> rowWave : waveValues) {
            for (Integer field : rowWave) {
                try {
                    if (Math.abs(buoyPositionX - numberPosX) <= 2 && Math.abs(buoyPositionY - numberPosY) <= 2) {
                        board.getFieldLabelsArray().get(shipPositionY + y).get(shipPositionX + x).getLabel().setText(Integer.toString(field));
                    }

                } catch (IndexOutOfBoundsException e) {

                }
                x++;
                numberPosX++;
            }
            y++;
            numberPosY++;
            x = -2;
            numberPosX = shipPositionX - 2;
        }
    }
}
