package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.HelpStorage;

import java.util.ArrayList;

/**
 * The HelpCommand Class shows users what the valid commands are.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";
    private String input;
    private static String MESSAGE_ERROR_INDEX = "Error! Input not a valid help section index";

    public HelpCommand(String input) {
        this.input = input;
    }


    /**
     * Shows a list of valid commands to the user and returns false.
     *
     * @param wallet The Wallet object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {
        //@@author Xdecosee
        int chosenIndex;
        try {
            chosenIndex = Integer.parseInt(input);
            if (chosenIndex <= 0 || chosenIndex > wallet.getHelpList().size()) {
                System.out.println(MESSAGE_ERROR_INDEX);
                return false;
            }
            ArrayList<String> sectionData = wallet.getHelpList().get(chosenIndex - 1).getSectionData();
            for (String s : sectionData) {
                System.out.println(s);
            }
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_INDEX);
        }
        return false;
        //@@author
    }
}
