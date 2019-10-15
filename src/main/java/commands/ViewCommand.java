package commands;

import EPstorage.Blacklist;
import MovieUI.Controller;
import MovieUI.MovieHandler;

public class ViewCommand extends CommandSuper {
    public ViewCommand(Controller uicontroller) {
        super(COMMANDKEYS.view, CommandStructure.cmdStructure.get(COMMANDKEYS.view), uicontroller);
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