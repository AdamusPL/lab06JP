package pl.edu.pwr.aczekalski.lab06.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Ship extends Thread {
    int id;
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;
    public int numberOfShips;
    String direction; //kierunek - N,NE,E,SE,S,SW,W,NW
    ShipLogic shipLogic;
    int shipPositionX;
    int shipPositionY;
    Wave wave;

    public Ship(Socket clientSocket, int id, String direction, int shipPositionX, int shipPositionY, ShipLogic shipLogic, Wave wave) throws IOException {
        this.clientSocket=clientSocket;
        in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out=new PrintWriter(clientSocket.getOutputStream(), true);
        this.direction=direction;
        this.shipPositionX=shipPositionX;
        this.shipPositionY=shipPositionY;
        this.shipLogic=shipLogic;
        this.wave=wave;
        this.id=id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String command = null;
                command = in.readLine();

                if ("MOVE".equals(command)) {
                    shipLogic.move(this);
                    Thread.sleep(1000);
                }

                if ("SCAN".equals(command)) {
                    shipLogic.scan(this);
                    Thread.sleep(1000);
                }

                else {
                    System.out.println("Nie rozpoznano");
                    out.println("unrecognised greeting");
                }
            }
        } catch (IOException e) {
            System.err.println("IO exception");
            System.err.println(e.getStackTrace());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
