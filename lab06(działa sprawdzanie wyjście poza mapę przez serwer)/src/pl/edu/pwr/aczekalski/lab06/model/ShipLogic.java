package pl.edu.pwr.aczekalski.lab06.model;

import java.util.ArrayList;
import java.util.Random;

public class ShipLogic {
    ArrayList<ArrayList<Field>> fieldLabelsArray; //arraylista pola przekazana z głównej metody
    ArrayList<Ship> ships;

    public ShipLogic(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships){
        this.fieldLabelsArray =fieldLabelsArray;
        this.ships=ships;
    }

    public String randomDirection(){
        String direction;
        ArrayList<String> possibleDirections = new ArrayList<>();
        possibleDirections.add("N");
        possibleDirections.add("NE");
        possibleDirections.add("E");
        possibleDirections.add("SE");
        possibleDirections.add("S");
        possibleDirections.add("SW");
        possibleDirections.add("W");
        possibleDirections.add("NW");
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(possibleDirections.size());
        direction=possibleDirections.get(index);

        return direction;
    }

    //to statek powinien pytać się świata czy może się ruszyć
    synchronized void move(Ship ship){
        if(ship.direction.equals("N") && canMoveY(ship.shipPositionY-1)){
            if(fieldLabelsArray.get(ship.shipPositionY - 1).get(ship.shipPositionX).set()) {
                fieldLabelsArray.get(ship.shipPositionY - 1).get(ship.shipPositionX).unSet();
                ship.shipPositionY--;
                fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
                fieldLabelsArray.get(ship.shipPositionY + 1).get(ship.shipPositionX).label.setText(".");
                ship.direction = randomDirection();
                ship.out.println("Ruch udany.");
            }
            else{
                ship.out.println("Kolizja!");
            }
        }
        else if(ship.direction.equals("NE") && canMoveY(ship.shipPositionY-1) && canMoveX(ship.shipPositionX+1)){
            ship.shipPositionX++;
            ship.shipPositionY--;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY+1).get(ship.shipPositionX-1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("E") && canMoveX(ship.shipPositionX+1)){
            ship.shipPositionX++;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX-1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("SE") && canMoveY(ship.shipPositionY+1) && canMoveX(ship.shipPositionX+1)){
            ship.shipPositionX++;
            ship.shipPositionY++;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY-1).get(ship.shipPositionX-1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("S") && canMoveY(ship.shipPositionY+1)){
            ship.shipPositionY++;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY-1).get(ship.shipPositionX).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("SW") && canMoveY(ship.shipPositionY+1) && canMoveX(ship.shipPositionX-1)){
            ship.shipPositionX--;
            ship.shipPositionY++;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY-1).get(ship.shipPositionX+1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("W") && canMoveX(ship.shipPositionX-1)){
            ship.shipPositionX--;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX+1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else if(ship.direction.equals("NW") && canMoveY(ship.shipPositionY-1) && canMoveX(ship.shipPositionX-1)){
            ship.shipPositionX--;
            ship.shipPositionY--;
            fieldLabelsArray.get(ship.shipPositionY).get(ship.shipPositionX).label.setText("S");
            fieldLabelsArray.get(ship.shipPositionY+1).get(ship.shipPositionX+1).label.setText(".");
            ship.direction=randomDirection();
            ship.out.println("Ruch udany.");
        }
        else{
            ship.out.println("Ruch zabroniony!");
            ship.direction=randomDirection();
        }
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
