package duke.ui;

/**
 * This class is meant to override the Ui class for ease of testing.
 */
public class UiTest extends Ui {

    /**
     * This overrides the printMessage in Ui so that it is suited for testing.
     * @param msg The message to print.
     */
    @Override
    public void printMessage(String msg) {
        System.out.println("Ui.Test: printMessage has received: " + msg);
    }
}
