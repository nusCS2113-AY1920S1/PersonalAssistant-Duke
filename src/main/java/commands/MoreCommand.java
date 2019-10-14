package commands;

import MovieUI.Controller;

public class MoreCommand extends CommandSuper {
    public MoreCommand(Controller UIController) {
        super(COMMANDKEYS.more, CommandStructure.cmdStructure.get(COMMANDKEYS.more), UIController);
    }

    @Override
    public void executeCommands() {

    }


}