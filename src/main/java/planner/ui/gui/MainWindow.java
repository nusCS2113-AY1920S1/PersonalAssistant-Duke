//@@author LongLeCE

package planner.ui.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import planner.main.CliLauncher;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private CliLauncher planner;
    private LinkedBlockingQueue<Character> inputQueue;
    private LinkedBlockingQueue<Character> outputHold;
    private PrintStream output;
    private InputStream input;

    private static final Image userImage = new Image(MainWindow.class.getResourceAsStream("/images/DaUser.png"));
    private static final Image planImage = new Image(MainWindow.class.getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        this.setupStreams();
    }

    public InputStream getInput() {
        return input;
    }

    public PrintStream getOutput() {
        return output;
    }

    /**
     * Initialize Planner.
     * @param p given planner
     */
    public void setPlanner(CliLauncher p) {
        this.planner = p;
        this.inputQueue = new LinkedBlockingQueue<>();
        this.outputHold = new LinkedBlockingQueue<>();
    }

    private void setupStreams() {
        input = new InputStream() {
            @Override
            public int read() {
                try {
                    return inputQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        };
        output = new PrintStream(new OutputStream() {
            @Override
            public void write(int i) {
                outputHold.add((char) i);
            }
        });
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        addUserDialog(input);
        for (char c: input.toCharArray()) {
            inputQueue.add(c);
        }
        inputQueue.add('\n');
    }

    private String read(LinkedBlockingQueue<Character> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        StringBuilder line = new StringBuilder();
        for (char c = queue.poll(); !queue.isEmpty(); c = queue.poll()) {
            line.append(c);
        }
        return line.toString();
    }

    private void exit() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * Display ModPlan's output.
     */
    public void addModPlanDialog() {
        addModPlanDialog(read(outputHold));
        if (!planner.isAlive()) {
            exit();
        }
    }

    /**
     * Display ModPlan's output.
     * @param text to be displayed
     */
    public void addModPlanDialog(String text) {
        Platform.runLater(() -> {
            if (text != null && !text.isBlank()) {
                dialogContainer.getChildren().add(DialogBox.getModPlanDialog(text, planImage));
            }
        });
    }

    /**
     * Display user's input.
     * @param text to be displayed
     */
    public void addUserDialog(String text) {
        Platform.runLater(() -> {
            if (text != null && !text.isBlank()) {
                dialogContainer.getChildren().add(DialogBox.getUserDialog(text, userImage));
            }
        });
    }
}