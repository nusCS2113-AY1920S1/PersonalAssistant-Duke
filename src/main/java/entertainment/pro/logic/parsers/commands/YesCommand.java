package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.ui.Controller;


/**
 * Blacklist command class to handle blacklist command functions.
 */
public class YesCommand extends CommandSuper {
    public YesCommand(Controller uicontroller) {
        super(COMMANDKEYS.YES, CommandStructure.cmdStructure.get(COMMANDKEYS.YES), uicontroller);
    }

    @Override
    public void executeCommands() {
        //Do nothing
    }
}
