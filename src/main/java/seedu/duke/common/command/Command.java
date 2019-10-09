package seedu.duke.common.command;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * This is an abstract command that all kinds of command inherit from. It has execute() function to be
 * overridden and can be set silent for UI output.
 */
public abstract class Command {
    /**
     * A flag whether UI output is turned off. Default to be false, so UI output is default to be turned on.
     */
    protected boolean silent = false;
    protected String responseMsg = "";
    protected ArrayList<Option> optionList = new ArrayList<>();

    /**
     * Executes the command. This method is to be overridden to specific functionality by different types of
     * command themselves.
     *
     * @return a flag whether the command is successfully executed.
     */
    public abstract boolean execute();

    /**
     * This function turns off the UI output of the command.
     */
    public void setSilent() {
        this.silent = true;
    }

    public static class Option extends Pair<String, String> {
        /**
         * Creates a new pair as option of the input
         *
         * @param key   The key for this pair
         * @param value The value to use for this pair
         */
        public Option(String key, String value) {
            super(key, value);
        }
    }
}
