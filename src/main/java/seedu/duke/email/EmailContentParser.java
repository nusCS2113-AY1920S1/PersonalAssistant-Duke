package seedu.duke.email;

import seedu.duke.Duke;
import seedu.duke.email.entity.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser to process the content of emails to support automatic management of email.
 */
public class EmailContentParser {
    private static int KEYWORD_SUBJECT_WEIGHTAGE = 5;
    private static int KEYWORD_SENDER_WEIGHTAGE = 3;
    private static int KEYWORD_BODY_WEIGHTAGE = 1;
    private static ArrayList<KeywordPair> keywordList;

    /**
     * Finds all keywords in email.
     * @param email Email to be scanned for keywords
     */
    public static void allKeywordInEmail(Email email) {
        for (KeywordPair keywordPair : keywordList) {
            if (keywordInEmail(email, keywordPair) > 0) {
                Duke.getUI().showDebug(keywordPair.getKeyword() + ": "
                        + keywordInEmail(email, keywordPair) + " => " + email.getSubject());
                email.addTag(keywordPair.getKeyword());
            }
        }
    }

    /**
     * Calculates the keyword occurrence score within an email based on its position and number of
     * occurrence.
     *
     * @param email       the email where the keyword pair is to be looked for
     * @param keywordPair the target keyword pair
     * @return the occurrence score
     */
    public static int keywordInEmail(Email email, KeywordPair keywordPair) {
        int totalScore = 0;
        totalScore += keywordInSubject(email, keywordPair) * KEYWORD_SUBJECT_WEIGHTAGE;
        totalScore += keywordInSender(email, keywordPair) * KEYWORD_SENDER_WEIGHTAGE;
        totalScore += keywordInBody(email, keywordPair) * KEYWORD_BODY_WEIGHTAGE;
        return totalScore;
    }

    private static int keywordInSubject(Email email, KeywordPair keywordPair) {
        return keywordInString(email.getSubject(), keywordPair);
    }

    private static int keywordInSender(Email email, KeywordPair keywordPair) {
        return keywordInString(email.getSenderString(), keywordPair);
    }

    private static int keywordInBody(Email email, KeywordPair keywordPair) {
        return keywordInString(email.getBody(), keywordPair);
    }

    /**
     * Looks for a keyword within a given string.
     *
     * @param input       the string where the keyword is looked for
     * @param keywordPair the target keyword looking for
     * @return whether the keyword pair is found in the string
     */
    public static int keywordInString(String input, KeywordPair keywordPair) {
        int occurrence = 0;
        for (int i = 0; i < keywordPair.getExpressions().size(); i++) {
            String expression = keywordPair.getExpressions().get(i);
            Pattern expressionPattern = Pattern.compile(".*" + expression + ".*",
                    Pattern.CASE_INSENSITIVE);
            Matcher expressionMatcher = expressionPattern.matcher(input);
            while (expressionMatcher.find()) {
                occurrence++;
            }
        }
        return occurrence;
    }

    /**
     * Keyword List for searching.
     */
    public static void initKeywordList() {
        ArrayList<KeywordPair> keywordList = new ArrayList<>();
        keywordList.add(new KeywordPair("CS2113T", new ArrayList<String>(List.of(
                "CS2113T", "CS2113", "TAN KIAN WEI, JASON", "Leow Wei Xiang"))));
        keywordList.add(new KeywordPair("CS2101", new ArrayList<String>(List.of(
                "CS2101", "Anita Toh Ann Lee"))));
        keywordList.add(new KeywordPair("CG2271", new ArrayList<String>(List.of(
                "CG2271", "Djordje Jevdjic"))));
        keywordList.add(new KeywordPair("CEG Admin", new ArrayList<String>(List.of(
                "Low Mun Bak"))));
        keywordList.add(new KeywordPair("SEP", new ArrayList<String>(List.of(
                "SEP", "Student Exchange Programme"))));

        EmailContentParser.keywordList = keywordList;
    }

    /**
     * A pair of keyword with its possible expressions.
     */
    public static class KeywordPair {
        private String keyword;
        private ArrayList<String> expressions;

        /**
         * Constructs a keyword pair.
         *
         * @param keyword     the value of keyword looked for
         * @param expressions the possible expressions of that keyword
         */
        public KeywordPair(String keyword, ArrayList<String> expressions) {
            this.keyword = keyword;
            this.expressions = expressions;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public ArrayList<String> getExpressions() {
            return this.expressions;
        }
    }
}
