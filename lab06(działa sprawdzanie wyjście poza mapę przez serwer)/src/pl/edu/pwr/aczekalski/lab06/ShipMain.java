package pl.edu.pwr.aczekalski.lab06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ShipMain {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendRequest(String request) throws IOException {
        out.println(request);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ShipMain client = new ShipMain();
        client.startConnection("127.0.0.1",6666);
        while(true) {
            String command = null;
            command = client.in.readLine();
            if("START".equals(command)){
                while(true){
                    client.sendRequest("MOVE");
                    client.sendRequest("SCAN");
                    String coordinates = client.in.readLine();
                    System.out.println(coordinates);
                }
            }
        }

//        System.out.println("Czy pływa:");
//        System.out.println("Aktualna komenda:");
//        System.out.println("Widzę na horyzoncie:");
    }


}
