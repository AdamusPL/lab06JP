package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.socket.ShipSocket;

import javax.swing.*;

public class ShipMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShipSocket client = new ShipSocket();
                client.startT();
            }
        });
    }
}
