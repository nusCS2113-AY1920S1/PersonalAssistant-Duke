package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.Deadline;
import entertainment.pro.model.Period;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.MovieHandler;

public class WatchlistCommand  extends CommandSuper {

    public WatchlistCommand(Controller uicontroller) {
        super(CommandKeys.WATCHLIST, CommandStructure.cmdStructure.get(CommandKeys.WATCHLIST), uicontroller);
    }

    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
        case ADD:
            try {
                addToWatchList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case SET:
            if (getPayload().isEmpty()) {
                executeIndexTaskDone();
            } else {
                executeNameTaskDone();
            }
            break;
        case DELETE:
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
    private void addToWatchList() throws Exception {
        try {
            String movie = ((MovieHandler) this.getUiController()).getAPIRequester().beginAddRequest(getPayload());
            if (movie.equals("")) {
                ((MovieHandler) (this.getUiController())).setGeneralFeedbackText("Movie not found.\n"
                        + "Please check your spelling");
                return;
            }
            movie = movie.toLowerCase();
            String type = this.getFlagMap().get("-t").get(0);
            switch (type) {
            case "d":
                addDeadlineTask(movie);
                break;
            case "p":
                addPeriodTask(movie);
                break;
            default:
                break;
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUiController())).setGeneralFeedbackText("Please enter a valid command in the form of: \n"
                    + "watchlist add <name of movie> -d <type of task> -s <start date only for task> "
                    + "-e <end date for task>");
        }
    }

    /**
     * adds a deadline task to my watchlist.
     * @param movie name of the movie title to be added to the watchlist
     */
    private void addDeadlineTask(String movie) throws IndexOutOfBoundsException {
        String endDate = this.getFlagMap().get("-e").get(0);
        Deadline deadline = new Deadline(movie, "D", endDate);
        if (!WatchlistHandler.add(deadline)) {
            ((MovieHandler) this.getUiController()).clearSearchTextField();
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText("No duplicates allowed");
        } else {
            WatchlistHandler.print_list(( MovieHandler ) (this.getUiController()));
        }
    }

    /**
     * adds a period task to my watchlist.
     * @param movie name of the movie title to be added to the watchlist
     */
    private void addPeriodTask(String movie) throws IndexOutOfBoundsException {
        String stDate = this.getFlagMap().get("-s").get(0);
        String enDate = this.getFlagMap().get("-e").get(0);
        Period period = new Period(movie, "P", stDate, enDate);
        if (!WatchlistHandler.add(period)) {
            ((MovieHandler)this.getUiController()).clearSearchTextField();
            ((MovieHandler)this.getUiController()).setGeneralFeedbackText("No duplicates allowed");
        } else {
            WatchlistHandler.print_list((MovieHandler)(this.getUiController()));
        }
    }

    /**
     * set the task on the watchlist as done.
     * root: set
     * sub: watchlist
     * payload: none
     * flag: -d (index of the element in the watchlist to be marked as done)
     */
    private void executeIndexTaskDone()  {
        try {
            String index = this.getFlagMap().get("-i").get(0);
            index = index.strip();
            int i = Integer.valueOf(index);
            System.out.println(i);
            WatchlistHandler.markIndexAsDone(i, (MovieHandler)(this.getUiController()));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUiController())).setGeneralFeedbackText("please enter a valid task number");
        }
    }

    /**
     * removes a movie of a certain name from the watchlist.
     */
    private void executeNameTaskDone() {
        String movie = getPayload();
        if (!WatchlistHandler.markMovieAsDone(movie, (MovieHandler)(this.getUiController()))) {
            ((MovieHandler)(this.getUiController())).setGeneralFeedbackText("please enter a movie in the watchlist");
        }
    }

    /**
     * removes an item from the watchlist.
     */
    private void deleteFromWatchlist() {
        String mov = getPayload();
        System.out.println(mov);
        if (WatchlistHandler.removeFromWatchlist(mov, (MovieHandler)(this.getUiController()))) {
            ((MovieHandler) getUiController()).setGeneralFeedbackText("Successfully removed the movie from WatchList: "
                    + mov);
        } else {
            ((MovieHandler) getUiController())
                    .setGeneralFeedbackText("Such a movie does not exist in your WatchList. Check your spelling?");
        }
    }
}