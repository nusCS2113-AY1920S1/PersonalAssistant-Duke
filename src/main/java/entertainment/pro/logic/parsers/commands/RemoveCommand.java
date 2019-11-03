package entertainment.pro.logic.parsers.commands;

import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

//TODO REMOVE THIS CLASS

public class RemoveCommand extends CommandSuper {
    /**
     * Constructor for each Command Super class.
     */
    public RemoveCommand(Controller uicontroller) {
        super(COMMANDKEYS.remove, CommandStructure.cmdStructure.get(COMMANDKEYS.remove), uicontroller);
    }

    /**
     * executes the entertainment.pro.logic.parsers.commands based on the subroot command that is passed to it
     */
    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
        case watchlist:
            String mov = getPayload();
            System.out.println(mov);
            if (WatchlistHandler.removeFromWatchlist(mov, (MovieHandler)(this.getUiController()))) {
                ((MovieHandler) getUiController())
                        .setGeneralFeedbackText("Successfully removed the movie from WatchList: " + mov);
            } else {
                ((MovieHandler) getUiController())
                        .setGeneralFeedbackText("Such a movie does not exist in your WatchList. Check your spelling?");
            }
            break;
        case blacklist:
            break;
        default:
            break;
        }
    }


    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
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
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

}