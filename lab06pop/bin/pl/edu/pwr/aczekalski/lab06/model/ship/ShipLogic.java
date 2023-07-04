package pl.edu.pwr.aczekalski.lab06.model.ship;


import pl.edu.pwr.aczekalski.lab06.model.board.Field;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ShipLogic {
    ArrayList<ArrayList<Field>> fieldLabelsArray; //arraylista pola przekazana z głównej metody
    ArrayList<Ship> ships;

    public ShipLogic(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships){
        this.fieldLabelsArray =fieldLabelsArray;
        this.ships=ships;
    }

    public void move(Ship ship, JPanel simPanel, int directionX, int directionY) throws IOException {
        if(canMoveX(ship.shipPositionX+directionX) && canMoveY(ship.shipPositionY+directionY)){
            if(fieldLabelsArray.get(ship.shipPositionY +directionY).get(ship.shipPositionX+directionX).set()) {
                fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).unSet();
                ship.shipPositionY+=directionY;
                ship.shipPositionX+=directionX;
                ship.out.println("Moved successfully.");
            }

            else if(directionX!=0 || directionY!=0){
                fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).unSet();
                ship.out.println("Collision!");
                ship.interrupt();
            }
        }

        else{
            ship.out.println("Move is prohibited!");
        }

        simPanel.repaint();
        simPanel.revalidate();

    }

    public void initShip(Ship ship){
        fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).initShip();
    }

    public void unsetFirst(Ship ship){
        fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).unSet();
    }

    public boolean canMoveX(int posX){ //metoda sprawdzająca czy gracz nie wyszedł poza pole
        return !(posX < 0 || posX >= fieldLabelsArray.size());
    }

    public boolean canMoveY(int posY){ //metoda sprawdzająca czy gracz nie wyszedł poza pole
        return !(posY < 0 || posY >= fieldLabelsArray.size());
    }

    synchronized void scan(Ship ship){
        for (Ship s: ships) {
            if(s!=ship) {
                ship.out.println("Pozycja statku nr: " + s.id + " X:" + s.shipPositionX + " Y:" + s.shipPositionY);
            }
        }
    }
}
