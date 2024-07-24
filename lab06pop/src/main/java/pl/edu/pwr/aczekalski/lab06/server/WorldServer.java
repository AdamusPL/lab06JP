package pl.edu.pwr.aczekalski.lab06.server;

import pl.edu.pwr.aczekalski.lab06.maker.ship.MakeShips;
import pl.edu.pwr.aczekalski.lab06.model.buoy.BuoyHandler;
import pl.edu.pwr.aczekalski.lab06.model.ship.Ship;
import pl.edu.pwr.aczekalski.lab06.ux.WorldUX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorldServer {
    private WorldUX worldUX;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private ArrayList<Ship> ships;
    private ArrayList<BuoyHandler> buoyHandlers;
    private ArrayList<Integer> pickedX;
    private ArrayList<Integer> pickedY;
    private int id;
    private boolean added;
    private int x = 2, y = 2;

    private void initialize() throws IOException { //arraylist, server socket and other variables/objects initialization
        worldUX = new WorldUX();
        serverSocket = new ServerSocket(49152, 64);
        buoyHandlers = new ArrayList<>();
        ships = new ArrayList<>();
        pickedX = new ArrayList<>();
        pickedY = new ArrayList<>();
        id = 0;
    }

    private void addNewClient() throws IOException { //handle new client
        added = false;
        System.out.println("Waiting for clients to connect");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }


    private void differentiate() throws IOException { //distinguish who connects to the server
        String info;
        info = in.readLine();
        if ("SHIP".equals(info)) { //if it's ship - add it to the board
            System.out.println("Ship");
            id++;
            MakeShips makeShips = new MakeShips(worldUX.getBoard().getFieldLabelsArray(), ships, pickedX, pickedY, clientSocket, id, in, out, buoyHandlers);
            ships = makeShips.addShip();
            added = true;
        } else if ("BUOY".equals(info)) { //if it's buoy - give info about buoys to ships
            BuoyHandler buoyHandler = new BuoyHandler(clientSocket, out, in, x, y);
            buoyHandlers.add(buoyHandler);

            System.out.println("Buoy");
            added = true;
            x += 5;
            if (x == 42) {
                y += 5;
                x = 2;
            }
        }
    }

    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                initialize();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try {
                    addNewClient();
                    while (true) {
                        differentiate();
                        if (added) break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void startT() {
        t.start();
    }
}
