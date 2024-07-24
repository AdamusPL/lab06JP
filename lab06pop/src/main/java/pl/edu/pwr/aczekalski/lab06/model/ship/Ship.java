package pl.edu.pwr.aczekalski.lab06.model.ship;

import pl.edu.pwr.aczekalski.lab06.model.buoy.BuoyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Ship extends Thread {
    int id;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public PrintWriter getOut() {
        return out;
    }

    private ArrayList<BuoyHandler> buoyHandlers;

    private ShipLogic shipLogic;
    private int shipPositionX;
    private int shipPositionY;

    public void setShipPositionY(int shipPositionY) {
        this.shipPositionY = shipPositionY;
    }

    public void setShipPositionX(int shipPositionX) {
        this.shipPositionX = shipPositionX;
    }

    public int getShipPositionX() {
        return shipPositionX;
    }

    public int getShipPositionY() {
        return shipPositionY;
    }

    public Ship(Socket clientSocket, int id, int shipPositionX, int shipPositionY, ShipLogic shipLogic, ArrayList<BuoyHandler> buoyHandlers,
                BufferedReader in, PrintWriter out) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.shipPositionX = shipPositionX;
        this.shipPositionY = shipPositionY;
        this.shipLogic = shipLogic;
        this.id = id;
        this.buoyHandlers = buoyHandlers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String command = null;
                command = in.readLine();

                if (command.contains("MOVE")) { //if ship sent MOVE command
                    String[] split = command.split(","); //separate data
                    List<Integer> converted = new ArrayList<Integer>();
                    for (String number : split) {
                        if (!number.equals("MOVE")) {
                            converted.add(Integer.parseInt(number.trim()));
                        }
                    }

                    int directionX = converted.get(0);
                    int directionY = converted.get(1);

                    shipLogic.move(this, directionX, directionY); //check if ship can move

                    for (BuoyHandler b : buoyHandlers) { //null
                        if ((Math.abs(b.getBuoyPositionX() - shipPositionX) <= 4 && Math.abs(b.getBuoyPositionY() - shipPositionY) <= 4)) {
                            b.getOut().println(shipPositionX + "," + shipPositionY);
                        }
                    }

                    converted.clear();
                    Thread.sleep(100);
                }

                if ("SCAN".equals(command)) { //scan to find other ships if SCAN command was called
                    shipLogic.scan(this);
                    Thread.sleep(100);
                } else {
                    System.out.println();
                    out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("IO exception");
            System.err.println(e.getStackTrace());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
