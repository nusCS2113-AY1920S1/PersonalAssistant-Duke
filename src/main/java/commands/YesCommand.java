package commands;

import MovieUI.Controller;

public class YesCommand extends CommandSuper {
    public YesCommand(Controller uicontroller) {
        super(COMMANDKEYS.yes, CommandStructure.cmdStructure.get(COMMANDKEYS.yes), uicontroller);
    }

    @Override
    public void executeCommands() {


    }
}