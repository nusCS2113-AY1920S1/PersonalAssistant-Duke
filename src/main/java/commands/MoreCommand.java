package commands;

import MovieUI.Controller;

public class MoreCommand extends CommandSuper {
    public MoreCommand(Controller uicontroller) {
        super(COMMANDKEYS.more, CommandStructure.cmdStructure.get(COMMANDKEYS.more), uicontroller);
    }

    @Override
    public void executeCommands() {

    }


}