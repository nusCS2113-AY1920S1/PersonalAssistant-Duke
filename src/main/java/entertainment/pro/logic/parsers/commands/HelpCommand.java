package entertainment.pro.logic.parsers.commands;


import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.storage.utils.HelpStorage;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

/**
 * Help command class to handle help command functions.
 */
public class HelpCommand extends CommandSuper {
    public HelpCommand(Controller uicontroller) {
        super(COMMANDKEYS.HELP, CommandStructure.cmdStructure.get(COMMANDKEYS.HELP), uicontroller);
    }

    /**
     * Function to execute commands depending on the subroot command.
     */
    @Override
    public void executeCommands() {
        //TODO Display help options
        ((MovieHandler) this.getUiController()).setGeneralFeedbackText(getHelp());
    }

    /**
     * Function to get help pertaining to each root command.
     */
    private String getHelp() {
        return HelpStorage.getCmdHelp().get(getSubRootCommand());
    }


}
