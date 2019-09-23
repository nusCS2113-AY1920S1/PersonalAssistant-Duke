package wallet.model.record;

import java.time.LocalDate;

/**
 * The Record Class that is the parent of Loans, Notes, Expenses.
 */
public abstract class Record {
    private String description;
    private LocalDate date;

    /**
     * Constructs a Record Object.
     *
     * @param description The String description.
     * @param createdDate The LocalDate object.
     */
    public Record(String description, LocalDate createdDate) {
        this.description = description;
        this.date = date;
    }

    /**
     * Returns a String description.
     *
     * @return String description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the String description.
     *
     * @param description The String description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns LocalDate object.
     *
     * @return The LocalDate object.
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the LocalDate object.
     *
     * @param createdDate The LocalDate object.
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

}
