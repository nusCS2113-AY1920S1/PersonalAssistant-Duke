package commands;

import MovieUI.Controller;

public class YesCommand extends CommandSuper {
    public YesCommand(Controller UIController) {
        super(COMMANDKEYS.yes, CommandStructure.cmdStructure.get(COMMANDKEYS.yes), UIController);
    }

    @Override
    public void executeCommands() {


    }
}
