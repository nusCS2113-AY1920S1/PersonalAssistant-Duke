package Commands;

import MovieUI.Controller;
import object.MovieInfoObject;

import java.util.ArrayList;

public class MoreCommand extends CommandSuper{
    public MoreCommand(Controller UIController) {
        super(COMMAND_KEYS.more, CommandStructure.cmdStructure.get(COMMAND_KEYS.more) ,UIController);
    }

    @Override
    public void executeCommands() {

    }


}