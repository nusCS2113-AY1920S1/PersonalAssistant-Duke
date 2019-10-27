package entertainment.pro.logic.parsers.commands;


import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.storage.utils.HelpStorage;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.io.IOException;

/**
 * Help command class to handle help command functions.
 */
public class HelpCommand extends CommandSuper {
    public HelpCommand(Controller uicontroller) {
        super(COMMANDKEYS.help, CommandStructure.cmdStructure.get(COMMANDKEYS.help), uicontroller);
    }

    /**
     * Function to execute commands depending on the subroot command.
     * @throws IOException
     */
    @Override
    public void executeCommands() {
        //TODO Display help options
        ((MovieHandler) this.getUIController()).setFeedbackText(getHelp());
    }

    /**
     * Function to get help pertaining to each root command.
     * @throws IOException
     */
    private String getHelp() {
        return HelpStorage.getCmdHelp().get(getSubRootCommand());
    }


}
