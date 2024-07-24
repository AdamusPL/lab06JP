package pl.edu.pwr.aczekalski.lab06.ux;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShipUX extends JFrame {

    private JPanel contentPane;

    public List<JButton> getButtonList() {
        return buttonList;
    }

    private List<JButton> buttonList;
    private ActionListener actionListener;

    public ShipUX(ActionListener actionListener) {
        buttonList = new ArrayList<>();
        this.actionListener = actionListener;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 200, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        addButton("Scan", 30, 50, 100);
        addButton("N", 60, 110, 50);
        addButton("NE", 110, 110, 50);
        addButton("E", 110, 160, 50);
        addButton("SE", 110, 210, 50);
        addButton("S", 60, 210, 50);
        addButton("SW", 5, 210, 55);
        addButton("W", 5, 160, 50);
        addButton("NW", 5, 110, 55);

        setContentPane(contentPane);

        setTitle("Ship");
        setAlwaysOnTop(true);
        setVisible(true);
    }

    private void addButton(String text, int x, int y, int width) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setBounds(x, y, width, 50);
        contentPane.add(button);
        buttonList.add(button);
    }
}
