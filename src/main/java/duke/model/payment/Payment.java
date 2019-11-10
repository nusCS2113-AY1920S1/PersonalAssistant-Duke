package duke.model.payment;

import duke.exception.DukeException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Payment to pay.
 */
public class Payment {

    // Initializes optional string fields with empty String
    private static final String NOT_ASSIGNED = "";

    // Initializes due as default
    private static final LocalDate DEFAULT_DUE = LocalDate.MIN;

    // Initializes amount as default
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;

    // Initializes Priority as default
    private static final Priority DEFAULT_PRIORITY = Priority.MEDIUM;

    // Compulsory fields
    private String description;
    private LocalDate due;
    private BigDecimal amount;

    // Optional fields
    private String receiver;
    private String tag;
    private Priority priority;

    /**
     * Represents the Priority of the Payment.
     */
    public enum Priority {
        HIGH("High", 3),
        MEDIUM("Medium", 2),
        LOW("Low", 1);

        /**
         * Helps parse Priority parameter in user input.
         * Also defines how Priority is displayed in Ui.
         */
        private String nameShowed;

        // Makes Priority comparable.
        private int numeratedLevel;

        public String toString() {
            return nameShowed;
        }

        public int getNumeratedLevel() {
            return numeratedLevel;
        }

        Priority(String nameShowed, int numeratedLevel) {
            this.nameShowed = nameShowed;
            this.numeratedLevel = numeratedLevel;
        }
    }

    /**
     * A Builder class for Payment.
     * Enables construction with optional fields.
     */
    public static class Builder {

        // Compulsory fields
        private String description = NOT_ASSIGNED;
        private LocalDate due = DEFAULT_DUE;
        private BigDecimal amount = DEFAULT_AMOUNT;

        // Optional fields
        private String receiver = NOT_ASSIGNED;
        private String tag = NOT_ASSIGNED;
        private Priority priority = DEFAULT_PRIORITY;

        /**
         * Initializes a builder with all properties undefined.
         */
        public Builder() {

        }

        /**
         * Constructs a builder from an existing Payment.
         * This enables modification on an existing Payment with optional fields.
         *
         * @param payment the existing Payment
         */
        public Builder(Payment payment) {
            requireNonNull(payment);
            
            description = payment.description;
            receiver = payment.receiver;
            due = payment.due;
            tag = payment.tag;
            amount = payment.amount;
            priority = payment.priority;
        }

        /**
         * Sets the description in builder.
         * The {@code description} cannot be empty.
         *
         * @param description the description to set
         * @return The builder with the description set
         */
        public Builder setDescription(String description) {
            requireNonNull(description);
            assert !description.isEmpty();

            this.description = description;
            return this;
        }

        /**
         * Sets the receiver in builder.
         * The {@code receiver} cannot be empty.
         *
         * @param receiver the receiver to set
         * @return The builder with the receiver set
         */
        public Builder setReceiver(String receiver) {
            requireNonNull(receiver);
            assert !receiver.isEmpty();

            this.receiver = receiver;
            return this;
        }

        /**
         * Parses and sets the field due in builder.
         *
         * @param due String expected to follow format dd/mm/yyyy
         * @return a builder with the due already set
         * @throws DukeException if the String has incorrect time format
         */
        public Builder setDue(String due) throws DukeException {
            requireNonNull(due);
            assert !due.isEmpty();

            try {
                this.due = LocalDate.parse(due, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_TIME_INVALID, due));
            }
            return this;
        }

        /**
         * Sets the tag in builder.
         * The {@code tag} cannot be empty.
         *
         * @param tag the tag to set
         * @return The builder with the tag set
         */
        public Builder setTag(String tag) {
            requireNonNull(tag);
            assert !tag.isEmpty();

            this.tag = tag;
            return this;
        }

        /**
         * Parses and sets the field amount in builder.
         *
         * @param amount String expected to follow BigDecimal format
         * @return a builder with the amount already set
         * @throws DukeException if the String has incorrect BigDecimal format
         */
        public Builder setAmount(String amount) throws DukeException {
            requireNonNull(amount);
            assert !amount.isEmpty();

            try {
                this.amount = new BigDecimal(amount);
            } catch (NumberFormatException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_AMOUNT_INVALID, amount));
            }
            return this;
        }

        /**
         * Parses and sets the field priority in builder.
         *
         * @param priority String expected to follow Priority format
         * @return a builder with the priority already set
         * @throws DukeException if the String has incorrect Priority format
         */
        public Builder setPriority(String priority) throws DukeException {
            requireNonNull(priority);
            assert !priority.isEmpty();

            try {
                this.priority = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PRIORITY_NAME_INVALID, priority));
            }
            return this;
        }

        /**
         * Builds a Payment with given fields.
         *
         * @return the built Payment
         */
        public Payment build() {
            return new Payment(this);
        }
    }

    /**
     * Constructs a Payment with a builder.
     *
     * @param builder the builder containing fields information
     */
    public Payment(Builder builder) {
        requireNonNull(builder);

        description = builder.description;
        receiver = builder.receiver;
        due = builder.due;
        tag = builder.tag;
        amount = builder.amount;
        priority = builder.priority;
    }

    public String getDescription() {
        return description;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDate getDue() {
        return due;
    }

    public String getTag() {
        return this.tag;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getNumeratedPriority() {
        return priority.getNumeratedLevel();
    }

    /**
     * Tests whether any of description, receiver and tag contains the keyword.
     * The case of letter is ignored.
     *
     * @param keyword the keyword being searched
     * @return true if the keyword is found
     */
    public boolean containsKeyword(String keyword) {
        requireNonNull(keyword);
        assert !keyword.isEmpty();

        return description.toLowerCase().contains(keyword.toLowerCase())
                || receiver.toLowerCase().contains(keyword.toLowerCase())
                || tag.toLowerCase().contains(keyword.toLowerCase());
    }
}
