package pl.edu.pwr.aczekalski.lab06.socket;

import pl.edu.pwr.aczekalski.lab06.model.ship.Direction;
import pl.edu.pwr.aczekalski.lab06.ux.ShipUX;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ShipSocket implements ActionListener {
    private ShipUX shipUX;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private void startConnection(String ip, int port) throws IOException {
        shipUX = new ShipUX(this);
        direction = new Direction();
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void sendRequest(String request) throws IOException {
        out.println(request);
    }

    private Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                startConnection("127.0.0.1", 49152);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendRequest("SHIP");
                System.out.println("Ship");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {

                String info = null;
                try {
                    info = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if ("Collision!".equals(info)) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    out.close();
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(0);
                } else {
                    System.out.println();
                }

                System.out.println(info);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void startT() {
        t.start();
    }

    private Direction direction;

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : shipUX.getButtonList()) {
            if (e.getSource() == button) {
                if (button.getText().equals("Scan")) {
                    try {
                        sendRequest("SCAN");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (button.getText().equals("Move")) {
                    try {
                        direction = Direction.randomDirection();
                        sendRequest("MOVE," + direction.getX() + "," + direction.getY());

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        switch (button.getText()) {
                            case "N":
                                setDirection(0, -1);
                                break;
                            case "NE":
                                setDirection(1, -1);
                                break;
                            case "E":
                                setDirection(1, 0);
                                break;
                            case "SE":
                                setDirection(1, 1);
                                break;
                            case "S":
                                setDirection(0, 1);
                                break;
                            case "SW":
                                setDirection(-1, 1);
                                break;
                            case "W":
                                setDirection(-1, 0);
                                break;
                            case "NW":
                                setDirection(-1, -1);
                                break;
                        }

                        sendRequest("MOVE," + direction.getX() + "," + direction.getY());

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    private void setDirection(int x, int y){
        direction.setX(x);
        direction.setY(y);
    }

}
