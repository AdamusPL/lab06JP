package pl.edu.pwr.aczekalski.lab06.model.ship;

import pl.edu.pwr.aczekalski.lab06.model.buoy.BuoyHandler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Ship extends Thread {
    int id;

    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    ArrayList<BuoyHandler> buoyHandlers;

    ShipLogic shipLogic;
    public int shipPositionX;
    public int shipPositionY;
    JPanel simPanel;


    public Ship(Socket clientSocket, int id, int shipPositionX, int shipPositionY, ShipLogic shipLogic, ArrayList<BuoyHandler> buoyHandlers,
                JPanel simPanel, BufferedReader in, PrintWriter out) throws IOException {
        this.clientSocket =clientSocket;
        this.in=in;
        this.out=out;
        this.shipPositionX=shipPositionX;
        this.shipPositionY=shipPositionY;
        this.shipLogic=shipLogic;
        this.id=id;
        this.simPanel=simPanel;
        this.buoyHandlers=buoyHandlers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String command = null;
                command = in.readLine();

                if (command.contains("MOVE")) { //jeśli statek wysłał komendę MOVE
                    String[] split = command.split(","); //rozdziel dane
                    List<Integer> converted = new ArrayList<Integer>();
                    for (String number : split) {
                        if(!number.equals("MOVE")) {
                            converted.add(Integer.parseInt(number.trim()));
                        }
                    }

                    int directionX = converted.get(0);
                    int directionY = converted.get(1);

                    shipLogic.move(this,simPanel,directionX,directionY); //sprawdź czy może się ruszyć

                    for (BuoyHandler b: buoyHandlers) { //null
                        if((Math.abs(b.buoyPositionX-shipPositionX)<=4 && Math.abs(b.buoyPositionY-shipPositionY)<=4)){
                            b.out.println(shipPositionX+","+shipPositionY);
                        }
                    }

                    converted.clear();
                    Thread.sleep(100);
                }

                if ("SCAN".equals(command)) { //zeskanuj w poszukiwaniu innych statków jeśli statek wysłał SCAN
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
