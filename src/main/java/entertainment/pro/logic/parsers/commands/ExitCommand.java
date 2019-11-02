package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandExceptions;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.io.IOException;

public class ExitCommand extends CommandSuper {
    public ExitCommand(Controller uicontroller) {
        super(COMMANDKEYS.exit, CommandStructure.cmdStructure.get(COMMANDKEYS.exit), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException, Exceptions {
            System.exit(0);
    }
}
