package compal.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class UiUtil {
    private ScrollPane mainWindow;
    private ScrollPane secondaryWindow;
    private TabPane tabWindow;

    private UiParts uiParts;

    /**
     * Constructs a new UiParts object.
     */
    public UiUtil() {
        mainWindow = UiParts.mainWindow;
        secondaryWindow = UiParts.secondaryWindow;
        tabWindow = UiParts.tabWindow;
    }

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's primary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printg(Object text) {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString()));
    }

    /**
     * Overloaded version of printg which allows you to customize style of text.
     * e.g usage; printg("hello world!", "verdana", 12, Color.RED);
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printg(Object text, String font, int size, Color color) {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString(), font, size, color));
    }

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's secondary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printSecondaryg(Object text, String font, int size, Color color) {
        VBox vbox = (VBox) secondaryWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel(text.toString(), font, size, color));
    }

    /**
     * Shows the number of tasks in the arraylist.
     * Used in TaskList.addTask.
     */
    public void showSize() {
        //printg("Now you have " + arrlist.size() + " tasks in the list");
    }


    //----------------------->

    //***MISC FUNCTIONS***----------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Returns a label (node) with the text as text.
     *
     * @param text Dialog text label received.
     * @return Label (Node) with the text as text.
     */
    private Label getDialogLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        label.setWrapText(true);
        return label;
    }

    /**
     * Returns a label (node) with the input text, font, fontsize and color.
     * Used when function is overloaded.
     *
     * @param text  Dialog text label received.
     * @param font  Font of text.
     * @param size  Fontsize of text.
     * @param color Color of text.
     * @return Label (Node) with the text as text.
     */
    private Label getDialogLabel(String text, String font, int size, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font(font, FontWeight.LIGHT, FontPosture.REGULAR, size));
        label.setTextFill(color);
        label.setWrapText(true);
        return label;
    }

    /**
     * Clears the display viewport on the GUI.
     * Parser calls this function when it receives a 'clear' command.
     */
    public void clearPrimary() {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().clear();
    }

    /**
     * Clears the secondary display viewport on the GUI.
     * Parser calls this function when it receives a 'clear' command.
     */
    public void clearSecondary() {
        VBox vbox = (VBox) secondaryWindow.getContent();
        vbox.getChildren().clear();
    }


}
