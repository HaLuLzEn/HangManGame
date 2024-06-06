package com.odfin.core;

import com.odfin.gui.GameFrame;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int TRIES = 10;
    public static final String WORDS_FILE = "words.txt";
    public static String[] foundWords;
    public static char[] displayWord;
    public static char[] wordToGuess;
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(WORDS_FILE)) {
            Scanner sc = new Scanner(fis);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine().trim());
            }
            foundWords = sb.toString().split(",");
            wordToGuess = foundWords[(int) (Math.random() * foundWords.length)].trim().toCharArray();
            displayWord = new char[wordToGuess.length];
            System.out.println("DEBUG: " + Arrays.toString(wordToGuess));

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
