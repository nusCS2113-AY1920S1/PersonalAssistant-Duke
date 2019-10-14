package Commands;

import Contexts.SearchResultContext;
import EPstorage.Blacklist;
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
            case blacklist:
                addToBlackList();
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
