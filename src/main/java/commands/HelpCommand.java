package commands;

import EPstorage.HelpStorage;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.util.Map;

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