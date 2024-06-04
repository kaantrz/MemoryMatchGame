import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryMatchGame extends JFrame implements ActionListener {

    private final int GRID_SIZE = 4;
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private final String[] icons = {"A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H"};
    private final ArrayList<String> iconList = new ArrayList<>();
    private JButton firstButton = null;
    private JButton secondButton = null;
    private int pairsFound = 0;

    public MemoryMatchGame() {
        setTitle("Memory Match Game");
        setSize(400, 400);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (String icon : icons) {
            iconList.add(icon);
        }
        Collections.shuffle(iconList);

        initializeGrid();
        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                buttons[i][j].putClientProperty("icon", iconList.remove(0));
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (clickedButton.getText().equals("") && secondButton == null) {
            clickedButton.setText((String) clickedButton.getClientProperty("icon"));

            if (firstButton == null) {
                firstButton = clickedButton;
            } else {
                secondButton = clickedButton;
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkForMatch();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void checkForMatch() {
        if (firstButton.getText().equals(secondButton.getText())) {
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
            pairsFound++;
            if (pairsFound == (GRID_SIZE * GRID_SIZE) / 2) {
                JOptionPane.showMessageDialog(this, "You won!");
            }
        } else {
            firstButton.setText("");
            secondButton.setText("");
        }
        firstButton = null;
        secondButton = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemoryMatchGame());
    }
}
