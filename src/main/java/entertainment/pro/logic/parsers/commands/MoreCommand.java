package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

//TODO REMOVE THIS CLASS
public class MoreCommand extends CommandSuper {
    public MoreCommand(Controller uicontroller) {
        super(COMMANDKEYS.MORE, CommandStructure.cmdStructure.get(COMMANDKEYS.MORE), uicontroller);
    }

    @Override
    public void executeCommands() {

    }


}