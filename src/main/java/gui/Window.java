package gui;

import javax.swing.JTextArea;
import javax.swing.JFrame;
import java.awt.Dimension;

public class Window {
    public static JTextArea outputArea;

    public Window(){
        JFrame frame = new JFrame("Duke Pro");
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        MainWindow mw = new MainWindow();
        outputArea = mw.outputText;
        frame.setContentPane(mw.rootPane);
        frame.setVisible(true);
    }
}
