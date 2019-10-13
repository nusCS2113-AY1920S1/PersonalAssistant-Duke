package Commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import task.Deadline;
import task.Period;
import ListCommands.WatchlistHandler;

public class AddCommand extends CommandSuper {
    public AddCommand(Controller UIController) {
        super(COMMAND_KEYS.add, CommandStructure.cmdStructure.get(COMMAND_KEYS.add), UIController);
    }


    @Override
    public void executeCommands() {
        switch(this.getSubRootCommand()) {
            case watchlist:
                addToWatchList();
                break;
            default:
                break;
        }
    }

    public void addToWatchList() {
        String movie = ((MovieHandler)this.getUIController()).getAPIRequester().beginAddRequest(getPayload());
        String type = this.getFlagMap().get("-d").get(0);
        switch (type) {
            case " d ":
                String end_date = this.getFlagMap().get("-e").get(0);
                Deadline deadline = new Deadline(movie, "D", end_date);
                WatchlistHandler.add(deadline);
                break;
            case " p ":
                String s_date = this.getFlagMap().get("-s").get(0);
                String e_date = this.getFlagMap().get("-e").get(0);
                Period period = new Period(movie, "P", s_date, e_date);
                WatchlistHandler.add(period);
                break;
        }
        WatchlistHandler.print_list((MovieHandler)(this.getUIController()));
    }
}
