package pl.edu.pwr.aczekalski.lab06.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Wave {
    ArrayList<ArrayList<Field>> fieldLabelsArray;

    public Wave(ArrayList<ArrayList<Field>> fieldLabelsArray, Ship ship) {
        this.fieldLabelsArray = fieldLabelsArray;
    }

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

    public void makeWave(String direction, int shipPositionX, int shipPositionY) { //zrób falę (kwadrat 5x5 wokół statku)
        int x=-2,y=-2;
        for (ArrayList<Integer> rowWave : waveValues) {
            for(Integer field : rowWave) {
                try {
                    fieldLabelsArray.get(shipPositionY + y).get(shipPositionX + x).label.setText(Integer.toString(field));
                } catch(IndexOutOfBoundsException e){

                }
                x++;
            }
            y++;
            x=-2;
        }
        try {
            if (direction.equals("N")) {
                int j = 3;
                for (int i = -2; i <= 2; i++) {
                    fieldLabelsArray.get(shipPositionY + j).get(shipPositionX + i).label.setText(".");
                }
            }
            if (direction.equals("NE")) {
                int j = -3;
                int i;
                for (i = -1; i <= 3; i++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }

                for (j = -3; j <= 1; j++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }

            }
            if (direction.equals("E")) {
                int j = -3;
                for (int i = -2; i <= 2; i++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }
            }
            if (direction.equals("SE")) {
                int j = -3;
                int i;
                for (i = 1; i >= -3; i--) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }

                for (j = -3; j <= 1; j++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }
            }
            if (direction.equals("S")) {
                int j = -3;
                for (int i = -2; i <= 2; i++) {
                    fieldLabelsArray.get(shipPositionY + j).get(shipPositionX + i).label.setText(".");
                }
            }
            if (direction.equals("SW")) {
                int j = 3;
                int i;
                for (i = 1; i >= -3; i--) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }

                for (j = 3; j >= -1; j--) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }
            }
            if (direction.equals("W")) {
                int j = 3;
                for (int i = -2; i <= 2; i++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }
            }
            if (direction.equals("NW")) {
                int j = 3;
                for (int i = -1; i <= 3; i++) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }

                int i = -3;
                for (j = 3; j >= -1; j--) {
                    fieldLabelsArray.get(shipPositionY + i).get(shipPositionX + j).label.setText(".");
                }
            }
        }catch (IndexOutOfBoundsException e){

        }
    }
}
