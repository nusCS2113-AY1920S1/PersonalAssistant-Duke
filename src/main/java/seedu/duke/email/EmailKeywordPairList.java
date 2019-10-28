package seedu.duke.email;

import seedu.duke.email.entity.KeywordPair;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmailKeywordPairList extends ArrayList<KeywordPair> {
    private LocalDateTime updatedOn;

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
