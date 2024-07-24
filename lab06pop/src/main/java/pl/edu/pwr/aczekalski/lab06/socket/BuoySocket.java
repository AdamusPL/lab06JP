package pl.edu.pwr.aczekalski.lab06.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BuoySocket {
    public BuoySocket(){
        this.assign = false;
    }
    private boolean assign;
    private int buoyPositionX;
    private int buoyPositionY;
    private Socket clientWorld;
    private Socket clientCentral;
    private PrintWriter outWorld;
    private PrintWriter outCentral;
    private BufferedReader inWorld;
    private BufferedReader inCentral;

    private void startConnectionWorld() throws IOException {
        clientWorld = new Socket("127.0.0.1", 49152);
        outWorld = new PrintWriter(clientWorld.getOutputStream(), true);
        inWorld = new BufferedReader(new InputStreamReader(clientWorld.getInputStream()));
    }

    private void startConnectionCentral() throws IOException {
        clientCentral = new Socket("127.0.0.1", 49153);
        outCentral = new PrintWriter(clientCentral.getOutputStream(), true);
        inCentral = new BufferedReader(new InputStreamReader(clientCentral.getInputStream()));
    }

    private void sendRequestWorld() throws IOException {
        outWorld.println("BUOY");
    }

    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                startConnectionWorld(); //connect buoy to the world
                startConnectionCentral(); //connect buoy to the central
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendRequestWorld();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String info = null;
            while (true) {
                try {
                    info = inWorld.readLine();
                    String[] split = info.split(",");
                    List<Integer> converted = new ArrayList<Integer>();
                    for (String number : split) {
                        converted.add(Integer.parseInt(number.trim()));
                    }

                    if (!assign) {
                        buoyPositionX = converted.get(0);
                        buoyPositionY = converted.get(1);
                        assign = true;
                    }

                    outCentral.println(info);

                    System.out.println(info);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                try {
                    Thread.sleep(100);
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
