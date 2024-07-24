package pl.edu.pwr.aczekalski.lab06.model.buoy;

import pl.edu.pwr.aczekalski.lab06.model.board.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Buoy extends Thread {
    private int id;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BuoyLogic buoyLogic;
    private int buoyPositionX;
    private int buoyPositionY;
    private Board board;

    public PrintWriter getOut() {
        return out;
    }

    public Buoy(int id, Socket clientSocket, int buoyPositionX, int buoyPositionY, Board board, BuoyLogic buoyLogic) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.buoyPositionX = buoyPositionX;
        this.buoyPositionY = buoyPositionY;
        this.board = board;
        this.buoyLogic = buoyLogic;
    }

    @Override
    public void run() {
        while (true) {
            String info;
            try {
                info = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (info != null) {
                String[] split = info.split(",");
                List<Integer> converted = new ArrayList<Integer>();
                for (String number : split) {
                    converted.add(Integer.parseInt(number.trim()));
                }

                int shipPositionX = converted.get(0);
                int shipPositionY = converted.get(1);

                buoyLogic.makeWave(buoyPositionX, buoyPositionY, shipPositionX, shipPositionY, board);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                buoyLogic.resetWave(shipPositionX, shipPositionY, board);
            }
        }
    }
}
