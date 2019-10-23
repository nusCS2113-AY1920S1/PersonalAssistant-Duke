package logic.parsers;

public class SpellingErrorCorrector {
    //@@author chenyuheng
    /**
     * This method automatically correct typos of command, only support the command words
     * in the dict String Array: <br />
     * <code>dict = {"ADD", "LIST", "DONE", "BYE", "DELETE", "FIND", "RECURRING", "SNOOZE", "SCHEDULE", "CHECK",
     * "LINK", "UNLINK", "REMOVE"};
     * </code>
     *
     * @param command the original command word
     * @return If the method can recognize the word, return the correct(ed) command word;
     * if the method cannot recognize the word, return the original word.
     */
    public static String commandCorrector(String[] dict, String command) {
        double[] similarity = new double[dict.length];
        double maxSimilarity = 0;
        int maxSimilarityCommandIndex = -1;
        for (int i = 0; i < dict.length; i++) {
            similarity[i] = getSimilarity(dict[i], command);
            maxSimilarity = maxSimilarity > similarity[i] ? maxSimilarity : similarity[i];
            maxSimilarityCommandIndex = maxSimilarity > similarity[i] ? maxSimilarityCommandIndex : i;
        }
        if (maxSimilarity > 0.5) {
            return dict[maxSimilarityCommandIndex];
        }
        return command;
    }

    /**
     * Get the similarity between two Strings.<br />
     * The similarity has some properties below:
     * <ul>
     * <li>If similarity == 1, the two Strings are equal.</li>
     * <li>If similarity < 1, the two Strings have certain difference.</li>
     * <li>If similarity < 0, the two Strings have different lengths.</li>
     * </ul>
     *
     * @param string1 the first String
     * @param string2 the second String
     * @return the defined similarity
     */
    public static double getSimilarity(String string1, String string2) {
        double delta = 1; // lost penalty
        double alpha = 1; // mismatch penalty
        double[][] opt = new double[string1.length() + 1][string2.length() + 1];
        opt[0][0] = 0;
        for (int i = 0; i < string1.length() + 1; i++) {
            opt[i][0] = i * delta;
        }
        for (int i = 0; i < string2.length() + 1; i++) {
            opt[0][i] = i * delta;
        }
        for (int j = 1; j < string2.length() + 1; j++) {
            for (int i = 1; i < string1.length() + 1; i++) {
                double s1 = opt[i - 1][j - 1];
                if (string1.charAt(i - 1) != string2.charAt(j - 1)) {
                    s1 = alpha + opt[i - 1][j - 1];
                }
                double s2 = delta + opt[i - 1][j];
                double s3 = delta + opt[i][j - 1];
                opt[i][j] = Math.min(Math.min(s1, s2), s3);
            }
        }
        return 2 * (0.5 - opt[string1.length()][string2.length()] / (string1.length() + string2.length()));
    }
}
