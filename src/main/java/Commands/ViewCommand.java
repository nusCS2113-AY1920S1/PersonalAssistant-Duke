package Commands;

import EPstorage.Blacklist;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import object.MovieInfoObject;

import java.util.ArrayList;

public class ViewCommand extends CommandSuper {
    public ViewCommand(Controller UIController) {
        super(COMMAND_KEYS.view, CommandStructure.cmdStructure.get(COMMAND_KEYS.view), UIController);
    }

    @Override
    public void executeCommands() {
        switch(this.getSubRootCommand()) {
            case watchlist:

                break;
            case blacklist:
                ((MovieHandler) this.getUIController()).setFeedbackText(Blacklist.printList());

                break;
            default:
                break;
        }
    }


}
