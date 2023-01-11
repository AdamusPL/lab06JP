package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.maker.MakeShips;
import pl.edu.pwr.aczekalski.lab06.model.Board;
import pl.edu.pwr.aczekalski.lab06.model.Field;
import pl.edu.pwr.aczekalski.lab06.model.Ship;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorldMain extends JFrame {

    public ServerSocket serverSocket;
    public Socket clientSocket;
    public PrintWriter out;

    ArrayList<Ship> ships;
    ArrayList<Integer> pickedX;
    ArrayList<Integer> pickedY;
    Board board;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        ships = new ArrayList<>();
        pickedX = new ArrayList<>();
        pickedY = new ArrayList<>();

        int id=0;
        while(true){
            System.out.println("Waiting for clients to connect");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            System.out.println();
            id++;
            MakeShips makeShips = new MakeShips(board.fieldLabelsArray, ships, pickedX, pickedY, clientSocket, id);
            ships = makeShips.addShip();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WorldMain worldMain = new WorldMain(); //przekazanie parametrów do konstruktora ramki
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                worldMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
                worldMain.setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
                worldMain.setTitle("Mapa świata");
                worldMain.setVisible(true);
                worldMain.startT();
            }
        });
    }

    JPanel simPanel;
    private JPanel contentPane;
    public WorldMain() throws IOException {
        board = new Board();

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
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                start(6666);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (true) {
                simPanel.repaint(); //odmalowanie i zrevalidowanie panelu
                simPanel.revalidate();
                try {
                    Thread.sleep(1000); //wait, żeby powstała "blokada" na sekundę żeby się wszystko zsynchronizowało
                } catch (Exception e) {
                }
            }
        }
    });

    public void startT(){
        t.start();
    }
}
