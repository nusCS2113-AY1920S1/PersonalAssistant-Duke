package javafx;

import javafx.beans.property.SimpleStringProperty;


/**
 * javafx class to display help in a tabular format in the GUI.
 * Each HelpFX object will display its command and the description of its command.
 * The description will contain the various explanations and possible inputs.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class HelpFX {
    private final SimpleStringProperty command = new SimpleStringProperty("");
    private final SimpleStringProperty description = new SimpleStringProperty("");

    /**
     * Empty constructor.
     */
    public HelpFX() {
        this("", "");
    }

    /**
     * Constructor for ths HelpFX object.
     *
     * @param command The command that a user can input.
     * @param description Various explanations on how to use that command.
     */
    public HelpFX(String command, String description) {
        setCommand(command);
        setDescription(description);
    }

    /**
     * Returns the command.
     * Required to be used by helpView.
     *
     * @return A possible command that a user can input.
     */
    public String getCommand() {
        return command.get();
    }

    /**
     * Sets the command of this HelpFX object.
     *
     * @param input The command of this HelpFX object.
     */
    public void setCommand(String input) {
        command.set(input);
    }

    /**
     * Returns the Description of this HelpFX object's command.
     * Required to be used by helpView.
     *
     * @return The description of this object as a string.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Sets the description of this HelpFX object's command.
     *
     * @param input The description of this object.
     */
    public void setDescription(String input) {
        description.set(input);
    }


}
