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
        Deadline deadline = new Deadline(movie, "D", "20/09/1997 12:00");
        System.out.println(movie);
        TaskList.addTask(deadline);
        Parser.listCommand();
    }
}
