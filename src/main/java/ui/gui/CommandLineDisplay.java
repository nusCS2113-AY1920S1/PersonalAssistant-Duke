package ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommandLineDisplay {
    @FXML
    AnchorPane cliDisplayRoot;
    @FXML
    ScrollPane cliDisplay;
    @FXML
    VBox container;

    private static final String LINE = "________________________________________________";

    /**
     * Prints string to CLI Display.
     * @param outputStr String to be printed onto the display
     */
    void printToDisplay(String outputStr) {
        Text newOutput = new Text(outputStr);
        container.getChildren().add(newOutput);
    }

    void setStyle() {
        this.cliDisplay.vvalueProperty().bind(container.heightProperty());
    }

    /**
     * Helper method to indicate duke is saying something.
     * @param string The message duke wants to say
     */
    void dukeSays(String string) {
        printToDisplay("Duke: " + string + "\n");
    }

    /**
     * Helper method to print Line Separator.
     */
    void printSeparator() {
        this.printToDisplay(LINE);
    }

    /**
     * Clears the current CLI display.
     */
    void clearCliDisplay() {
        this.container.getChildren().clear();
    }
}
