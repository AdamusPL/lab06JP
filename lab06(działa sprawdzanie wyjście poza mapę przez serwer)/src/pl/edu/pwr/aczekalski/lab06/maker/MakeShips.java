package pl.edu.pwr.aczekalski.lab06.maker;

import pl.edu.pwr.aczekalski.lab06.model.Field;
import pl.edu.pwr.aczekalski.lab06.model.Ship;
import pl.edu.pwr.aczekalski.lab06.model.ShipLogic;
import pl.edu.pwr.aczekalski.lab06.model.Wave;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MakeShips {
    int id;
    Ship ship;
    Wave wave;
    ArrayList<Integer> pickedX; //arraylista do której będą wkładane wszystkie wartości X poprzednio wylosowane, żeby się nie powtarzały
    ArrayList<Integer> pickedY; //arraylista do której będą wkładane wszystkie wartości Y poprzednio wylosowane, żeby się nie powtarzały
    ArrayList<Ship> ships;
    ArrayList<ArrayList<Field>> fieldLabelsArray;
    Socket clientSocket;

    public MakeShips(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships, ArrayList<Integer> pickedX, ArrayList<Integer> pickedY, Socket clientSocket, int id){
        this.fieldLabelsArray=fieldLabelsArray;
        this.ships=ships;
        this.clientSocket=clientSocket;
        this.pickedX=pickedX;
        this.pickedY=pickedY;
        this.id=id;
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

    public ArrayList<Ship> addShip() throws IOException {
        Random randomX=new Random();
        Random randomY=new Random();

//        int randomPositionX = randomX.nextInt(40);
//        int randomPositionY = randomY.nextInt(40);

        int randomPositionX = randomX.nextInt(3); //tymczasowo!!!!
        int randomPositionY = randomY.nextInt(3);

        while(pickedX.contains(randomPositionX)){
//            randomPositionX = randomX.nextInt(40);
            randomPositionX = randomX.nextInt(3); //tymczasowo!!!!
        }

        while(pickedY.contains(randomPositionY)){
//            randomPositionY = randomY.nextInt(40);
            randomPositionY = randomY.nextInt(3); //tymczasowo!!!!
        }

        pickedX.add(randomPositionX);
        pickedY.add(randomPositionY);

        ShipLogic shipLogic = new ShipLogic(fieldLabelsArray,ships); //do testowania
        wave = new Wave(fieldLabelsArray,ship);

        ship = new Ship(clientSocket,id,randomDirection(), randomPositionX, randomPositionY, shipLogic, wave);
        ship.out.println("START");

        ship.start();
        ships.add(ship);
        return ships;
    }
}
