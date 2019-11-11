package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.exceptions.DuplicateEntryException;
import entertainment.pro.logic.contexts.SearchResultContext;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Blacklist command class to handle blacklist command functions.
 */
public class BlacklistCommand extends CommandSuper {

    private static final Logger logger = Logger.getLogger(Blacklist.class.getName());

    /**
     * Constructor for each Command Super class.
     */
    public BlacklistCommand(Controller uicontroller) {
        super(CommandKeys.BLACKLIST, CommandStructure.cmdStructure.get(CommandKeys.BLACKLIST), uicontroller);
    }


    /**
     * Function to execute commands depending on the subroot command.
     */
    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
        case ADD:
            addToBlackList();
            break;
        case REMOVE:
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
            if (Character.digit(s.charAt(i), radix) < 0) {
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
        String payload = getPayload().trim();

        try {

            if (getFlagMap().get("-k") != null) {
                String movies[] = payload.split(",");
                for (String movie : movies) {
                    if (isInteger(movie.trim(), 10)) {
                        Blacklist.addToBlacklistKeyWord(SearchResultContext
                                .getItemAtIndex(Integer.parseInt(movie.trim())).getTitle());
                    } else {
                        Blacklist.addToBlacklistKeyWord(movie.toLowerCase().trim());
                    }
                }
            } else {
                String movies[] = payload.split(",");
                for (String movie : movies) {
                    if (isInteger(movie.trim(), 10)) {
                        Blacklist.addToBlacklistMoviesID(SearchResultContext
                                .getItemAtIndex(Integer.parseInt(movie.trim())));
                    } else {
                        Blacklist.addToBlacklistMovie(movie.toLowerCase().trim());
                    }
                }

            }

            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(Blacklist.printList());

        } catch (DuplicateEntryException e) {


            ((MovieHandler) this.getUiController())
                    .setGeneralFeedbackText(PromptMessages.DUPLICATE_BLACKLIST + Blacklist.printList());
        }

        logger.log(Level.INFO, PromptMessages.BLACKLIST_ADD_SUCCUESS);
    }

    /**
     * Removes items from the blacklist.
     */
    private void removeFromBlackList() {

        String payload = getPayload();

        boolean stat = false;
        if (getFlagMap().get("-k") != null) {
            String movies[] = payload.split(",");
            for (String movie : movies) {
                if (isInteger(movie.trim(), 10)) {
                    stat = Blacklist.removeFromBlacklistKeyWord(SearchResultContext
                            .getItemAtIndex(Integer.parseInt(movie.trim()))
                            .getTitle());
                } else {
                    stat = Blacklist.removeFromBlacklistKeyWord(movie.toLowerCase().trim());
                }
            }

        } else {
            String movies[] = payload.split(",");
            for (String movie : movies) {
                if (isInteger(movie.trim(), 10)) {
                    stat = Blacklist.removeFromBlacklistMovies(SearchResultContext
                            .getItemAtIndex(Integer.parseInt(movie.trim())));
                } else {
                    stat = Blacklist.removeFromBlacklistMovieTitle(movie.toLowerCase().trim());
                }
            }

        }

        logger.log(Level.INFO, PromptMessages.BLACKLIST_REMOVE_SUCCUESS);

        if (stat) {
            ((MovieHandler) getUiController()).setGeneralFeedbackText(PromptMessages.BLACKLIST_REMOVE_SUCCUESS);
        } else {
            ((MovieHandler) getUiController())
                    .setGeneralFeedbackText(PromptMessages.BLACKLIST_REMOVE_FAILURE);

        }
    }

}

