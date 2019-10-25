package entertainment.pro.logic.parsers.commands;

import entertainment.pro.storage.utils.HelpStorage;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

public class HelpCommand extends CommandSuper {
    public HelpCommand(Controller uicontroller) {
        super(COMMANDKEYS.help, CommandStructure.cmdStructure.get(COMMANDKEYS.help), uicontroller);
    }

    @Override
    public void executeCommands() {
        //TODO Display help options
        ((MovieHandler) this.getUIController()).setFeedbackText(getHelp());
    }

    private String getHelp() {
        return HelpStorage.getCmdHelp().get(getSubRootCommand());
    }


}