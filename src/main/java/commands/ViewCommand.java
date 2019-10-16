package commands;

import EPstorage.Blacklist;
import ListCommands.WatchlistHandler;
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
                WatchlistHandler.print_list((MovieHandler)(this.getUIController()));
                break;
            case blacklist:
                ((MovieHandler) this.getUIController()).setFeedbackText(Blacklist.printList());
                break;
            default:
                break;
        }
    }


}