package commands;

import Contexts.SearchResultContext;
import EPstorage.Blacklist;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.io.IOException;

public class BlacklistCommand extends CommandSuper {

    /**
     * Constructor for each Command Super class.
     *
     * @param uicontroller
     */
    public BlacklistCommand (Controller uicontroller) {
        super(COMMANDKEYS.blacklist, CommandStructure.cmdStructure.get(COMMANDKEYS.blacklist), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
            case add:
                addToBlackList();
                break;
            case remove:
                String movie = getPayload();
                removeFromBlackList();
                Blacklist.saveBlackList();
                break;
            default:
                break;
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

}

