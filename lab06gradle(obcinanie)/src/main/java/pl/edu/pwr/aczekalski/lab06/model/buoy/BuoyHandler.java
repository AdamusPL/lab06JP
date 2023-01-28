package pl.edu.pwr.aczekalski.lab06.model.buoy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuoyHandler {
    public int buoyPositionX;
    public int buoyPositionY;

    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    public BuoyHandler(Socket clientSocket, PrintWriter out, BufferedReader in, int buoyPositionX, int buoyPositionY) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
        this.buoyPositionX=buoyPositionX;
        this.buoyPositionY=buoyPositionY;
    }

}
