package wallet.model.record;

import java.time.LocalDate;

/**
 * The Record Class that is the parent of Loans, Notes, Expenses.
 */
public abstract class Record {
    private int id;
    private String description;
    private LocalDate date;

    /**
     * Default constructor.
     */
    public Record() {

    }

    /**
     * Constructs a Record Object.
     *
     * @param description The String description.
     * @param date        The LocalDate object.
     */
    public Record(String description, LocalDate date) {
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
     * Sets the LocalDate object.
     *
     * @param date LocalDate object.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the LocalDate object.
     *
     * @return the LocalDate object.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Sets the id of the Record Object.
     *
     * @param id The identity of the Record object.
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Formats the record to write to save file.
     */
    public abstract String writeToFile();
}
