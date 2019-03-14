package com.kserocki;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardPlayer {

    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator cardIterator;
    private FlashCard currentCard;
    private int currentCardIndex;
    private JButton showButton;
    private JFrame frame;
    private boolean isShowAnswer;

    public FlashCardPlayer() {
        // Build UI (User Interface)
        frame = new JFrame("Flash Card Player");
        JPanel mainPanel = new JPanel();
        Font myFont = new Font("Helvetica Neue", Font.BOLD, 22);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup of display
        display = new JTextArea(10, 20);
        display.setFont(myFont);

        // JScrollPane question
        JScrollPane questionScrollPane = new JScrollPane(display);
        questionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        showButton = new JButton("Show Answer");

        mainPanel.add(questionScrollPane);
        mainPanel.add(showButton);
        showButton.addActionListener(new NextCardListener());

        // Add menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(new OpenMenuListener());

        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        // Add to frame
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(640, 500);
        frame.setVisible(true);
        frame.setJMenuBar(menuBar);

    }


    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardPlayer();
            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if( isShowAnswer ) {
                display.setText(currentCard.getAnswer());
                showButton.setText("Next card");
                isShowAnswer = false;
            } else {
                // Show the next question
                if (cardIterator.hasNext()) {
                    showNextCard();
                } else {
                    // No more cards
                    display.setText("That was last card");
                    showButton.setEnabled(false);
                }
            }

        }
    }

    class OpenMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    private void loadFile(File selectedFile) {

        cardList = new ArrayList<FlashCard>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;

            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }

        } catch (Exception e) {
            System.out.println("Couldn't read file");
            e.printStackTrace();
        }

        // Show the first card
        cardIterator = cardList.iterator();
        showNextCard();

    }

    private void showNextCard() {

        currentCard = (FlashCard) cardIterator.next();

        display.setText(currentCard.getQuestion());
        showButton.setText("Show Answer");
        isShowAnswer = true;
    }

    private void makeCard(String lineToParse) {

        String[] result = lineToParse.split("/");

        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("Made a card");
    }
}
