package wallet.logic.command;

import wallet.model.Wallet;
import wallet.ui.HelpPrompt;
import wallet.storage.HelpStorage;

/**
 * The HelpCommand Class shows users what the valid commands are.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    /**
     * Shows a list of valid commands to the user and returns false.
     *
     * @param wallet The Wallet object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {

        HelpPrompt helpData = new HelpPrompt();
        HelpStorage accessSections = new HelpStorage();

        int selectedSection = helpData.prompt();
        if (selectedSection > 0) {
            accessSections.sectionData(selectedSection);
        }
        return false;
    }
}
