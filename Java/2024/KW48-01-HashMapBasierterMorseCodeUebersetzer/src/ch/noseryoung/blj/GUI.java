package ch.noseryoung.blj;
import javax.swing.*;

public class GUI {
    public GUI(){

        // Erstelle ein JFrame
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Erstelle ein JTextField
        JTextField textField = new JTextField();
        textField.setBounds(20, 20, 200, 30);

        // Erstelle ein JLabel
        JLabel label = new JLabel("Mein Label");
        label.setBounds(20, 60, 200, 30);

        // Erstelle ein JButton
        JButton button = new JButton("Klick mich");
        button.setBounds(20, 100, 200, 30);


        // Füge die GUI-Elemente dem Content Pane hinzu
        frame.getContentPane().add(textField);
        frame.getContentPane().add(label);
        frame.getContentPane().add(button);

        // Setze das Layout des Content Pane auf null
        frame.getContentPane().setLayout(null);

        // Setze das JFrame sichtbar
        frame.setVisible(true);
    }
}
