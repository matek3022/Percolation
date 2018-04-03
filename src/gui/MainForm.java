package gui;

import javax.swing.*;


public class MainForm extends JFrame{
    private JButton startButton;
    private JPanel mainPanel;
    private JTextArea textArea1;

    public MainForm() {
        setContentPane(mainPanel);
        setVisible(true);
        setSize(500, 300);
        setTitle("MainFrame");
        startButton.setVisible(true);
        startButton.setSize(500, 100);
        textArea1.setText("Text");
        startButton.setText("Button");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
