package pl.edu.pwr.aczekalski.lab06.model.buoy;

import pl.edu.pwr.aczekalski.lab06.model.board.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class BuoyLogic {
    public ArrayList<ArrayList<Integer>> fillWave(){ //wypełnienie arraylisty wartościami fali
        ArrayList<ArrayList<Integer>> waveValues = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(0,1,2,1,0));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(1,2,3,2,1));
        ArrayList<Integer> row3 = new ArrayList<>(Arrays.asList(2,3,4,3,2));
        ArrayList<Integer> row4 = row2;
        ArrayList<Integer> row5 = row1;
        waveValues.add(row1);
        waveValues.add(row2);
        waveValues.add(row3);
        waveValues.add(row4);
        waveValues.add(row5);
        return waveValues;
    }

    ArrayList<ArrayList<Integer>> waveValues = fillWave();

    public void resetWave(int shipPositionX, int shipPositionY, Board board){
        int x=-2,y=-2;
        for (ArrayList<Integer> rowWave : waveValues) {
            for(Integer field : rowWave) {
                try {
                    board.fieldLabelsArray.get(shipPositionY + y).get(shipPositionX + x).label.setText("0");

                } catch(IndexOutOfBoundsException e){

                }
                x++;
            }
            y++;
            x=-2;
        }
    }

    public void makeWave(int buoyPositionX, int buoyPositionY, int shipPositionX, int shipPositionY, Board board) { //zrób falę (kwadrat 5x5 wokół statku)
        int x=-2,y=-2;
        int numberPosX=shipPositionX+x;
        int numberPosY=shipPositionY+y;
        for (ArrayList<Integer> rowWave : waveValues) {
            for(Integer field : rowWave) {
                try {
//                        String currentSL = board.fieldLabelsArray.get(shipPositionY + y).get(shipPositionX + x).label.getText(); //próba interferencji
//                        int intCurrentSL = Integer.parseInt(currentSL);
//                        intCurrentSL += field;
//                        currentSL = Integer.toString(intCurrentSL);
//                        board.fieldLabelsArray.get(shipPositionY + y).get(shipPositionX + x).label.setText(currentSL);
                    if(Math.abs(buoyPositionX-numberPosX)<=2 && Math.abs(buoyPositionY-numberPosY)<=2) {
                        board.fieldLabelsArray.get(shipPositionY + y).get(shipPositionX + x).label.setText(Integer.toString(field));
                    }

                } catch(IndexOutOfBoundsException e){

                }
                x++;
                numberPosX++;
            }
            y++;
            numberPosY++;
            x=-2;
            numberPosX=shipPositionX-2;
        }
    }
}
