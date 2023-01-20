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

    //to statek powinien pytać się świata czy może się ruszyć
    public void move(Ship ship, JPanel simPanel) throws IOException {
        if(canMoveX(ship.shipPositionX+ship.direction.x) && canMoveY(ship.shipPositionY+ship.direction.y)){
            if(fieldLabelsArray.get(ship.shipPositionY +ship.direction.y).get(ship.shipPositionX+ship.direction.x).set()) {
                fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).unSet();
                ship.shipPositionY+=ship.direction.y;
                ship.shipPositionX+=ship.direction.x;
                ship.direction = Direction.randomDirection();
//                ship.out.println("WAVE");
                ship.out.println("Moved successfully.");
            }
            else if(ship.direction.x!=0 || ship.direction.y!=0){
                fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).unSet();
                ship.out.println("Collision!");
                ship.interrupt();
            }
            else{
                ship.direction=Direction.randomDirection();
            }
        }

        else{
            ship.out.println("Move is prohibited!");
            ship.direction=Direction.randomDirection();
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
