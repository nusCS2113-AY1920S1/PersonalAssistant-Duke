package Commands;

import Contexts.SearchResultContext;
import EPstorage.Blacklist;
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
            case blacklist:
                addToBlackList();
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

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    public void addToBlackList() {
        System.out.print("HERE!!");
        String movie = getPayload().trim();
        if(isInteger(movie,10)){
            Blacklist.addToBlacklist(SearchResultContext.getIndex(Integer.parseInt(movie)));
        }else{
            Blacklist.addToBlacklist(movie);
        }

        Blacklist.printList();



    }


}
