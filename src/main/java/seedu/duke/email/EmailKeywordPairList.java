package seedu.duke.email;

import javafx.util.converter.TimeStringConverter;
import seedu.duke.common.storage.TimestampHelper;
import seedu.duke.email.entity.KeywordPair;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmailKeywordPairList extends ArrayList<KeywordPair> {
    private LocalDateTime updatedOn;

    public EmailKeywordPairList() {
        super();
        updatedOn = TimestampHelper.getDateTime();
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Sets updatedOn to current time.
     */
    public void updateTimestamp() {
        this.updatedOn = TimestampHelper.getDateTime();
    }

    private EmailKeywordPairList copy() {
        EmailKeywordPairList newList = new EmailKeywordPairList();
        for (KeywordPair keywordPair : this) {
            newList.add(keywordPair);
        }
        newList.updateTimestamp();
        return newList;
    }

    /**
     * Checks whether the keyword in the keyword pair already exists.
     * @param keywordPair the keyword pair to be checked
     * @return the existing keyword pair if exists, otherwise returns null
     */
    private KeywordPair checkExists(KeywordPair keywordPair) {
        for (KeywordPair existingPair : this) {
            if (keywordPair.getKeyword().equals(existingPair.getKeyword())) {
                return existingPair;
            }
        }
        return null;
    }

    /**
     * Adds a keyword pair to a copied list of the existing keyword pair list.
     *
     * @param keywordPair to be added
     * @return the copied keyword pair list after adding
     */
    public EmailKeywordPairList addAndCopy(KeywordPair keywordPair) {
        EmailKeywordPairList newList = this.copy();
        KeywordPair existingPair = checkExists(keywordPair);
        if (existingPair == null) {
            newList.add(keywordPair);
        } else {
            for (String expression: keywordPair.getExpressions()) {
                existingPair.addExpression(expression);
            }
        }
        return newList;
    }
}
