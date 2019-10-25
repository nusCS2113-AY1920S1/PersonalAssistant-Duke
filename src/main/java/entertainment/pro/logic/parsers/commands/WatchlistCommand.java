package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.Deadline;
import entertainment.pro.model.Period;
import entertainment.pro.storage.user.WatchlistHandler;

public class WatchlistCommand  extends CommandSuper {

    public WatchlistCommand (Controller uicontroller) {
        super(COMMANDKEYS.watchlist, CommandStructure.cmdStructure.get(COMMANDKEYS.watchlist), uicontroller);
    }

    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
            case add:
                addToWatchList();
                break;
            case set:
                executeTaskDone();
                break;
            case delete:
                deleteFromWatchlist();
                break;
            default:
                break;
        }
    }

    /**
     * Add items to the watchlist.
     *
     */
    public void addToWatchList() {
        try{
            String movie = ((MovieHandler)this.getUIController()).getAPIRequester().beginAddRequest(getPayload());
            movie = movie.toLowerCase();
            String type = this.getFlagMap().get("-d").get(0);
            switch (type) {
                case "d":
                    String endDate = this.getFlagMap().get("-e").get(0);
                    Deadline deadline = new Deadline(movie, "D", endDate);
                    WatchlistHandler.add(deadline);
                    break;
                case "p":
                    String stDate = this.getFlagMap().get("-s").get(0);
                    String enDate = this.getFlagMap().get("-e").get(0);
                    Period period = new Period(movie, "P", stDate, enDate);
                    WatchlistHandler.add(period);
                    break;
                default:
                    break;
            }
            WatchlistHandler.print_list((MovieHandler)(this.getUIController()));
        } catch(NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUIController())).setFeedbackText("Please enter a valid command in the form of: \n" +
                    "watchlist add <name of movie> -d <type of task> -s <start date only for task> -e <end date for task>");
        }
    }

    /**
     * set the task on the watchlist as done
     * root: set
     * sub: watchlist
     * payload: none
     * flag: -d (index of the element in the watchlist to be marked as done)
     */
    private void executeTaskDone()  {
        try {
            String index = this.getFlagMap().get("-d").get(0);
            index = index.strip();
            int i = Integer.valueOf(index);
            System.out.println(i);
            WatchlistHandler.markAsDone(i, (MovieHandler)(this.getUIController()));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUIController())).setFeedbackText("please enter a valid task number");
        }
    }

    /**
     * removes an item from the watchlist
     */
    private void deleteFromWatchlist() {
        String mov = getPayload();
        System.out.println(mov);
        if (WatchlistHandler.removeFromWatchlist(mov, (MovieHandler)(this.getUIController()))) {
            ((MovieHandler) getUIController()).setFeedbackText("Successfully removed the movie from WatchList: " + mov);
        } else {
            ((MovieHandler) getUIController()).setFeedbackText("Such a movie does not exist in your WatchList. Check your spelling?");
        }
    }
}