package Commands;

import Execution.CommandStack;
import MovieUI.Controller;

public class YesCommand extends CommandSuper {
    public YesCommand(Controller UIController) {
        super(COMMAND_KEYS.yes, CommandStructure.cmdStructure.get(COMMAND_KEYS.yes), UIController);
    }

    @Override
    public void executeCommands() {


    }
}
