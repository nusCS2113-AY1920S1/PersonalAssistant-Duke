package entertainment.pro.logic.parsers.commands;

import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.Deadline;
import entertainment.pro.model.Period;
import entertainment.pro.storage.user.WatchlistHandler;


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
//                addToBlackList();
                break;
            default:
                break;
        }
    }

    /**
     * Add items to the watchlist.
     */
    public void addToWatchList() {
        try {
            String movie = ((MovieHandler) this.getUIController()).getAPIRequester().beginAddRequest(getPayload());
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
            WatchlistHandler.print_list((MovieHandler) (this.getUIController()));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler) (this.getUIController())).setFeedbackText("Please enter a valid command in the form of: \n" +
                    "add watchlist <name of movie> -d <type of duke.task> -s <start date only for duke.task> -e <end date for duke.task>");
        }
    }
}