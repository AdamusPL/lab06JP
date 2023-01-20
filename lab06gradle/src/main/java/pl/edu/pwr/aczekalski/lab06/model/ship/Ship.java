package pl.edu.pwr.aczekalski.lab06.model.ship;

import pl.edu.pwr.aczekalski.lab06.model.Wave;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Ship extends Thread {
    int id;

    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    public Socket clientBuoySocket;
    public PrintWriter outBuoy;
    public BufferedReader inBuoy;

    public void setClientBuoySocket(Socket clientBuoySocket) {
        this.clientBuoySocket = clientBuoySocket;
    }

    public void setOutBuoy(PrintWriter outBuoy) {
        this.outBuoy = outBuoy;
    }

    public void setInBuoy(BufferedReader inBuoy) {
        this.inBuoy = inBuoy;
    }

    Direction direction;
    ShipLogic shipLogic;
    public int shipPositionX;
    public int shipPositionY;
    Wave wave;
    JPanel simPanel;
    boolean firstMove=false;


    public Ship(Socket clientSocket, int id, Direction direction, int shipPositionX, int shipPositionY, ShipLogic shipLogic,
                Wave wave, JPanel simPanel, BufferedReader in, PrintWriter out) throws IOException {
        this.clientSocket =clientSocket;
        this.in=in;
        this.out=out;
        this.shipPositionX=shipPositionX;
        this.shipPositionY=shipPositionY;
        this.shipLogic=shipLogic;
        this.direction=direction;
        this.wave=wave;
        this.id=id;
        this.simPanel=simPanel;
    }

    @Override
    public void run() {
        try {
//            shipLogic.initShip(this);
            while (true) {
                String command = null;
                command = in.readLine();

                if ("MOVE".equals(command)) {
//                    if(!firstMove) {
//                        shipLogic.unsetFirst(this);
//                        firstMove=true;
//                    }
                    shipLogic.move(this,simPanel);
                    outBuoy.println(shipPositionX+","+shipPositionY); //informacja do boi że statek się ruszył
//                    wave.makeWave();
                    Thread.sleep(100);
//                    wave.resetWave();
                }

                if ("SCAN".equals(command)) {
                    shipLogic.scan(this);
                    Thread.sleep(100);
                }

                else {
                    System.out.println();
                    out.println();
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
