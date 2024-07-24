package pl.edu.pwr.aczekalski.lab06.model.ship;


import pl.edu.pwr.aczekalski.lab06.model.board.Field;

import java.io.IOException;
import java.util.ArrayList;

public class ShipLogic {
    private ArrayList<ArrayList<Field>> fieldLabelsArray; //field arraylist from main method
    private ArrayList<Ship> ships;

    public ShipLogic(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships) {
        this.fieldLabelsArray = fieldLabelsArray;
        this.ships = ships;
    }

    protected void move(Ship ship, int directionX, int directionY) throws IOException {
        if (canMoveX(ship.getShipPositionX() + directionX) && canMoveY(ship.getShipPositionY() + directionY)) {
            if (fieldLabelsArray.get(ship.getShipPositionY() + directionY).get(ship.getShipPositionX() + directionX).set()) {
                fieldLabelsArray.get(ship.getShipPositionY()).get(ship.getShipPositionX()).unSet();
                ship.setShipPositionY(ship.getShipPositionY() + directionY);
                ship.setShipPositionX(ship.getShipPositionX() + directionX);
                ship.getOut().println("Moved successfully.");
            } else if (directionX != 0 || directionY != 0) {
                fieldLabelsArray.get(ship.getShipPositionY()).get(ship.getShipPositionX()).unSet();
                ship.getOut().println("Collision!");
                ship.interrupt();
            }
        } else {
            ship.getOut().println("Move is prohibited!");
        }

    }

    private boolean canMoveX(int posX) { //metoda sprawdzająca czy gracz nie wyszedł poza pole
        return !(posX < 0 || posX >= fieldLabelsArray.size());
    }

    private boolean canMoveY(int posY) { //metoda sprawdzająca czy gracz nie wyszedł poza pole
        return !(posY < 0 || posY >= fieldLabelsArray.size());
    }

    protected synchronized void scan(Ship ship) {
        for (Ship s : ships) {
            if (s != ship) {
                ship.getOut().println("Ship position nr: " + s.id + " X:" + s.getShipPositionX() + " Y:" + s.getShipPositionY());
            }
        }
    }
}
