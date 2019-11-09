package wallet.logic.parser;

import wallet.exception.InsufficientParameters;
import wallet.exception.WrongDateTimeFormat;
import wallet.logic.LogicManager;
import wallet.logic.command.AddCommand;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.RecurrenceRate;
import wallet.ui.Ui;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AddCommandParser implements Parser<AddCommand> {
    public static final String MESSAGE_ERROR_ADD_CONTACT = "Error in input format when adding contact.";
    public static final String MESSAGE_ERROR_INSUFFICIENT_PARAMETERS = "There are insufficient parameters provided.";
    public static final String MESSAGE_ERROR_WRONG_DATETIME_FORMAT = "Wrong date format, use \"dd/MM/yyyy\".";
    public static final String MESSAGE_ERROR_INVALID_CATEGORY
            = "Category can only be Bills, Food, Others, Shopping or Transport.";
    public static final String MESSAGE_ERROR_INVALID_RECURRENCE_RATE
            = "Recurrence rate can only be Daily, Weekly or Monthly.";

    /**
     * Returns an AddCommand object.
     *
     * @param input User input of command.
     * @return An AddCommand object.
     * @throws ParseException ParseException.
     */
    @Override
    public AddCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);
        switch (arguments[0]) {
        case "expense":
            Expense expense;
            try {
                expense = parseExpense(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(MESSAGE_ERROR_INSUFFICIENT_PARAMETERS);
            } catch (DateTimeParseException dt) {
                throw new WrongDateTimeFormat(MESSAGE_ERROR_WRONG_DATETIME_FORMAT);
            }
            if (expense != null) {
                return new AddCommand(expense);
            } else {
                break;
            }

        case "contact":
            Contact contact;
            try {
                contact = parseContact(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(MESSAGE_ERROR_INSUFFICIENT_PARAMETERS);
            }
            if (contact != null) {
                return new AddCommand(contact);
            } else {
                break;
            }
        case "loan":
            Loan loan;
            try {
                loan = parseLoan(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(MESSAGE_ERROR_INSUFFICIENT_PARAMETERS);
            } catch (DateTimeParseException dt) {
                throw new WrongDateTimeFormat(MESSAGE_ERROR_WRONG_DATETIME_FORMAT);
            }
            if (loan != null) {
                return new AddCommand(loan);
            }
            break;
        default:
            System.out.println(AddCommand.MESSAGE_USAGE);
            return null;
        }
        return null;
    }

    //@@author kyang96
    /**
     * Parses user input and returns an Expense object with input values.
     *
     * @param input A string input.
     * @return The Expense object.
     * @throws NumberFormatException  Wrong format.
     * @throws InsufficientParameters Insufficient parameters provided.
     */
    public Expense parseExpense(String input) throws DateTimeParseException,
                                                NumberFormatException, InsufficientParameters {
        boolean isRecurring = input.contains("/r");
        String[] arguments = input.split("\\$");
        String desc = arguments[0].trim();
        arguments = arguments[1].split(" ", 2);
        Double amount = Double.parseDouble(arguments[0].trim());
        Category cat;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        RecurrenceRate freq = null;

        if (arguments[1].contains("/on")) {
            arguments = arguments[1].split(" ", 2);
            cat = Category.getCategory(arguments[0].trim());
            if (cat == null) {
                Ui.printError(MESSAGE_ERROR_INVALID_CATEGORY);
                return null;
            }
            if (isRecurring) {
                arguments = arguments[1].split("/on");
                arguments = arguments[1].split("/r");
                date = LocalDate.parse(arguments[0].trim(), formatter);
                freq = RecurrenceRate.getRecurrence(arguments[1].trim());
                if (freq == null) {
                    Ui.printError(MESSAGE_ERROR_INVALID_RECURRENCE_RATE);
                    return null;
                }
            } else {
                arguments = arguments[1].split("/on");
                date = LocalDate.parse(arguments[1].trim(), formatter);
                freq = RecurrenceRate.NO;
            }
        } else {
            cat = Category.getCategory(arguments[1].trim());
            if (cat == null) {
                Ui.printError(MESSAGE_ERROR_INVALID_CATEGORY);
                return null;
            }
            freq = RecurrenceRate.NO;
        }
        Expense expense = new Expense(desc, date, amount, cat, isRecurring, freq);

        return expense;
        //@@author
    }

    /**
     * Returns a Loan object.
     *
     * @param input The string after "loan".
     * @return The Loan object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     * @throws ParseException                 ParseException.
     */
    Loan parseLoan(String input) throws InsufficientParameters, ParseException {
        //@@author A0171206R
        Loan loan = null;
        Boolean isLend = false;

        String[] info = input.split("\\$", 2);
        String description = info[0].trim();
        info = info[1].split(" ", 4);
        double amount = Double.parseDouble(info[0].trim());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse(info[1].trim(), formatter);

        info = info[3].split("/c ");
        int contactId = Integer.parseInt(info[1].trim());

        if (input.contains("/l")) {
            isLend = true;
        } else if (input.contains("/b")) {
            isLend = false;
        }

        ArrayList<Contact> contactList = LogicManager.getWalletList().getWalletList()
                .get(LogicManager.getWalletList().getState()).getContactList().getContactList();
        int index = LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                .getContactList().findIndexWithId(contactId);
        Contact person = new ContactList(contactList).getContact(index);
        loan = new Loan(description, createdDate, amount, isLend, false, person);
        return loan;
        //@@author
    }

    /**
     * Returns a Contact object.
     *
     * @param input The string after "contact".
     * @return The Contact object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     */
    public Contact parseContact(String input) throws ArrayIndexOutOfBoundsException {
        //@@author Xdecosee
        String[] info = input.split(" ");
        ContactParserHelper contactHelper = new ContactParserHelper();
        Contact contact = contactHelper.newInput(info);
        if (contact == null) {
            System.out.println(MESSAGE_ERROR_ADD_CONTACT);
            return null;
        } else {
            return contact;
        }
        //@@author
    }
}
