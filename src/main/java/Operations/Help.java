package Operations;

import Enums.HelpMessage;
import Enums.TaskType;

public class Help {
    private Ui ui = new Ui();

    /**
     * constructor for help class.
     */
    public Help() {
    }

    /**
     * shows the help tips for the command specified by the keyword.
     * @param keyword the command the user wants tot seek help on
     */
    public void showHelp(String keyword) {
        try {
            ui.showHelpMessage(HelpMessage.valueOf(keyword.toUpperCase()));
        } catch (IllegalArgumentException e) {
            ui.showHelpMessage(HelpMessage.MENU);
        }
    }
    /**
     * shows all commands that can be used with help.
     */
    public void helpCommandList() {
        ui.showHelpMessage(HelpMessage.MENU);;
    }
}
