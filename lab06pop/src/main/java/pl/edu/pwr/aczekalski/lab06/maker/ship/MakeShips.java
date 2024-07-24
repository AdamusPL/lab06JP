package pl.edu.pwr.aczekalski.lab06.maker.ship;

import pl.edu.pwr.aczekalski.lab06.model.board.Field;
import pl.edu.pwr.aczekalski.lab06.model.buoy.BuoyHandler;
import pl.edu.pwr.aczekalski.lab06.model.ship.Ship;
import pl.edu.pwr.aczekalski.lab06.model.ship.ShipLogic;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MakeShips {
    private int id;
    private Ship ship;
    private ArrayList<Integer> pickedX; //arraylist which stores previously random generated X values, in order not to repeat them
    private ArrayList<Integer> pickedY; //arraylist which stores previously random generated Y values, in order not to repeat them
    private ArrayList<Ship> ships;
    private ArrayList<ArrayList<Field>> fieldLabelsArray;
    private ArrayList<BuoyHandler> buoyHandlers;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public MakeShips(ArrayList<ArrayList<Field>> fieldLabelsArray, ArrayList<Ship> ships, ArrayList<Integer> pickedX,
                     ArrayList<Integer> pickedY, Socket clientSocket, int id,
                     BufferedReader in, PrintWriter out, ArrayList<BuoyHandler> buoyHandlers) {
        this.fieldLabelsArray = fieldLabelsArray;
        this.ships = ships;
        this.clientSocket = clientSocket;
        this.pickedX = pickedX;
        this.pickedY = pickedY;
        this.id = id;
        this.in = in;
        this.out = out;
        this.buoyHandlers = buoyHandlers;
    }

    public ArrayList<Ship> addShip() { //add ship
        Random randomX = new Random();
        Random randomY = new Random();

        int randomPositionX = randomX.nextInt(40);
        int randomPositionY = randomY.nextInt(40);

        while (pickedX.contains(randomPositionX)) {
            randomPositionX = randomX.nextInt(40);
        }

        while (pickedY.contains(randomPositionY)) {
            randomPositionY = randomY.nextInt(40);
        }

        pickedX.add(randomPositionX);
        pickedY.add(randomPositionY);

        ShipLogic shipLogic = new ShipLogic(fieldLabelsArray, ships);

        ship = new Ship(clientSocket, id, randomPositionX, randomPositionY, shipLogic, buoyHandlers, in, out);
        ship.getOut().println("START"); //inform ship that it can start moving

        ship.start();
        ships.add(ship);
        return ships;
    }
}
