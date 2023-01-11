package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.model.Board;
import pl.edu.pwr.aczekalski.lab06.model.Field;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class HeadQuartersMain extends JFrame {

    public static void main(String[] args) {
        HeadQuartersMain headQuartersFrame = new HeadQuartersMain();
        headQuartersFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        headQuartersFrame.setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
        headQuartersFrame.setAlwaysOnTop(true);
        headQuartersFrame.setVisible(true);
        headQuartersFrame.startT();
    }

    JPanel simPanel;
    private JPanel contentPane;

    public HeadQuartersMain() {
        Board board = new Board();
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

        //tu dodaj rzeczy do okienka

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {

            while (true) {
                simPanel.repaint(); //odmalowanie i zrevalidowanie panelu
                simPanel.revalidate();
                try {
                    wait(500); //wait, żeby powstała "blokada" na sekundę żeby się wszystko zsynchronizowało
                } catch (Exception e) {
                }
            }
        }
    });

    public void startT(){
        t.start();
    }



}
