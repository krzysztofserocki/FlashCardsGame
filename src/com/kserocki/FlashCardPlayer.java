package com.kserocki;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

    }
}
