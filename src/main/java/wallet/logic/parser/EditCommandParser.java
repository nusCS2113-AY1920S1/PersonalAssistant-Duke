package wallet.logic.parser;

import wallet.exception.InsufficientParameters;
import wallet.exception.WrongDateTimeFormat;
import wallet.exception.WrongParameterFormat;
import wallet.logic.LogicManager;
import wallet.logic.command.EditCommand;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.RecurrenceRate;
import wallet.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The EditCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class EditCommandParser implements Parser<EditCommand> {
    public static final String MESSAGE_ERROR_EDIT_CONTACT = "Error in command syntax when editing contact.";
    public static final String MESSAGE_ERROR_NOT_NUMBER = "Error when parsing number, please provide proper input.";
    public static final String MESSAGE_ERROR_WRONG_DATE_FORMAT = "Error when parsing date, format is \"dd/MM/yyyy\".";
    public static final String MESSAGE_ERROR_INVALID_CATEGORY =
            "Category can only be Bills, Food, Others, Shopping or Transport.";
    public static final String MESSAGE_ERROR_INVALID_RECURRENCE_RATE = "Invalid value for rate of recurrence. "
            + "Acceptable values are: Daily, Weekly, Monthly or No";
    public static final String MESSAGE_ERROR_NEGATIVE_AMOUNT = "Amount should only be positive values.";

    @Override
    public EditCommand parse(String input) throws InsufficientParameters {
        String[] arguments = input.split(" ", 2);
        switch (arguments[0].toLowerCase()) {
        case "expense":
            Expense expense;
            try {
                expense = parseExpense(arguments[1]);
            } catch (DateTimeParseException dt) {
                throw new WrongDateTimeFormat(MESSAGE_ERROR_WRONG_DATE_FORMAT);
            } catch (NumberFormatException nf) {
                if (nf.toString().equals(MESSAGE_ERROR_INVALID_CATEGORY)) {
                    throw new WrongParameterFormat(MESSAGE_ERROR_INVALID_CATEGORY);
                } else {
                    throw new WrongParameterFormat(MESSAGE_ERROR_NOT_NUMBER);
                }
            }
            if (expense != null) {
                return new EditCommand(expense);
            } else {
                break;
            }

        case "loan":
            Loan loan;

            loan = parseLoan(arguments[1]);

            if (loan != null) {
                return new EditCommand(loan);
            }
            break;

        case "contact":
            Contact contact;

            contact = parseContact(arguments[1]);

            if (contact != null) {
                return new EditCommand(contact);
            } else {
                break;
            }

        default:
            Ui.printError(EditCommand.MESSAGE_USAGE);
            return null;
        }
        return null;
    }

    /**
     * Parses the parameters of contact to be edited.
     *
     * @param input User input arguments
     */
    public Contact parseContact(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        //@@author Xdecosee

        String[] parameters = input.split(" ");

        try {

            ContactParserHelper contactHelper = new ContactParserHelper();
            Contact contact = contactHelper.updateInput(parameters);
            if (contact == null) {
                Ui.printError(MESSAGE_ERROR_EDIT_CONTACT);
                return null;
            }

            return contact;
        } catch (NumberFormatException e) {
            Ui.printError(MESSAGE_ERROR_EDIT_CONTACT);
            return null;
        }

        //@@author

    }

    /**
     * Parses the parameters of contact to be edited.
     *
     * @param input User input arguments
     */
    Loan parseLoan(String input) throws NumberFormatException, InsufficientParameters {
        //@@author A0171206R
        Loan loan = new Loan();
        int loanId;
        boolean isValid = true;

        String[] arguments = input.split(" ", 2);
        String[] check = input.split(" ");
        try {
            loanId = Integer.parseInt(arguments[0].trim());
        } catch (NumberFormatException err) {
            throw new WrongParameterFormat("You need to provide a valid ID (Number) when editing your loans!");
        }

        loan.setId(loanId);
        String parameters = arguments[1].trim();

        Wallet wallet = LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState());

        int index = wallet.getLoanList().findIndexWithId(loanId);
        Loan currentLoan = LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList()
                .getState()).getLoanList().getLoan(index);
        loan.setId(currentLoan.getId());
        loan.setDescription(currentLoan.getDescription());
        loan.setIsSettled(currentLoan.getIsSettled());
        loan.setAmount(currentLoan.getAmount());
        loan.setPerson(currentLoan.getPerson());
        loan.setIsLend(currentLoan.getIsLend());
        loan.setDate(currentLoan.getDate());

        if (parameters.contains("/c")) {
            String[] getContact = parameters.split("/c");
            int contactId = Integer.parseInt(getContact[1].trim());
            for (Contact contact : LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList()
                    .getState()).getContactList().getContactList()) {
                if (contact.getId() == contactId) {
                    System.out.println("Edit: Contact found! " + contact.toString());
                    loan.setPerson(contact);
                    break;
                }
            }
            parameters = getContact[0].trim();
        }
        if (parameters.contains("/l")) {
            loan.setIsLend(true);
            if (parameters.equals("/l")) {
                return loan;
            }
            String[] getIsLend = parameters.split("/l");
            parameters = getIsLend[0].trim();
        } else if (parameters.contains("/b")) {
            loan.setIsLend(false);
            if (parameters.equals("/b")) {
                return loan;
            }
            String[] getIsLend = parameters.split("/b");
            parameters = getIsLend[0].trim();
        }
        if (parameters.contains("/t")) {
            String[] getDate = parameters.split("/t");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate createdDate = LocalDate.parse(getDate[1].trim(), formatter);
            loan.setDate(createdDate);
            parameters = getDate[0].trim();
        }
        if (parameters.contains("/a")) {
            String[] getAmount = parameters.split("/a");
            double amount = Double.parseDouble(getAmount[1].trim());
            loan.setAmount(amount);
            parameters = getAmount[0].trim();
        }
        if (parameters.contains("/d")) {
            String[] getDescription = parameters.split("/d");
            String description = getDescription[1].trim();
            loan.setDescription(description);
        }
        return loan;
        //@@author
    }

    /**
     * Parses the parameters of expense to be edited.
     */
    public Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        //@@author kyang96
        Expense expense = new Expense();

        String[] arguments = input.split(" ", 2);
        int id = Integer.parseInt(arguments[0].trim());
        expense.setId(id);
        String parameters = arguments[1].trim();
        int recIndex = parameters.indexOf("/r");
        int catIndex = parameters.indexOf("/c");
        int amtIndex = parameters.indexOf("/a");
        int dateIndex = parameters.indexOf("/t");
        int descIndex = parameters.indexOf("/d");
        if (recIndex != -1) {
            if (recIndex > catIndex && recIndex > amtIndex && recIndex > dateIndex && recIndex > descIndex) {
                String[] getRecurring = parameters.split("/r");
                RecurrenceRate rec = RecurrenceRate.getRecurrence(getRecurring[1].trim());
                if (rec == RecurrenceRate.DAILY || rec == RecurrenceRate.WEEKLY || rec == RecurrenceRate.MONTHLY) {
                    expense.setRecurring(true);
                    expense.setRecFrequency(rec);
                } else if (rec == RecurrenceRate.NO) {
                    expense.setRecurring(false);
                    expense.setRecFrequency(rec);
                } else {
                    throw new WrongParameterFormat(MESSAGE_ERROR_INVALID_RECURRENCE_RATE);
                }
                parameters = getRecurring[0].trim();
            } else {
                throw new WrongParameterFormat(EditCommand.MESSAGE_ERROR_FORMAT);
            }
        }
        if (catIndex != -1) {
            if (catIndex > amtIndex && catIndex > dateIndex && catIndex > descIndex) {
                String[] getCategory = parameters.split("/c");
                if (Category.getCategory(getCategory[1].trim()) == null) {
                    throw new WrongParameterFormat(MESSAGE_ERROR_INVALID_CATEGORY);
                }
                expense.setCategory(Category.getCategory(getCategory[1].trim()));
                parameters = getCategory[0].trim();
            } else {
                throw new WrongParameterFormat(EditCommand.MESSAGE_ERROR_FORMAT);
            }
        }
        if (amtIndex != -1) {
            if (amtIndex > dateIndex && amtIndex > descIndex) {
                String[] getAmount = parameters.split("/a");
                if (Double.parseDouble(getAmount[1].trim()) < 0) {
                    throw new WrongParameterFormat(MESSAGE_ERROR_NEGATIVE_AMOUNT);
                }
                expense.setAmount(Double.parseDouble(getAmount[1].trim()));
                parameters = getAmount[0].trim();
            } else {
                throw new WrongParameterFormat(EditCommand.MESSAGE_ERROR_FORMAT);
            }
        }
        if (dateIndex != -1) {
            if (dateIndex > descIndex) {
                String[] getDate = parameters.split("/t");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
                expense.setDate(date);
                parameters = getDate[0].trim();
            } else {
                throw new WrongParameterFormat(EditCommand.MESSAGE_ERROR_FORMAT);
            }
        }
        if (descIndex != -1) {
            String[] getDescription = parameters.split("/d");
            expense.setDescription(getDescription[1].trim());
        }
        return expense;
    }
}
