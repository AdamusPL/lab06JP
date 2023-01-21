package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.maker.buoy.MakeBuoys;
import pl.edu.pwr.aczekalski.lab06.model.board.Board;
import pl.edu.pwr.aczekalski.lab06.model.board.Field;
import pl.edu.pwr.aczekalski.lab06.model.buoy.Buoy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HeadQuartersMain extends JFrame {
    public ServerSocket serverSocket;
    public Socket clientSocket;
    public PrintWriter out;
    ArrayList<Buoy> buoys;
    Board board;
    int id;
    int positionX;
    int positionY;

    public void initialize(int port) throws IOException {
        buoys=new ArrayList<>();
        serverSocket = new ServerSocket(port);
        id=0;
        positionX=2;
        positionY=2;
    }

    public void addNewClient() throws IOException {
        System.out.println("Waiting for clients to connect");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        System.out.println();
        id++;
        MakeBuoys makeBuoys = new MakeBuoys(clientSocket, id, positionX, positionY, buoys, board);
        buoys = makeBuoys.addBuoy();
        board.fieldLabelsArray.get(positionY).get(positionX).setBuoys();
        positionX+=5;
        if(positionX==42){
            positionY+=5;
            positionX=2;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HeadQuartersMain headQuartersMain = new HeadQuartersMain(); //przekazanie parametrów do konstruktora ramki
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                headQuartersMain.startT();
            }
        });
    }

    JPanel simPanel;
    private JPanel contentPane;
    public HeadQuartersMain() throws IOException {
        board = new Board("0");

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
                initialize(49153);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(true){
                try {
                    addNewClient();
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
