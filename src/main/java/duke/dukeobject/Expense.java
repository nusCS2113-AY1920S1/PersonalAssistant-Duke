package duke.dukeobject;

import duke.exception.DukeException;
import duke.parser.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.StringJoiner;

public class Expense extends DukeItem {
    /**
     * The amount of money of the expense.
     */
    private final BigDecimal amount;
    /**
     * The description of the expense.
     */
    private final String description;
    /**
     * Whether or not the expense is tentative.
     */
    private boolean isTentative;
    /**
     * The time of the expense.
     */
    private final LocalDateTime time;

    /**
     * {@inheritDoc}
     */
    public static class Builder extends DukeItem.Builder<Builder> {
        private BigDecimal amount = BigDecimal.ZERO;
        private String description = "";
        private boolean isTentative = false;
        private LocalDateTime time = LocalDateTime.now();

        public Builder() {

        }

        /**
         * Constructs a builder from an existing expense.
         *
         * @param expense the expense whose values to use as the builder's default values.
         */
        public Builder(Expense expense) {
            super(expense);
            amount = expense.amount;
            description = expense.description;
            isTentative = expense.isTentative;
            time = expense.time;
        }

        /**
         * {@inheritDoc}
         */
        Builder(String storageString) throws DukeException {
            this(storageStringToMap(storageString));
        }

        /**
         * {@inheritDoc}
         */
        Builder(Map<String, String> mappedStorageString) throws DukeException {
            super(mappedStorageString);
            if (mappedStorageString.containsKey("amount")) {
                setAmount(mappedStorageString.get("amount"));
            }
            if (mappedStorageString.containsKey("description")) {
                setDescription(mappedStorageString.get("description"));
            }
            if (mappedStorageString.containsKey("isTentative")) {
                setTentative(Boolean.parseBoolean(mappedStorageString.get("isTentative")));
            }
            if (mappedStorageString.containsKey("time")) {
                setTime(Parser.parseTime(mappedStorageString.get("time")));
            }
        }

        /**
         * Sets the amount of the expense using a string.
         *
         * @param amount the amount of the expense as a string.
         * @return this builder.
         * @throws DukeException if the value in amount cannot be converted into a {@code BigDecimal},
         *                       or if the {@code BigDecimal} does not represent a valid amount.
         * @see #setAmount(BigDecimal)
         */
        public Builder setAmount(String amount) throws DukeException {
            try {
                return setAmount(new BigDecimal(amount));
            } catch (NumberFormatException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_EXPENSE_AMOUNT_INVALID, amount));
            }
        }

        /**
         * Sets the amount of the expense.
         *
         * @param amount the amount of the expense.
         * @return this builder.
         * @throws DukeException if the {@code BigDecimal} does not represent a valid amount.
         */
        public Builder setAmount(BigDecimal amount) throws DukeException {
            if (amount.scale() > 2) {
                throw new DukeException(
                    String.format(DukeException.MESSAGE_EXPENSE_AMOUNT_INVALID, amount.toPlainString()));
            }
            this.amount = amount.setScale(2, RoundingMode.UNNECESSARY);
            return this;
        }

        /**
         * Sets the description of the expense.
         *
         * @param description the description of the expense.
         * @return this builder.
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the tentativeness of the expense.
         *
         * @param tentative whether the expense is tentative.
         * @return this builder.
         */
        public Builder setTentative(boolean tentative) {
            isTentative = tentative;
            return this;
        }

        /**
         * Sets the time of the expense using a string.
         *
         * @param time the time of the expense as a string.
         * @return this builder.
         * @throws DukeException if the time string cannot be parsed into a {@code LocalDateTime} object.
         * @see #setTime(LocalDateTime)
         */
        public Builder setTime(String time) throws DukeException {
            try {
                return setTime(Parser.parseTime(time));
            } catch (DukeException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_EXPENSE_TIME_INVALID, time));
            }
        }

        /**
         * Sets the time of the expense.
         *
         * @param time the time of the expense.
         * @return this builder.
         */
        public Builder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        /**
         * Builds the expense.
         *
         * @return the expense.
         */
        public Expense build() {
            return new Expense(this);
        }
    }

    /**
     * Constructs an expense from the expense builder.
     *
     * @param builder the expense builder.
     */
    private Expense(Builder builder) {
        super(builder);
        amount = builder.amount;
        description = builder.description;
        isTentative = builder.isTentative;
        time = builder.time;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return {@link #amount}.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the description of the expense.
     *
     * @return {@link #description}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the expense is tentative.
     *
     * @return {@link #isTentative}.
     */
    public boolean isTentative() {
        return isTentative;
    }

    public void setTentative(boolean val) {
        isTentative = val;
    }

    /**
     * Returns the date of the expense.
     *
     * @return {@link #time}.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Return the formatted time.
     *
     * @return String of time that is formatted
     */
    public String getTimeString() {
        return Parser.formatTime(time);
    }

    /**
     * Converts the expense into a string.
     *
     * @return the expense as a string.
     */
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add((amount.compareTo(BigDecimal.valueOf(0)) < 0 ? "-$" + amount.abs() : "$" + amount));
        stringJoiner.add(description);
        stringJoiner.add(Parser.formatTime(time));
        if (isTentative) {
            stringJoiner.add("(tentative)");
        }
        if (!tags.isEmpty()) {
            stringJoiner.add("Tags: " + String.join(" ", tags));
        }

        return stringJoiner.toString();
    }

    /**
     * Converts the expense into a storage string.
     *
     * @return the expense as a storage string.
     */
    @Override
    protected String toStorageString() {
        StringJoiner stringJoiner = new StringJoiner(STORAGE_FIELD_DELIMITER);
        stringJoiner.add(super.toStorageString());
        stringJoiner.add("amount" + STORAGE_NAME_SEPARATOR + amount);
        stringJoiner.add("description" + STORAGE_NAME_SEPARATOR + description);
        stringJoiner.add("time" + STORAGE_NAME_SEPARATOR + Parser.formatTime(time));
        stringJoiner.add("isTentative" + STORAGE_NAME_SEPARATOR + isTentative);

        return stringJoiner.toString();
    }
}
