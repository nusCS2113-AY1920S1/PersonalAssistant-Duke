package gui;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import core.Duke;
import utils.TasksCounter;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Window {

    private JFrame frame;
    private JTextField inputField;
    private TasksCounter tasksCounter;

    public static JTextArea outputArea;
    private JTextField completedPercField;

    /**
     * Create the application.
     */
    public Window(TasksCounter tc) {
    	this.tasksCounter = tc;
        initialize();
        this.frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(120, 168, 219));
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(22, 357, 363, 191);
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel.setBackground(SystemColor.window);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(10, 10, 343, 171);
        panel.add(scrollPane);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        scrollPane.setViewportView(outputArea);
        outputArea.setFont(new Font("Constantia", Font.PLAIN, 15));
        outputArea.setWrapStyleWord(true);
        outputArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel1.setBackground(Color.WHITE);
        panel1.setBounds(408, 508, 363, 40);
        frame.getContentPane().add(panel1);

        inputField = new JTextField();
        inputField.setFont(new Font("Constantia", Font.PLAIN, 15));
        inputField.setBounds(10, 10, 343, 19);
        panel1.add(inputField);
        inputField.setColumns(10);
        inputField.setBorder(BorderFactory.createEmptyBorder());
        frame.setBounds(100, 100, 800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                inputField.requestFocus();
            }
        });
        Window.outputArea = outputArea;
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel2.setBackground(Color.WHITE);
        panel2.setBounds(106, 307, 197, 40);
        frame.getContentPane().add(panel2);
        
        completedPercField = new JTextField();
        completedPercField.setText("" + tasksCounter.getPercCompleted() + "% of tasks complete");
        completedPercField.setHorizontalAlignment(SwingConstants.CENTER);
        completedPercField.setFont(new Font("Constantia", Font.PLAIN, 15));
        completedPercField.setColumns(10);
        completedPercField.setBorder(BorderFactory.createEmptyBorder());
        completedPercField.setBounds(10, 10, 177, 19);
        panel2.add(completedPercField);

        Action enterPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Duke.processCommand(inputField.getText());
                inputField.setText("");
            }
        };
        inputField.addActionListener(enterPressed);
    }
}
