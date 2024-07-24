package pl.edu.pwr.aczekalski.lab06.model.buoy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuoyHandler {
    private int buoyPositionX;
    private int buoyPositionY;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public int getBuoyPositionX() {
        return buoyPositionX;
    }

    public int getBuoyPositionY() {
        return buoyPositionY;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BuoyHandler(Socket clientSocket, PrintWriter out, BufferedReader in, int buoyPositionX, int buoyPositionY) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
        this.buoyPositionX = buoyPositionX;
        this.buoyPositionY = buoyPositionY;
    }

}
