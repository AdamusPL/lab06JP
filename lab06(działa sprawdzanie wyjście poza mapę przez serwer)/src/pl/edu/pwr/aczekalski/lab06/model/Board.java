package pl.edu.pwr.aczekalski.lab06.model;

import java.util.ArrayList;

public class Board { //plansza

    public ArrayList<ArrayList<Field>> fieldLabelsArray; //arraylista pola gry
    public ArrayList<Field> makeRow(int x, int y) {
        ArrayList<Field> rowField = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            x += 15;
            boolean isOccupied=false;
            Field field = new Field(x,y,isOccupied);
            rowField.add(field);
            if((i+1)%5==0){
                x+=10;
            }
        }
        return rowField;
    }

    public void makeBoard(){
        fieldLabelsArray = new ArrayList<>();
        int y = 5;
        for (int i = 0; i < 40; i++) {
            int x = 5;
            ArrayList<Field> rowLabel = makeRow(x, y);
            fieldLabelsArray.add(rowLabel);
            if ((i + 1) % 5 == 0) {
                y += 10;
            }
            y += 10;
        }
    }

}
