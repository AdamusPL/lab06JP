package pl.edu.pwr.aczekalski.lab06;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BuoyMain {
    boolean assign=false;
    int buoyPositionX;
    int buoyPositionY;
    private Socket clientWorld;
    private Socket clientCentral;
    private PrintWriter outWorld;
    private PrintWriter outCentral;
    private BufferedReader inWorld;
    private BufferedReader inCentral;

    public void startConnectionWorld(String ip, int port) throws IOException {
        clientWorld = new Socket(ip, port);
        outWorld = new PrintWriter(clientWorld.getOutputStream(), true);
        inWorld = new BufferedReader(new InputStreamReader(clientWorld.getInputStream()));
    }

    public void startConnectionCentral(String ip, int port) throws IOException {
        clientCentral = new Socket(ip, port);
        outCentral = new PrintWriter(clientCentral.getOutputStream(), true);
        inCentral = new BufferedReader(new InputStreamReader(clientCentral.getInputStream()));
    }

    public void sendRequestWorld(String request) throws IOException {
        outWorld.println(request);
    }

    public void sendRequestCentral(String request) throws IOException {
        outCentral.println(request);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BuoyMain client = new BuoyMain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.startT();
            }
        });
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                startConnectionWorld("127.0.0.1",49152); //połączenie boi do świata
                startConnectionCentral("127.0.0.1",49153); //połączenie boi do centrali
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendRequestWorld("BUOY");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String info=null;
            while (true){
                try {
                    info = inWorld.readLine();
                    String[] split = info.split(",");
                    List<Integer> converted = new ArrayList<Integer>();
                    for (String number : split) {
                        converted.add(Integer.parseInt(number.trim()));
                    }

                    if(!assign){
                        buoyPositionX=converted.get(0);
                        buoyPositionY=converted.get(1);
                        assign=true;
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

    public void startT(){
        t.start();
    }

}
