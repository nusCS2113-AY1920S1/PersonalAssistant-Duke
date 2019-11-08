package seedu.duke.email.parser;

import org.json.JSONException;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.entity.Email;
import seedu.duke.email.entity.KeywordPair;
import seedu.duke.email.storage.EmailKeywordPairStorage;
import seedu.duke.ui.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser to process the content of emails to support automatic management of email.
 */
public class EmailContentParseHelper {
    private static int KEYWORD_SUBJECT_WEIGHTAGE = 5;
    private static int KEYWORD_SENDER_WEIGHTAGE = 3;
    private static int KEYWORD_BODY_WEIGHTAGE = 1;
    private static int INFINITY = 0x3f3f3f;
    private static int FUZZY_LIMIT = 3;

    /**
     * Finds all keywords in email.
     *
     * @param email Email to be scanned for keywords
     */
    public static void allKeywordInEmail(Email email) {
        EmailKeywordPairList keywordList = Model.getInstance().getKeywordPairList();
        //skip if the email update is more recent than the keyword update
        if (email.getUpdatedOn() != null && email.getUpdatedOn().compareTo(keywordList.getUpdatedOn()) >= 0) {
            return;
        }
        for (KeywordPair keywordPair : keywordList) {
            int relevance = keywordInEmail(email, keywordPair);
            if (relevance > 0) {
                UI.getInstance().showDebug(keywordPair.getKeyword() + ": " + keywordInEmail(email, keywordPair)
                        + " => " + email.getSubject());
                email.addTag(keywordPair, relevance);
            }
        }
        email.updateTimestamp();
    }

    /**
     * Calculates the keyword relevance score within an email based on its position and number of occurrence.
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
            String processedInput = input;
            Pattern expressionPattern = Pattern.compile("(^|\\W)" + expression + "(\\W|$)",
                    Pattern.CASE_INSENSITIVE);
            Matcher expressionMatcher = expressionPattern.matcher(processedInput);
            while (expressionMatcher.find()) {
                occurrence++;
                processedInput = expressionMatcher.replaceFirst(" ");
                expressionMatcher = expressionPattern.matcher(processedInput);
            }
        }
        return occurrence;
    }

    /**
     * Keyword List for searching.
     */
    public static EmailKeywordPairList initKeywordList() {
        if (EmailKeywordPairStorage.keywordPairFileExists()) {
            try {
                return EmailKeywordPairStorage.readKeywordPairList();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                UI.getInstance().showDebug("Keyword list file is empty or in wrong format. Default used...");
            }
        }
        EmailKeywordPairList keywordList = getDefaultKeywordPairList();
        try {
            EmailKeywordPairStorage.saveKeywordPairList(keywordList);
        } catch (JSONException | IOException e) {
            UI.getInstance().showError("Keyword Pair List Save Failed...");
        }
        return keywordList;
    }

    private static EmailKeywordPairList getDefaultKeywordPairList() {
        EmailKeywordPairList keywordList = new EmailKeywordPairList();
        keywordList.add(new KeywordPair("CS2113T", new ArrayList<>(List.of(
                "CS2113T", "CS2113", "TAN KIAN WEI, JASON", "Leow Wei Xiang", "Akshay Narayan", "Akshay"))));
        keywordList.add(new KeywordPair("CS2101", new ArrayList<>(List.of(
                "CS2101", "Anita Toh Ann Lee"))));
        keywordList.add(new KeywordPair("CG2271", new ArrayList<>(List.of(
                "CG2271", "Djordje Jevdjic"))));
        keywordList.add(new KeywordPair("CS2102", new ArrayList<>(List.of(
                "CS2102", "Adi Yoga Sidi Prabawa"))));
        keywordList.add(new KeywordPair("CS3230", new ArrayList<>(List.of(
                "CS3230", "Divesh Aggarwal"))));
        keywordList.add(new KeywordPair("CEG Admin", new ArrayList<>(List.of(
                "Low Mun Bak"))));
        keywordList.add(new KeywordPair("SEP", new ArrayList<>(List.of(
                "SEP", "Student Exchange Programme"))));
        keywordList.add(new KeywordPair("Tutorial", new ArrayList<>(List.of(
                "Tutorial"))));
        keywordList.add(new KeywordPair("Assignment", new ArrayList<>(List.of(
                "Assignment"))));
        keywordList.add(new KeywordPair("Spam", new ArrayList<>(List.of(
                "UHC Wellness", "luminus-do-not-reply", "NUS Libraries"))));

        return keywordList;
    }

    /**
     * Removes all the old keywords in an email.
     *
     * @param email the email where the keywords are removed
     * @param keywordPairList the list of old keyword pairs to be removed
     */
    public static void clearOldKeywordPairs(Email email, EmailKeywordPairList keywordPairList) {
        for (KeywordPair keywordPair : keywordPairList) {
            email.removeTag(keywordPair.getKeyword());
        }
    }

    /**
     * Computes the edit distance between first and second, which is the number of steps required to transform
     * first to second if only addition, deletion, update of a single character is allowed for each step.
     *
     * @param w1 first word
     * @param w2 second word
     * @return edit distance between first and second
     */
    public static int editDistance(String w1, String w2) {
        if (w1.length() == 0 || w2.length() == 0) {
            return w1.length() + w2.length();
        }
        String first = w1.toLowerCase();
        String second = w2.toLowerCase();
        //Prepare a distance array for DP
        int[][] dist = new int[first.length() + 1][second.length() + 1];
        //Initialize distance array with all zeros
        for (int[] row : dist) {
            Arrays.fill(row, 0);
        }
        //Initialize starting positions for DP
        for (int i = 0; i <= first.length(); i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j <= second.length(); j++) {
            dist[0][j] = j;
        }
        //Start DP
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int min = INFINITY;
                min = Math.min(min, dist[i - 1][j - 1] + (first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1));
                min = Math.min(min, dist[i - 1][j] + 1);
                min = Math.min(min, dist[i][j - 1] + 1);
                dist[i][j] = min;
            }
        }
        return dist[first.length()][second.length()];
    }

    /**
     * Searches a keyword in input string with some tolerance of inaccuracy.
     *
     * @param input  input string where the keyword is searched
     * @param target the target keyword to be searched
     * @return a relevance score related to both occurrence and relevance
     */
    private static int fuzzySearchInString(String input, String target) {
        int relevance = 0;
        String[] inputWords = input.split("\\W");
        String[] targetWords = target.split("\\W");
        for (String inputWord : inputWords) {
            for (String targetWord : targetWords) {
                if (inputWord.length() == 0 || targetWord.length() == 0) {
                    continue;
                }
                int distance = editDistance(inputWord, targetWord);
                if (distance <= FUZZY_LIMIT) {
                    relevance += FUZZY_LIMIT - distance + 1;
                }
            }
        }
        UI.getInstance().showError(relevance + " : " + input + " <> " + target);
        return relevance;
    }

}
