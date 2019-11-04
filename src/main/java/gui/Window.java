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
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import common.LoggerController;
import core.Duke;
import logic.LogicController;
import logic.command.CommandOutput;
import model.Member;
import model.Task;
import common.DukeException;
import gui.PieChart;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

//@@author AugGust
public class Window {
    public boolean initialized = false;

    private boolean isShowingTasks = true;

    private JFrame frame;
    private JTextField inputField;
    private TasksCounter tasksCounter;
    private JLabel viewLabel;

    public static Window instance;
    public JTextArea outputArea;
    public JTextArea taskViewArea;

    private JTextField completedPercField;
    private PieChart pieChart;
    protected LogicController logicController;

    /**
     * Create the Window
     */
    public Window(TasksCounter tc, LogicController logicController) {
        Window.instance = this;
        this.logicController = logicController;
        this.tasksCounter = tc;
        initialize();
        this.showTaskView(true);
        this.frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Team Manager");
        frame.getContentPane().setBackground(new Color(120, 168, 219));
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(22, 284, 363, 264);
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel.setBackground(SystemColor.window);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(10, 10, 343, 244);
        panel.add(scrollPane);

        JTextArea outputArea = new JTextArea();
        scrollPane.setViewportView(outputArea);
        outputArea.setText("Welcome to Team Manager!");
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        outputArea.setWrapStyleWord(true);
        outputArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
        this.outputArea = outputArea;
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

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel2.setBackground(Color.WHITE);
        panel2.setBounds(106, 234, 197, 40);
        frame.getContentPane().add(panel2);

        completedPercField = new JTextField();
        completedPercField.setText("" + (int) tasksCounter.getPercCompleted() + "% of tasks complete");
        completedPercField.setHorizontalAlignment(SwingConstants.CENTER);
        completedPercField.setFont(new Font("Constantia", Font.PLAIN, 15));
        completedPercField.setColumns(10);
        completedPercField.setBorder(BorderFactory.createEmptyBorder());
        completedPercField.setBounds(10, 10, 177, 19);
        panel2.add(completedPercField);

        JPanel piePanel = new JPanel();
        piePanel.setLocation(106, 24);
        piePanel.setBackground(new Color(120, 168, 219));
        piePanel.setLayout(null);
        piePanel.setSize(200, 200);

        frame.getContentPane().add(piePanel);
        pieChart = new PieChart(tasksCounter.getPercCompleted());
        pieChart.setBounds(0, 0, 200, 200);
        piePanel.add(pieChart);

        JPanel panel3 = new JPanel();
        panel3.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
        panel3.setBackground(Color.WHITE);
        panel3.setBounds(408, 87, 363, 399);
        frame.getContentPane().add(panel3);
        panel3.setLayout(null);

        JScrollPane taskScrollPane = new JScrollPane();
        taskScrollPane.setBounds(10, 10, 343, 379);
        taskScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        taskScrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel3.add(taskScrollPane);

        taskViewArea = new JTextArea();
        taskViewArea.setEditable(false);
        taskViewArea.setFont(new Font("Dialog", Font.PLAIN, 15));
        taskViewArea.setLineWrap(true);
        taskViewArea.setWrapStyleWord(true);
        taskScrollPane.setViewportView(taskViewArea);

        viewLabel = new JLabel("Tasks");
        viewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        viewLabel.setFont(new Font("Constantia", Font.BOLD, 20));
        viewLabel.setBounds(408, 34, 363, 53);
        frame.getContentPane().add(viewLabel);

        InputMemory im = new InputMemory();

        Action enterPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    executeCommand(inputField.getText());
                } catch (DukeException error) {
                    setOutputArea(error.getMessage());
                }
                im.addToHistory(inputField.getText());
                inputField.setText("");
            }
        };
        inputField.addActionListener(enterPressed);

        Action upPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText(im.moveUp(inputField.getText()));
            }
        };
        String key = "UP";
        KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
        inputField.getInputMap().put(keyStroke, key);
        inputField.getActionMap().put(key, upPressed);

        key = "DOWN";
        keyStroke = KeyStroke.getKeyStroke(key);
        inputField.getInputMap().put(keyStroke, key);

        Action downPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText(im.moveDown(inputField.getText()));
            }
        };
        inputField.getActionMap().put(key, downPressed);

        initialized = true;
    }

    /**
     * Updates the percentage displayed on the window
     */
    public void updatePercentage() {
        completedPercField.setText("" + (int) tasksCounter.getPercCompleted() + "% of tasks complete");
        pieChart.setPercentage(tasksCounter.getPercCompleted());
    }

    /**
     * Sets output area to desired text
     */
    public void setOutputArea(String outputString) {
        outputArea.setText(outputArea.getText() + "\n\n" + outputString);
    }

    /**
     * Sets the text in the task view area to the provided string
     *
     * @param outputString String to be displayed
     */
    private void setTaskViewArea(String outputString) {
        taskViewArea.setText(outputString);
    }

    /**
     * Formats list of tasks to display on the task view area
     *
     * @param tasks List of tasks to view
     * @return Formatted output
     */
    private String formatTasksView(ArrayList<Task> tasks) {
        String output = "";
        for (int i = 0; i < tasks.size(); i++) {
            output += (i + 1) + ". " + tasks.get(i) + '\n';
            //================ For displaying skill requirement list ==============================
            ArrayList<String> skillList = tasks.get(i).getSkillReqList();
            if (skillList != null && skillList.size()!=0) {
                output += "     required Skill List: \n";
            }
            for (int k = 0; k < skillList.size(); k += 1) {
                output += "           \u27a3 " + skillList.get(k) + '\n';
            }
            //================ For displaying members that are linked to ths task =========================
            ArrayList<String> members = tasks.get(i).getMemberList();
            if (members.size() != 0) {
                output += "     Assigned members: \n";
            }
            for (int j = 0; j < members.size(); j++) {
                output += "           \u27a5 " + members.get(j) + '\n';
            }
            output += '\n';
        }
        return output;
    }

    /**
     * Formats list of members to display on the task view area
     *
     * @param members      List of members to view
     * @param fullTaskList Full list of tasks (assigned or not)
     * @return Formatted output
     */
    private String formatMembersView(ArrayList<Member> members, ArrayList<Task> fullTaskList) {
        String output = "";

        ArrayList<String> fullStrTaskList = new ArrayList<String>();
        for (int i = 0; i < fullTaskList.size(); i++) {
            fullStrTaskList.add(fullTaskList.get(i).getName());
        }

        ArrayList<Integer> assignedTasks = new ArrayList<Integer>();

        for (int i = 0; i < members.size(); i++) {
            output += members.get(i).getName() + '\n';
            ArrayList<String> tasks = members.get(i).getTaskList();

            //================ For displaying skill requirement list ==============================
            ArrayList<String> skillList = members.get(i).getSkillList();
            if (skillList != null && skillList.size()!=0) {
                output += "     Skills: \n";
            }
            for (int k = 0; k < skillList.size(); k += 1) {
                output += "    \u27a3 " + skillList.get(k) + '\n';
            }

            //=============== For displaying Assigned Tasks ==================================
            if (tasks.size() != 0) {
                output += "\n     Assigned tasks: \n";
            }
            for (int j = 0; j < tasks.size(); j++) {
                output += "    \u27a5 ";

                int indexOfTask = fullStrTaskList.indexOf(tasks.get(j));

                output += "Task " + (indexOfTask + 1) + ": " + fullTaskList.get(indexOfTask) + '\n';
                if (!assignedTasks.contains(indexOfTask)) {
                    assignedTasks.add(indexOfTask);
                }
            }

            output += "\n";
        }

        if (assignedTasks.size() != fullTaskList.size()) {
            output += "Unassigned Tasks\n";
            for (int i = 0; i < fullTaskList.size(); i++) {
                if (!assignedTasks.contains(i)) {
                    output += "    \u27a5 Task " + (i + 1) + ": ";
                    output += fullTaskList.get(i).toString() + '\n';
                }
            }
        }
        return output;
    }

    /**
     * Changes view to Tasks or Members mode
     *
     * @param isTaskView chooses Task mode is true, members if false
     */
    public void showTaskView(boolean isTaskView) {
        isShowingTasks = isTaskView;
        if (isShowingTasks) {
            setTaskViewArea(formatTasksView(logicController.model.getTaskList()));
            viewLabel.setText("Tasks");
        } else {
            setTaskViewArea(
                    formatMembersView(logicController.model.getMemberList(), logicController.model.getTaskList()));
            viewLabel.setText("Members");
        }
    }

    /**
     * Updates the command text box to show results from commands
     */
    public void executeCommand(String fullCommandText) throws DukeException {
        CommandOutput commandOutput = logicController.execute(fullCommandText);
        setOutputArea(commandOutput.getOutputToUser());
        if (isShowingTasks) {
            setTaskViewArea(formatTasksView(logicController.model.getTaskList()));
        } else {
            setTaskViewArea(
                    formatMembersView(logicController.model.getMemberList(), logicController.model.getTaskList()));
        }
        updatePercentage();
        if (commandOutput.isExit()) {
            System.exit(0);
        }
    }
}
