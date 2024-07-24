package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.server.HeadQuartersServer;

import javax.swing.*;

public class HeadQuartersMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HeadQuartersServer headQuartersServer = new HeadQuartersServer();
                headQuartersServer.startT();
            }
        });
    }
}
