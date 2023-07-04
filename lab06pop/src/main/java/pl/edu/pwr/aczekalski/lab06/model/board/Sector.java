package pl.edu.pwr.aczekalski.lab06.model.board;

import java.util.ArrayList;

public class Sector {

    public ArrayList<ArrayList<Field>> sector;

    public void makeSector(int x, int y) {
        ArrayList<Field> row = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                x += 15;
                boolean isOccupied = false;
                Field field = new Field(x, y, isOccupied, "0");
                row.add(field);
            }
            y+=15;
            sector.add(row);
        }
    }

}
