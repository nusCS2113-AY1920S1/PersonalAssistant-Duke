package entertainment.pro.logic.parsers;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.assertions.CommandAssertions;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.Controller;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CommandDebugger class to do spellchecking by checking for similarity with words.
 * JccardSimilarity algorithm used to quantify similarity
 */
public class CommandDebugger {

    private static final int INITSCORE = 0;
    private static final Logger logger = Logger.getLogger(CommandDebugger.class.getName());


    /**
     * Spellchcker function to determine closest possible words.
     * @param undefinedCommandArr the user input split into array form
     * @param root Root command
     * @param controller UI controller
     * @return
     */
    public static CommandPair commandSpellChecker(String[] undefinedCommandArr,
                                                  CommandKeys root, Controller controller)
        throws MissingInfoException {


        assert (CommandAssertions.assertIsLowerStringArr(undefinedCommandArr));
        logger.log(Level.INFO, PromptMessages.LOGGER_UNKNOWN_COMMAND_TYPED);

        root = getCorrectedRoot(undefinedCommandArr, root);
        CommandKeys mostSimilarSub = getCorrectedSubRoot(undefinedCommandArr, root);

        CommandPair cp = new CommandPair(root, mostSimilarSub);

        if (undefinedCommandArr.length < 1 && CommandStructure.hasSubRoot(root)) {
            cp.setValidCommand(false);
        }

        return cp;

    }

    /**
     * Get the corrected Root.
     * @param undefinedCommandArr user input in array form.
     * @param root root command.
     * @return the corrected root command.
     */
    private static CommandKeys getCorrectedSubRoot(String[] undefinedCommandArr, CommandKeys root)
            throws MissingInfoException {
        double score;
        score = INITSCORE;
        CommandKeys mostSimilarSub = CommandKeys.NONE;

        if (undefinedCommandArr.length <= 1) {
            logger.log(Level.INFO, PromptMessages.COMMAND_MISSING_ARGS);
            throw new MissingInfoException("You are missing a few arguments and presumably have a typo as well");
        }

        if (root != CommandKeys.NONE && CommandStructure.hasSubRoot(root) && undefinedCommandArr.length > 1) {
            for (CommandKeys s : CommandStructure.cmdStructure.get(root)) {
                double temp = calculateJaccardSimilarity(s.toString().toLowerCase(),
                        undefinedCommandArr[1]);
                if (temp > score) {
                    mostSimilarSub = s;
                    score = temp;
                }
            }
        }
        return mostSimilarSub;
    }

    /**
     * Get the corrected subroot.
     * @param undefinedCommandArr user input in array form
     * @param root root command
     * @return the corrected root command
     */
    private static CommandKeys getCorrectedRoot(String[] undefinedCommandArr, CommandKeys root) {
        double score = INITSCORE;
        if (root == CommandKeys.NONE && undefinedCommandArr.length > 0) {
            for (CommandKeys s : CommandStructure.AllRoots) {
                double temp = calculateJaccardSimilarity(s.toString().toLowerCase(),
                        undefinedCommandArr[0]);
                if (temp > score) {
                    root = s;
                    score = temp;
                }
            }

        }
        return root;
    }

    /**
     * Compute string similarity based on  Jaccard Similarity algorithm.
     * @return the similarity score based on the algorithm
     */
    public static Double calculateJaccardSimilarity(String word1, String word2) {
        assert (CommandAssertions.assertIsLowerString(word1));
        assert (CommandAssertions.assertIsLowerString(word2));
        Set<String> iset = new HashSet<String>();
        Set<String> unionSet = new HashSet<String>();
        boolean isfilled = false;
        int leftLength = word1.length();
        int rightLength = word2.length();

        if (leftLength == 0 || rightLength == 0) {
            return 0.0;
        }

        for (int lefti = 0; lefti < leftLength; lefti++) {
            unionSet.add(String.valueOf(word1.charAt(lefti)));
            for (int righti = 0; righti < rightLength; righti++) {
                if (!isfilled) {
                    unionSet.add(String.valueOf(word2.charAt(righti)));
                }
                if (word1.charAt(lefti) == word2.charAt(righti)) {
                    iset.add(String.valueOf(word1.charAt(lefti)));
                }
            }
            isfilled = true;
        }
        return Double.valueOf(iset.size()) / Double.valueOf(unionSet.size());
    }
}
