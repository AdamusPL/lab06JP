package pl.edu.pwr.aczekalski.lab06.model.buoy;

import pl.edu.pwr.aczekalski.lab06.model.Wave;
import pl.edu.pwr.aczekalski.lab06.model.board.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Buoy extends Thread {
    int id;
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;
    BuoyLogic buoyLogic;
    int buoyPositionX;
    int buoyPositionY;
    Board board;

    public Buoy(int id, Socket clientSocket, int buoyPositionX, int buoyPositionY, Board board) throws IOException {
        this.id = id;
        this.clientSocket = clientSocket;
        in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out=new PrintWriter(clientSocket.getOutputStream(), true);
        this.buoyPositionX = buoyPositionX;
        this.buoyPositionY = buoyPositionY;
        this.board=board;
    }

    @Override
    public void run(){
        while(true) {
            String info;
            try {
                info = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String[] split = info.split(",");
            List<Integer> converted = new ArrayList<Integer>();
            for (String number : split) {
                converted.add(Integer.parseInt(number.trim()));
            }


            int shipPositionX = converted.get(0);
            int shipPositionY = converted.get(1);

//            if ((shipPositionX <= buoyPositionX - 4 && shipPositionX >= buoyPositionX + 4) &&
//                    (shipPositionY >= buoyPositionY - 4) && (shipPositionY <= buoyPositionY + 4)){
                buoyLogic.makeWave(shipPositionX,shipPositionY,board);
//            }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                buoyLogic.resetWave(shipPositionX,shipPositionY,board);
        }
    }
}
