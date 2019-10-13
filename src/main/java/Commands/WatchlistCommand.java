package Commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import movieRequesterAPI.URLRetriever;
import task.Deadline;
import task.Tasks;
import task.Period;
import task.TaskList;
import parser.Parser;
import object.MovieInfoObject;
import java.util.ArrayList;

public class WatchlistCommand extends CommandSuper {
    public WatchlistCommand(Controller UIController) {
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
            case "D":
                String end_date = this.getFlagMap().get("-e").get(0);
                String concat = end_date + " " + this.getFlagMap().get("-e").get(1);
                Deadline deadline = new Deadline(movie, "D", concat);
                TaskList.addTask(deadline);
                break;
            case "P":
                String s_date = this.getFlagMap().get("-s").get(0);
                String s_time = this.getFlagMap().get("-s").get(1);
                String s_result = s_date + " " + s_time;
                String e_date = this.getFlagMap().get("-e").get(0);
                String e_time = this.getFlagMap().get("-e").get(1);
                String e_result = e_date + " " + e_time;
                Period period = new Period(movie, "P", s_result, e_result);
                TaskList.addTask(period);
                break;
        }
        ((MovieHandler)this.getUIController()).setFeedbackText("");
        Parser.listCommand();
    }
}
