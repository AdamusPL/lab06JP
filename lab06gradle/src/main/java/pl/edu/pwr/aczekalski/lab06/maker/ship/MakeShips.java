package pl.edu.pwr.aczekalski.lab06.maker.ship;

import pl.edu.pwr.aczekalski.lab06.model.*;
import pl.edu.pwr.aczekalski.lab06.model.board.Field;
import pl.edu.pwr.aczekalski.lab06.model.ship.Direction;
import pl.edu.pwr.aczekalski.lab06.model.ship.Ship;
import pl.edu.pwr.aczekalski.lab06.model.ship.ShipLogic;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    JPanel simPanel;
    BufferedReader in;
    PrintWriter out;

    public MakeShips(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships, ArrayList<Integer> pickedX,
                     ArrayList<Integer> pickedY, Socket clientSocket, int id, JPanel simPanel, BufferedReader in, PrintWriter out){
        this.fieldLabelsArray=fieldLabelsArray;
        this.ships=ships;
        this.clientSocket=clientSocket;
        this.pickedX=pickedX;
        this.pickedY=pickedY;
        this.id=id;
        this.simPanel=simPanel;
        this.in=in;
        this.out=out;
    }

    public ArrayList<Ship> addShip() throws IOException {
        Random randomX=new Random();
        Random randomY=new Random();

        int randomPositionX = randomX.nextInt(40);
        int randomPositionY = randomY.nextInt(40);
//
//        int randomPositionX = randomX.nextInt(3); //tymczasowo!!!!
//        int randomPositionY = randomY.nextInt(3);

        while(pickedX.contains(randomPositionX)){
            randomPositionX = randomX.nextInt(40);
//            randomPositionX = randomX.nextInt(3); //tymczasowo!!!!
        }

        while(pickedY.contains(randomPositionY)){
            randomPositionY = randomY.nextInt(40);
//            randomPositionY = randomY.nextInt(3); //tymczasowo!!!!
        }

        pickedX.add(randomPositionX);
        pickedY.add(randomPositionY);

        ShipLogic shipLogic = new ShipLogic(fieldLabelsArray,ships); //do testowania
        wave = new Wave(fieldLabelsArray,ship);

        ship = new Ship(clientSocket,id, Direction.randomDirection(), randomPositionX, randomPositionY, shipLogic, wave, simPanel, in, out);
        ship.out.println("START");

        ship.start();
        ships.add(ship);
        return ships;
    }
}
