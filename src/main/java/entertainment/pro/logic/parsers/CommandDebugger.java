package entertainment.pro.logic.parsers;


import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.model.CommandPair;
import entertainment.pro.ui.Controller;
import java.util.HashSet;
import java.util.Set;

/**
 * CommandDebugger class to do spellchecking by checking for similarity with words.
 * JccardSimilarity algorithm used to quantify similarity
 */
public class CommandDebugger {

    private static final int INITSCORE = 0;


    /**
     * Spellchcker function to determine closest possible words.
     * @param undefinedCommandArr the user input split into array form
     * @param root Root command
     * @param controller UI controller
     * @return
     */
    public static CommandPair commandSpellChecker(String[] undefinedCommandArr, COMMANDKEYS root, Controller controller) {

        System.out.println("Cant find anything");
        root = getCorrectedRoot(undefinedCommandArr, root);
        COMMANDKEYS mostSimilarSub = getCorrectedSubRoot(undefinedCommandArr, root);

        CommandPair cp = new CommandPair(root, mostSimilarSub);

        if (undefinedCommandArr.length < 1 && CommandStructure.cmdStructure.get(root).length != 0) {
            cp.setValidCommand(false);
        }

        return cp;

    }

    /**
     * Get the corrected Root.
     * @param undefinedCommandArr user input in array form
     * @param root root command
     * @return the corrected root command
     */
    private static COMMANDKEYS getCorrectedSubRoot(String[] undefinedCommandArr, COMMANDKEYS root) {
        double score;
        score = INITSCORE;
        COMMANDKEYS mostSimilarSub = COMMANDKEYS.none;

        if (root != COMMANDKEYS.none && CommandStructure.cmdStructure.get(root).length != 0 && undefinedCommandArr.length > 1) {
            for (COMMANDKEYS s : CommandStructure.cmdStructure.get(root)) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[1]);
                if (temp > score) {
                    mostSimilarSub = s;
                    score = temp;
                }
            }
            System.out.println("Did you mean" + mostSimilarSub);
        }
        return mostSimilarSub;
    }

    /**
     * Get the corrected subroot.
     * @param undefinedCommandArr user input in array form
     * @param root root command
     * @return the corrected root command
     */
    private static COMMANDKEYS getCorrectedRoot(String[] undefinedCommandArr, COMMANDKEYS root) {
        double score = INITSCORE;
        if (root == COMMANDKEYS.none && undefinedCommandArr.length > 0) {
            for (COMMANDKEYS s : CommandStructure.AllRoots) {
                double temp = calculateJaccardSimilarity(s.toString(), undefinedCommandArr[0]);
                if (temp > score) {
                    root = s;
                    score = temp;
                }
            }
            if (root != COMMANDKEYS.none) {
                System.out.println("Did you mean" + root);
            }
        }
        return root;
    }

    /**
     * Compute string similarity based on  Jaccard Similarity algorithm.
     *
     * @param word1
     * @param word2
     * @return the similarity score based on the algorithm
     */
    public static Double calculateJaccardSimilarity(CharSequence word1, CharSequence word2) {
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
