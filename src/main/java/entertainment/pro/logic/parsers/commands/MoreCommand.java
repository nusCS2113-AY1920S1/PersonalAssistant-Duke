package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

public class MoreCommand extends CommandSuper {
    public MoreCommand(Controller uicontroller) {
        super(COMMANDKEYS.more, CommandStructure.cmdStructure.get(COMMANDKEYS.more), uicontroller);
    }

    @Override
    public void executeCommands() {

    }


}