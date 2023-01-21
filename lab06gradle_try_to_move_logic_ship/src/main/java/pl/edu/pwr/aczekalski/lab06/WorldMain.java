package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.maker.ship.MakeShips;
import pl.edu.pwr.aczekalski.lab06.model.board.Board;
import pl.edu.pwr.aczekalski.lab06.model.board.Field;
import pl.edu.pwr.aczekalski.lab06.model.buoy.BuoyHandler;
import pl.edu.pwr.aczekalski.lab06.model.ship.Ship;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorldMain extends JFrame {

    public ServerSocket serverSocket;
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    ArrayList<Ship> ships;
    ArrayList<BuoyHandler> buoyHandlers;
    ArrayList<Integer> pickedX;
    ArrayList<Integer> pickedY;
    Board board;
    int id;
    boolean added; //tu coś nie halo, zastanowić się
    int x=2,y=2;

    public void initialize(int port) throws IOException { //zainicjalizuj arraylisty, serversocket i inne potrzebne zmienne/obiekty
        serverSocket = new ServerSocket(port);
        buoyHandlers=new ArrayList<>();
        ships = new ArrayList<>();
        pickedX = new ArrayList<>();
        pickedY = new ArrayList<>();
        id=0;
    }

    public void addNewClient() throws IOException { //dodaj nowego klienta
        added=false;
        System.out.println("Waiting for clients to connect");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }


    public void differentiate() throws IOException { //rozróżnij kto się podłącza do serwera
        String info;
        info = in.readLine();
        if ("SHIP".equals(info)) { //jeśli to statek, to dodaj statek na planszę
            System.out.println("Statek");
            id++;
            MakeShips makeShips = new MakeShips(board.fieldLabelsArray, ships, pickedX, pickedY, clientSocket, id, simPanel,in,out,buoyHandlers);
            ships = makeShips.addShip();
            added=true;
        }

        else if("BUOY".equals(info)){ //jeśli boja, poustawiaj statkom sockety boi
            BuoyHandler buoyHandler = new BuoyHandler(clientSocket,out,in,x,y);
            buoyHandlers.add(buoyHandler);

//            for (Ship s: ships) {
//                s.setBuoyHandlers(buoyHandlers);
//            }

            System.out.println("Boja");
            added=true;
            x+=5;
            if(x==42){
                y+=5;
                x=2;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WorldMain worldMain = new WorldMain(); //przekazanie parametrów do konstruktora ramki
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                worldMain.startT();
            }
        });
    }

    JPanel simPanel;
    private JPanel contentPane;
    public WorldMain() throws IOException {
        board = new Board(".");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); //zeby mozna bylo dowolnie ustawiac miejsca panelow w ramce

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 700, 500);
        simPanel.setLayout(null);

        //tu dodaj rzeczy do okienka
        board.makeBoard();

        for (ArrayList<Field> rowArray : board.fieldLabelsArray) {
            for (Field f : rowArray) {
                JLabel label = f.label;
                label.setBounds(f.x, f.y, 10, 10);
                simPanel.add(label);
            }
        }

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
        setAlwaysOnTop(true);
        setTitle("Mapa świata");
        setVisible(true);
    }

    //trzeba jakoś zrepaintować simPanel!

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                initialize(49152);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                try {
                    addNewClient();
                    while(true){
                        differentiate();
                        if(added) break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void startT(){
        t.start();
    }
}
