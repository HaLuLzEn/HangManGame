package com.odfin.gui;

import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.odfin.core.Main.TRIES;
import static com.odfin.core.Main.wordToGuess;

public class GamePanel extends JPanel {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    public boolean wrongGuess = false;

    private JPanel rightPanel;
    private JPanel buttonsPanel;
    private JPanel hangmanPanel;
    private double[][] rightSizes = {
            {5, TableLayout.FILL, 5},
            {20, TableLayout.PREFERRED, 20}
    };

    private JLabel triesLabel;

    private double[][] hangmanSizes = {
            {20, TableLayout.FILL, 20},
            {15, TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 15}
    };
    private JLabel[] letterLabels;
    private JPanel lettersPanel;

    private HangmanPanel hangman;
    boolean[] guessedLetters = new boolean[wordToGuess.length];

    char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß".toCharArray();
    JButton[] buttons = new JButton[letters.length];
    final Font LETTER_FONT = new Font("ALLA", Font.PLAIN, 75);
    final Font DEFAULT_FONT = new Font("ALTER", Font.PLAIN, 25);

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new BorderLayout());

        rightPanel = new JPanel();
        triesLabel = new JLabel();
        triesLabel.setFont(DEFAULT_FONT);
        rightPanel.setBounds(500, 0, 300, HEIGHT);
        rightPanel.setLayout(new TableLayout(rightSizes));
        rightPanel.add(triesLabel, "1, 1");
        this.add(rightPanel, BorderLayout.EAST);

        lettersPanel = new JPanel();
        letterLabels = new JLabel[wordToGuess.length];
        lettersPanel.setLayout(new GridLayout(1, wordToGuess.length));
        for (int i = 0; i < letterLabels.length; i++) {
            letterLabels[i] = new JLabel("_");
            letterLabels[i].setFont(LETTER_FONT);
            lettersPanel.add(letterLabels[i]);
        }


        hangmanPanel = new JPanel();
        hangman = new HangmanPanel();
        hangman.setPreferredSize(new Dimension(400, 400));
        hangmanPanel.setLayout(new TableLayout(hangmanSizes));
        hangmanPanel.add(lettersPanel, "1, 3");
        hangmanPanel.add(hangman, "1, 1");
        this.add(hangmanPanel, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, buttons.length));
        initializeButtons(buttonsPanel, buttons);
        this.add(buttonsPanel, BorderLayout.SOUTH);

    }

    void initializeButtons(JPanel panel, JButton[] buttons) {
        ActionListener buttonListener = e -> {
            System.out.println(((JButton) e.getSource()).getText());
            char guessedLetter = ((JButton) e.getSource()).getText().charAt(0);
            char guessedUpperCase = Character.toUpperCase(guessedLetter);
            if (checkLetter(guessedUpperCase, wordToGuess)) {
                for (int i = 0; i < wordToGuess.length; i++) {
                    if (wordToGuess[i] == guessedUpperCase && !guessedLetters[i]) {
                        letterLabels[i].setText(String.valueOf(guessedUpperCase));
                        guessedLetters[i] = true;
                    }
                }
            } else {
                if (TRIES > 1) {
                    TRIES--;
                    repaint();
                } else {
                    TRIES = 0;
                    repaint();
                    JOptionPane.showMessageDialog(null, "You lost", "Game over", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
            ((JButton) e.getSource()).setEnabled(false);
        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(String.valueOf(letters[i]));
            panel.add(buttons[i]);
            buttons[i].addActionListener(buttonListener);
        }
        repaint();
    }

    boolean checkLetter(char letter, char[] wordLetters) {
        for (char wordLetter : wordLetters) {
            if (letter == Character.toUpperCase(wordLetter))
                return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        triesLabel.setText("Tries left: " + TRIES);
    }
}

class HangmanPanel extends JPanel {
    public HangmanPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        //if () {
            switch (TRIES) {
                case 9:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    break;
                case 8:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    break;
                case 7:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    break;
                case 6:
                    TRIES--;
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    break;
                case 5:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    break;
                case 4:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    g2.drawLine(100, 60, 100, 120); // body
                    break;
                case 3:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    g2.drawLine(100, 60, 100, 120); // body
                    g2.drawLine(100, 70, 80, 90); // left arm
                    break;
                case 2:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    g2.drawLine(100, 60, 100, 120); // body
                    g2.drawLine(100, 70, 80, 90); // left arm
                    g2.drawLine(100, 70, 120, 90); // right arm
                    break;
                case 1:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    g2.drawLine(100, 60, 100, 120); // body
                    g2.drawLine(100, 70, 80, 90); // left arm
                    g2.drawLine(100, 70, 120, 90); // right arm
                    g2.drawLine(100, 120, 80, 150); // left leg
                    break;
                case 0:
                    g2.drawLine(60, 20, 60, 200); // vertical line
                    g2.drawLine(20, 200, 100, 200); // horizontal line
                    g2.drawLine(60, 20, 100, 20); // horizontal line
                    g2.drawLine(100, 20, 100, 40); // short vertical line
                    g2.drawOval(90, 40, 20, 20); // head
                    g2.drawLine(100, 60, 100, 120); // body
                    g2.drawLine(100, 70, 80, 90); // left arm
                    g2.drawLine(100, 70, 120, 90); // right arm
                    g2.drawLine(100, 120, 80, 150); // left leg
                    g2.drawLine(100, 120, 120, 150); // right leg
                    break;
            }
      //  }
    }
}
