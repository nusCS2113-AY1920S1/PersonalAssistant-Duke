package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.ui.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for exiting the program.
 */
public class ExitCommand extends CommandSuper {
    private static final Logger logger = Logger.getLogger(ExitCommand.class.getName());

    /**
     * Constructor for Command Super class.
     * @param uicontroller Controller class.
     */
    public ExitCommand(Controller uicontroller) {
        super(CommandKeys.EXIT, CommandStructure.cmdStructure.get(CommandKeys.EXIT), uicontroller);
    }

    /**
     * Responsible for exiting from the app.
     */
    @Override
    public void executeCommands() {
        logger.log(Level.INFO, PromptMessages.EXIT);
        System.exit(0);
    }
}
