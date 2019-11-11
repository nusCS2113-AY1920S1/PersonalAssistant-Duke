package seedu.duke.email;

import javafx.util.Pair;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.email.entity.Email;
import seedu.duke.email.parser.EmailContentParseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmailList extends ArrayList<Email> {

    private SortOrder order = SortOrder.TIME;

    public void setOrder(SortOrder order) {
        this.order = order;
        sortByGivenOrder();
    }

    /**
     * Converts the email list to a string of the pre-determined format that is ready to be displayed by the
     * UI.
     *
     * @return list of emails in formatted String.
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return "There is nothing in your email list.";
        }
        int index = 0;
        String listOfEmails = "This is your list of emails " + "(total of " + this.size() + "): ";
        for (Email email : this) {
            listOfEmails += System.lineSeparator() + (++index) + ". " + email.toGuiString();
        }
        return listOfEmails;
    }

    /**
     * Convert email list to String when given an index list of emails.
     *
     * @param indexes a list of index if emails
     * @return string of email List
     */
    public String toString(ArrayList<Integer> indexes) {
        if (this.size() == 0) {
            return "There is nothing in your email list.";
        }
        if (indexes.size() == 0) {
            return "There is nothing to be shown.";
        }
        try {
            String listOfEmails = "";
            for (int i = 0; i < indexes.size(); i++) {
                Integer index = indexes.get(i);
                listOfEmails += index + 1 + ". ";
                listOfEmails += this.get(index).toGuiString() + System.lineSeparator();
            }
            return listOfEmails;
        } catch (IndexOutOfBoundsException e) {
            return "Some index provided is out of bounds.";
        }
    }

    /**
     * Show the email in browser.
     *
     * @param index of the email to be shown in the email list.
     * @return a string to inform the user that the particular email is being shown in browser.
     * @throws CommandParseHelper.CommandParseException thrown when index parsing failed or out of range
     * @throws IOException                              if fails to load the filepath or open the browser.
     */
    public String[] show(int index) {
        Email email = this.get(index);
        String emailContent = email.highlightOnTag();
        String responseMsg = "Showing email in browser: " + email.getSubject();
        String[] responseArray = {responseMsg, emailContent};
        return responseArray;
    }


    /**
     * Delete email at the given index from the email list.
     *
     * @param index of email to be deleted
     * @return confirmation message to be displayed to user
     */
    public String deleteLocalEmail(int index) {
        Email email = this.get(index);
        this.remove(email);
        String responseMsg = constructDeleteMessage(email);
        return responseMsg;
    }

    /**
     * Finds the email with the input SHA hash and converts into subject.
     *
     * @param sha the identifier to be converted
     * @return    Subject that corresponds to the SHA hash
     */
    public String convertShaToSubject(String sha) {
        for (Email email : this) {
            if (sha.equals(email.getShaHash())) {
                return email.getSubject();
            }
        }
        return null;
    }

    /**
     * Finds the email with the input SHA hash and converts into index.
     *
     * @param sha the identifier to be converted
     * @return    Index that corresponds to the SHA hash
     */
    public int convertShaToIndex(String sha) {
        for (int i = 0; i < this.size(); i++) {
            Email email = this.get(i);
            if (sha.equals(email.getShaHash())) {
                return i;
            }
        }
        return 0;
    }

    private String constructDeleteMessage(Email email) {
        return "Deleted email: " + email.getSubject();
    }

    /**
     * Clears the email list by deleting the email one by one.
     *
     * @return confirmation message to be displayed to user
     */
    public String clearList() {
        if (this.size() == 0) {
            return "The email list has already been cleared";
        } else {
            while (this.size() != 0) {
                this.remove(0);
            }
        }
        String responseMsg = constructClearListMessage();
        return responseMsg;
    }

    private String constructClearListMessage() {
        return "Email List has been cleared";
    }

    /**
     * Tags email at the index with the tags input.
     *
     * @param index email to add tags to
     * @param tags  tags to be added to the email
     * @return confirmation message to be displayed to user
     */
    public String addTags(int index, ArrayList<String> tags) {
        Email email = this.get(index);
        ArrayList<String> successTagList = new ArrayList<>();
        for (String tag : tags) {
            boolean success = email.addTag(tag);
            if (success) {
                successTagList.add(tag);
            }
        }
        String responseMsg = "";
        if (successTagList.size() > 0) {
            responseMsg = constructAddTagsMessage(successTagList, email);
        }
        return responseMsg;
    }

    private String constructAddTagsMessage(ArrayList<String> successTagList, Email email) {
        return "Tags added: " + successTagList.toString() + System.lineSeparator() + "to email: " + email.getSubject();
    }

    /**
     * Generates a list of string of all emails for gui display.
     *
     * @return list of string generated for gui display
     */
    public ArrayList<String> getEmailGuiStringList() {
        ArrayList<String> emailStringList = new ArrayList<>();
        for (Email email : this) {
            emailStringList.add(email.toGuiString());
        }
        return emailStringList;
    }

    /**
     * Searches for the target string with some degree of tolerance in difference.
     *
     * @param target the given target string
     * @return the string to be displayed to the user containing all the search result
     */
    public String fuzzySearch(String target) {
        ArrayList<Pair<Integer, Integer>> results = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            int score = EmailContentParseHelper.fuzzySearchInEmail(this.get(i), target);
            if (score > 0) {
                results.add(new Pair<>(i, score));
            }
        }
        Collections.sort(results, (r1, r2) -> r2.getValue().compareTo(r1.getValue()));
        ArrayList<Integer> indexes = extractIndexFromFuzzyResults(results);
        return this.toString(indexes);
    }

    private static ArrayList<Integer> extractIndexFromFuzzyResults(ArrayList<Pair<Integer, Integer>> results) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (Pair result : results) {
            indexes.add((Integer) result.getKey());
        }
        return indexes;
    }

    /**
     * Sort the email list by the order given earlier.
     */
    public void sortByGivenOrder() {
        switch (order) {
        case TIME:
            sort(Comparator.comparing(Email::getReceivedDateTime).reversed());
            break;
        default:
            return;
        }
    }

    public enum SortOrder {
        TIME,
        RELEVANCE
    }
}
