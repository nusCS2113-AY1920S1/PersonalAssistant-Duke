package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.ui.Controller;

import java.io.IOException;

/**
 * This class is responsible for exiting the program.
 */
public class ExitCommand extends CommandSuper {

    /**
     * Constructor for Command Super class.
     * @param uicontroller Controller class.
     */
    public ExitCommand(Controller uicontroller) {
        super(COMMANDKEYS.exit, CommandStructure.cmdStructure.get(COMMANDKEYS.exit), uicontroller);
    }

    /**
     * Responsible for exiting from the app.
     */
    @Override
    public void executeCommands() {
        System.exit(0);
    }
}
