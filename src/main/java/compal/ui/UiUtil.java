package compal.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


/**
 * Represents userinterface.
 */
public class UiUtil {
    public static ScrollPane mainWindow;
    public static TabPane tabWindow;

    public void setMainWindow(ScrollPane mainWindow) {
        UiUtil.mainWindow = mainWindow;
    }

    public void setTabWindow(TabPane tabWindow) {
        UiUtil.tabWindow = tabWindow;
    }

    //@@author jaedonkey

    /**
     * Converts the object into string form using toString()
     * and prints it onto the GUI's primary display box.
     *
     * @param text Input object received to be print on gui. Any object type can be used, as long as
     *             it has a 'toString()' function defined.
     */
    public void printg(Object text) {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().addAll(getDialogLabel("COMPal says:\n" + text.toString()));
    }

    //@@author jaedonkey

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

    //@@author jaedonkey

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

    //@@author jaedonkey

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

    //@@author jaedonkey

    /**
     * Clears the display viewport on the GUI.
     * Parser calls this function when it receives a 'clear' command.
     */
    public void clearPrimary() {
        VBox vbox = (VBox) mainWindow.getContent();
        vbox.getChildren().clear();
    }

}


