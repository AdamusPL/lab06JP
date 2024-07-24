package pl.edu.pwr.aczekalski.lab06.server;

import pl.edu.pwr.aczekalski.lab06.maker.buoy.MakeBuoys;
import pl.edu.pwr.aczekalski.lab06.model.buoy.Buoy;
import pl.edu.pwr.aczekalski.lab06.ux.HeadQuartersUX;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HeadQuartersServer {
    private HeadQuartersUX headQuartersUX;
    private ArrayList<Buoy> buoys;
    private int id;
    private int positionX;
    private int positionY;

    private void initialize() throws IOException {
        headQuartersUX = new HeadQuartersUX();
        buoys = new ArrayList<>();
        serverSocket = new ServerSocket(49153);
        id = 0;
        positionX = 2;
        positionY = 2;
    }

    private void addNewClient() throws IOException {
        System.out.println("Waiting for clients to connect");
        clientSocket = serverSocket.accept();
        System.out.println("Buoy connected");
        System.out.println();
        id++;
        System.out.println("Buoy nr: " + id);
        MakeBuoys makeBuoys = new MakeBuoys(clientSocket, id, positionX, positionY, buoys, headQuartersUX.getBoard());
        buoys = makeBuoys.addBuoy();
        headQuartersUX.getBoard().getFieldLabelsArray().get(positionY).get(positionX).setBuoys();
        positionX += 5;
        if (positionX == 42) {
            positionY += 5;
            positionX = 2;
        }
    }

    private ServerSocket serverSocket;
    private Socket clientSocket;

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
