package com.kserocki;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardBuilder {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    public FlashCardBuilder() {
        // Build the User interface
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold everything
        JPanel mainPanel = new JPanel();

        // Create amd setup font
        Font greatFont = new Font("Helvetica Neue", Font.BOLD, 21);

        // Question font area
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(greatFont);

        // Answer font area
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(greatFont);

        // JScrollPane question
        JScrollPane questionScrollPane = new JScrollPane(question);
        questionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // JScrollPane answer
        JScrollPane answerScrollPane = new JScrollPane(answer);
        answerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Setup buttons
        JButton nextButton = new JButton("Next Card");

        // Setup ArrayList as FlashCard object holder
        cardList = new ArrayList<FlashCard>();

        // Create a few labels
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        // Add components to mainPanel
        mainPanel.add(questionLabel);
        mainPanel.add(questionScrollPane);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScrollPane);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        // Add eventListeners
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuItemListener());


        // Add to the Frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a FlashCard object
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
            System.out.println("Size of arrayList: " + cardList.size());
        }
    }

    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New menu clicked");
        }
    }

    class SaveMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a file dialog with file chooser
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private void saveFile(File selectedFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
            Iterator<FlashCard> cardIterator = cardList.iterator();

            for (FlashCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();

        } catch (Exception e) {
            System.out.println("Couldn't write to file");
            e.printStackTrace();
        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
}
