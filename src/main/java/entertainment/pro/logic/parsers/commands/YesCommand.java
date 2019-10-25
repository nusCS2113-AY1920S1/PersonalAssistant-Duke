package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

public class YesCommand extends CommandSuper {
    public YesCommand(Controller uicontroller) {
        super(COMMANDKEYS.yes, CommandStructure.cmdStructure.get(COMMANDKEYS.yes), uicontroller);
    }

    @Override
    public void executeCommands() {


    }
}