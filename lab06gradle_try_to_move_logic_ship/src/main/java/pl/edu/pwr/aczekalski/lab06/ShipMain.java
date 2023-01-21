package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.model.ship.Direction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ShipMain extends JFrame implements ActionListener{
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

    private JButton button;
    private JButton moveButton;

    //FOR DEBBUGING MODE!
    private JButton buttonN;
    private JButton buttonNE;
    private JButton buttonE;
    private JButton buttonSE;
    private JButton buttonS;
    private JButton buttonSW;
    private JButton buttonW;
    private JButton buttonNW;


    public ShipMain(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 200, 300);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); //zeby mozna bylo dowolnie ustawiac miejsca panelow w ramce

        button = new JButton("Scan");
        button.addActionListener(this);
        button.setBounds(30,50,100,50);
        contentPane.add(button);

//        moveButton = new JButton("Move");
//        moveButton.addActionListener(this);
//        moveButton.setBounds(30,110,100,50);
//        contentPane.add(moveButton);

        buttonN = new JButton("N");
        buttonN.addActionListener(this);
        buttonN.setBounds(60,110,50,50);
        contentPane.add(buttonN);

        buttonNE = new JButton("NE");
        buttonNE.addActionListener(this);
        buttonNE.setBounds(110,110,50,50);
        contentPane.add(buttonNE);

        buttonE = new JButton("E");
        buttonE.addActionListener(this);
        buttonE.setBounds(110,160,50,50);
        contentPane.add(buttonE);

        buttonSE = new JButton("SE");
        buttonSE.addActionListener(this);
        buttonSE.setBounds(110,210,50,50);
        contentPane.add(buttonSE);

        buttonS = new JButton("S");
        buttonS.addActionListener(this);
        buttonS.setBounds(60,210,50,50);
        contentPane.add(buttonS);

        buttonSW = new JButton("SW");
        buttonSW.addActionListener(this);
        buttonSW.setBounds(5,210,55,50);
        contentPane.add(buttonSW);

        buttonW = new JButton("W");
        buttonW.addActionListener(this);
        buttonW.setBounds(5,160,50,50);
        contentPane.add(buttonW);

        buttonNW = new JButton("NW");
        buttonNW.addActionListener(this);
        buttonNW.setBounds(5,110,55,50);
        contentPane.add(buttonNW);

        setContentPane(contentPane);

        setTitle("Ship");
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ShipMain client = new ShipMain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.startT();
            }
        });
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                startConnection("127.0.0.1",49152);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendRequest("SHIP");
                System.out.println("Ship");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while(true){
//                try {
//                    direction = Direction.randomDirection();
//                    sendRequest("MOVE,"+direction.x+","+direction.y);
//
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }

                String info = null;
                try {
                    info = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if("Collision!".equals(info)){
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
                }

                else{
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

    public void startT(){
        t.start();
    }

//    Direction direction; //for auto
    Direction direction = new Direction(); //FOR DEBBUGING

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            try {
                sendRequest("SCAN");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==moveButton){
            try {
                direction = Direction.randomDirection();
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonN){
            try {
                direction.x=0;
                direction.y=-1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonNE){
            try {
                direction.x=1;
                direction.y=-1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonE){
            try {
                direction.x=1;
                direction.y=0;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonSE){
            try {
                direction.x=1;
                direction.y=1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonS){
            try {
                direction.x=0;
                direction.y=1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonSW){
            try {
                direction.x=-1;
                direction.y=1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonW){
            try {
                direction.x=-1;
                direction.y=0;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==buttonNW){
            try {
                direction.x=-1;
                direction.y=-1;
                sendRequest("MOVE,"+direction.x+","+direction.y);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
