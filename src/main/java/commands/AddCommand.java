package commands;

import Contexts.SearchResultContext;
import EPstorage.Blacklist;
import EPstorage.BlacklistStorage;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import task.Deadline;
import task.Period;
import ListCommands.WatchlistHandler;


public class AddCommand extends CommandSuper {

    public AddCommand(Controller uicontroller) {
        super(COMMANDKEYS.add, CommandStructure.cmdStructure.get(COMMANDKEYS.add), uicontroller);
    }

    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
        case watchlist:
            addToWatchList();
            break;
        case blacklist:
            addToBlackList();
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
        try {
            String movie = ((MovieHandler)this.getUIController()).getAPIRequester().beginAddRequest(getPayload());
            movie = movie.toLowerCase();
            String type = this.getFlagMap().get("-d").get(0);
            switch (type) {
                case " d ":
                    String endDate = this.getFlagMap().get("-e").get(0);
                    Deadline deadline = new Deadline(movie, "D", endDate);
                    WatchlistHandler.add(deadline);
                    break;
                case " p ":
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
                    "add watchlist <name of movie> -d <type of task> -s <start date only for task> -e <end date for task>");
        }
    }

    /**
     * Check if payload is an integer.
     *
     * @param radix the chosen radix.
     * @param s string payload.
     */
    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i) , radix) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add items to the blacklist.
     *
     */
    public void addToBlackList() {
        System.out.print("HERE!!");
        String movie = getPayload().trim();

        if (getFlagMap().get("-k") != null) {
            System.out.print("ADDing keyword");
            if (isInteger(movie, 10)) {
                Blacklist.addToBlacklistKeyWord(SearchResultContext.getIndex(Integer.parseInt(movie)).getTitle());
            } else {
                Blacklist.addToBlacklistKeyWord(movie);
            }
        } else {
            System.out.print("ADDing movie");
            if (isInteger(movie, 10)) {
                Blacklist.addToBlacklistMoviesID(SearchResultContext.getIndex(Integer.parseInt(movie)));
            } else {
                Blacklist.addToBlacklistMovie(movie);
            }

        }



        ((MovieHandler) this.getUIController()).setFeedbackText(Blacklist.printList());

    }


}
