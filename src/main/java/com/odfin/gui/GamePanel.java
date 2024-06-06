package com.odfin.gui;

import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
                System.out.println("YES");
                for (int i = 0; i < wordToGuess.length; i++) {
                    if (Character.toUpperCase(wordToGuess[i]) == guessedUpperCase && !guessedLetters[i]) {
                        letterLabels[i].setText(String.valueOf(guessedLetter));
                        guessedLetters[i] = true;
                    }
                }
            } else {
                System.out.println("NO");
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
            if (!checkWord(guessedLetters)) {
                JOptionPane.showMessageDialog(null, "You won, the word is: " + Arrays.toString(wordToGuess), "Nice", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
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

    boolean checkWord(boolean[] guessedLetters) {
        int guessCount = guessedLetters.length;
        for (int i = 0; i < guessedLetters.length; i++) {
            if (guessedLetters[i])
                guessCount--;
        }

        if (guessCount == 0)
            return false;
        else
            return true;
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
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        switch (TRIES) {
            case 9:
                g2.drawLine(120, 40, 120, 400); // vertical line
                break;
            case 8:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                break;
            case 7:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                break;
            case 6:
                TRIES--;
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                break;
            case 5:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                break;
            case 4:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                g2.drawLine(200, 120, 200, 240); // body
                break;
            case 3:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                g2.drawLine(200, 120, 200, 240); // body
                g2.drawLine(200, 140, 160, 180); // left arm
                break;
            case 2:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                g2.drawLine(200, 120, 200, 240); // body
                g2.drawLine(200, 140, 160, 180); // left arm
                g2.drawLine(200, 140, 240, 180); // right arm
                break;
            case 1:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                g2.drawLine(200, 120, 200, 240); // body
                g2.drawLine(200, 140, 160, 180); // left arm
                g2.drawLine(200, 140, 240, 180); // right arm
                g2.drawLine(200, 240, 160, 300); // left leg
                break;
            case 0:
                g2.drawLine(120, 40, 120, 400); // vertical line
                g2.drawLine(40, 400, 200, 400); // horizontal line
                g2.drawLine(120, 40, 200, 40); // horizontal line
                g2.drawLine(200, 40, 200, 80); // short vertical line
                g2.drawOval(180, 80, 40, 40); // head
                g2.drawLine(200, 120, 200, 240); // body
                g2.drawLine(200, 140, 160, 180); // left arm
                g2.drawLine(200, 140, 240, 180); // right arm
                g2.drawLine(200, 240, 160, 300); // left leg
                g2.drawLine(200, 240, 240, 300); // right leg
                break;
        }
    }
}
