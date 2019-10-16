package commands;

import Contexts.SearchResultContext;
import EPstorage.Blacklist;
import ListCommands.WatchlistHandler;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.io.IOException;

public class RemoveCommand extends CommandSuper {
    /**
     * Constructor for each Command Super class.
     *
     * @param uicontroller
     */
    public RemoveCommand(Controller uicontroller) {
        super(COMMANDKEYS.remove, CommandStructure.cmdStructure.get(COMMANDKEYS.remove), uicontroller);
    }

    /**
     * executes the commands based on the subroot command that is passed to it
     * @throws IOException
     */
    @Override
    public void executeCommands() throws IOException {
        switch(this.getSubRootCommand()) {
            case watchlist:
                String mov = getPayload();
                System.out.println(mov);
                if (WatchlistHandler.removeFromWatchlist(mov, (MovieHandler)(this.getUIController()))) {
                        ((MovieHandler) getUIController()).setFeedbackText("Successfully removed the movie from WatchList: " + mov);
                } else {
                        ((MovieHandler) getUIController()).setFeedbackText("Such a movie does not exist in your WatchList. Check your spelling?");
                }
                break;
            case blacklist:
                String movie = getPayload();
                removeFromBlackList();
                break;
            default:
                break;
        }
    }

    private void removeFromBlackList() {

        String movie = getPayload();

        boolean stat = false;
        if (getFlagMap().get("-k") != null) {
            System.out.print("REmoving ing keyword");
            if (isInteger(movie, 10)) {
                stat = Blacklist.removeFromBlacklistKeyWord(SearchResultContext.getIndex(Integer.parseInt(movie)).getTitle());
            } else {
                stat = Blacklist.removeFromBlacklistKeyWord(movie);
            }
        } else {
            System.out.print("Removingg movie");
            if (isInteger(movie, 10)) {
                stat = Blacklist.removeFromBlacklistMovies(SearchResultContext.getIndex(Integer.parseInt(movie)));
            } else {
                stat = Blacklist.removeFromBlacklistMovieTitle(movie);
            }

        }

        if (stat) {
            ((MovieHandler) getUIController()).setFeedbackText("Successfully Removed from BlackList");
        } else {
            ((MovieHandler) getUIController())
                    .setFeedbackText("Such a movie does not exist in your BlackList. Check your spelling?");
        }
    }

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

}
