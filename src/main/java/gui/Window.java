package gui;

import javax.swing.JFrame;

import java.awt.Dimension;

public class Window {
    public Window() {

    }

    /**
     * Creates a new JFrame instance
     */
    public void newForm()   {
        JFrame frame = new JFrame("Duke Pro");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 480));
        frame.setVisible(true);
    }
}
