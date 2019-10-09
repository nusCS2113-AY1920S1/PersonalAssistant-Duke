package GUI;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window() {

    }

    public void newForm()   {
        JFrame frame = new JFrame("Duke Pro");
        frame.setMinimumSize(new Dimension(800, 480));
        frame.setVisible(true);
    }
}
