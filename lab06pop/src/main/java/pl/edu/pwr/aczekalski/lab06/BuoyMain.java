package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.socket.BuoySocket;

import javax.swing.*;

public class BuoyMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BuoySocket client = new BuoySocket();
                client.startT();
            }
        });
    }

}
