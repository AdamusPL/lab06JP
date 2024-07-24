package pl.edu.pwr.aczekalski.lab06.ux;

import pl.edu.pwr.aczekalski.lab06.model.board.Board;
import pl.edu.pwr.aczekalski.lab06.model.board.Field;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class HeadQuartersUX extends JFrame {

    public Board getBoard() {
        return board;
    }

    private Board board;
    private JPanel simPanel;
    private JPanel contentPane;

    public HeadQuartersUX() {
        board = new Board("0");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 700, 500);
        simPanel.setLayout(null);

        board.makeBoard();

        for (ArrayList<Field> rowArray : board.getFieldLabelsArray()) {
            for (Field f : rowArray) {
                JLabel label = f.getLabel();
                label.setBounds(f.getX(), f.getY(), 10, 10);
                simPanel.add(label);
            }
        }

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Wave Map");
        setVisible(true);
    }

}
