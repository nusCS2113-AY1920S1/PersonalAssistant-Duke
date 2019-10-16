package commands;

import EPstorage.Blacklist;
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

    @Override
    public void executeCommands() throws IOException {
        switch(this.getSubRootCommand()) {
            case watchlist:

                break;
            case blacklist:

                String movie = getPayload();

                if (isInteger(movie , 10)) {
                    int movieIndex = Integer.parseInt(movie);
                    movie = Blacklist.getIndex(movieIndex);
                }
                if (Blacklist.removeFromBlacklist(movie)) {
                    ((MovieHandler) getUIController()).setFeedbackText("Successfully Removed from BlackList");
                } else {
                    ((MovieHandler) getUIController()).setFeedbackText("Such a movie does not exist in your BlackList. Check your spelling?");
                }
                break;
            default:
                break;
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