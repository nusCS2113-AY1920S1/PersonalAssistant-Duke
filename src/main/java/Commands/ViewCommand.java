package Commands;

import MovieUI.Controller;
import object.MovieInfoObject;

import java.util.ArrayList;

public class ViewCommand extends CommandSuper {
    public ViewCommand(Controller UIController) {
        super(COMMAND_KEYS.view, CommandStructure.cmdStructure.get(COMMAND_KEYS.view), UIController);
    }

    @Override
    public void executeCommands() {

    }


}
