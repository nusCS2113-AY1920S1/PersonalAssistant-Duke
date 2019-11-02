package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class ViewCommand extends CommandSuper {
    private Controller controller;

    public ViewCommand(Controller uicontroller) {
        super(COMMANDKEYS.view, CommandStructure.cmdStructure.get(COMMANDKEYS.view), uicontroller);
    }

    @Override
    public void executeCommands() throws Exceptions {
        switch (this.getSubRootCommand()) {
            case watchlist:
                WatchlistHandler.print_list((MovieHandler) (this.getUiController()));
                break;
            case blacklist:
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(Blacklist.printList());
                break;
            case entry:
                executeEntryCommands(Integer.parseInt(getPayload()));
                break;
            default:
                break;
        }
    }

    /**
     * Responisible for displaying more information about a movie/TV show.
     * Called when user is viewing list of movies/TV shows from a search request and want to know more information.
     *
     * @param num The number of the movie or TV show in the list indicated below the title.
     * @throws Exceptions
     */
    private void executeEntryCommands(int num) throws Exceptions {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        //String payload = getPayload();
        //int num = Integer.parseInt(payload);
        //System.out.println("this is num +" + num);
        ((MovieHandler) this.getUiController()).showMovie(num);
    }

}

