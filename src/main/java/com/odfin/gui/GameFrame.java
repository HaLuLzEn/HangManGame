package com.odfin.gui;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setTitle("Hangman Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new GamePanel());
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
