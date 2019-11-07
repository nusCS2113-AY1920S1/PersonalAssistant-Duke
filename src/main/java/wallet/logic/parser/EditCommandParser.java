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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The EditCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class EditCommandParser implements Parser<EditCommand> {
    public static final String MESSAGE_ERROR_EDIT_CONTACT = "Error in input format when editing contact.";
    public static final String MESSAGE_ERROR_NOT_NUMBER = "Error when parsing number, please provide proper input.";
    public static final String MESSAGE_ERROR_WRONG_DATE_FORMAT = "Error when parsing date, format is \"dd/MM/yyyy\".";

    @Override
    public EditCommand parse(String input) throws InsufficientParameters {
        String[] arguments = input.split(" ", 2);
        switch (arguments[0]) {
        case "expense":
            Expense expense;
            try {
                expense = parseExpense(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("There are no arguments when editing the expense!");
            } catch (NumberFormatException nf) {
                throw new WrongParameterFormat(MESSAGE_ERROR_NOT_NUMBER);
            } catch (DateTimeParseException dt) {
                throw new WrongDateTimeFormat(MESSAGE_ERROR_WRONG_DATE_FORMAT);
            }
            if (expense != null) {
                return new EditCommand(expense);
            } else {
                break;
            }

        case "loan":
            Loan loan;
            try {
                loan = parseLoan(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("There are no arguments when editing the loan!");
            }
            if (loan != null) {
                return new EditCommand(loan);
            }
            break;

        case "contact":
            Contact contact;
            try {
                contact = parseContact(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("There are no arguments when editing the contact!");
            }
            if (contact != null) {
                return new EditCommand(contact);
            } else {
                break;
            }

        default:
            System.out.println(EditCommand.MESSAGE_USAGE);
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
        String[] arguments = input.split(" ", 2);
        if (arguments.length == 2) {

            String[] parameters = arguments[1].split(" ");
            try {
                int id = Integer.parseInt(arguments[0].trim());
                ContactParserHelper contactHelper = new ContactParserHelper();
                Contact contact = contactHelper.updateInput(parameters);
                if (contact == null) {
                    System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
                    return null;
                }
                contact.setId(id);
                return contact;
            } catch (NumberFormatException e) {
                System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
                return null;
            }

        }
        System.out.println(MESSAGE_ERROR_EDIT_CONTACT);
        return null;
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
        int dateIndex = parameters.indexOf("/t");
        int catIndex = parameters.indexOf("/c");
        int amtIndex = parameters.indexOf("/a");
        int descIndex = parameters.indexOf("/d");
        if (recIndex != -1) {
            if (recIndex > dateIndex && recIndex > catIndex && recIndex > amtIndex && recIndex > descIndex) {
                String[] getRecurring = parameters.split("/r");
                if (getRecurring[1].trim().equalsIgnoreCase("DAILY")
                        || getRecurring[1].trim().equalsIgnoreCase("WEEKLY")
                        || getRecurring[1].trim().equalsIgnoreCase("MONTHLY")) {
                    expense.setRecurring(true);
                    expense.setRecFrequency(getRecurring[1].trim().toUpperCase());
                } else if (getRecurring[1].trim().equals("no")) {
                    expense.setRecurring(false);
                    expense.setRecFrequency(null);
                }
                parameters = getRecurring[0].trim();
            } else {
                System.out.println(EditCommand.MESSAGE_ERROR_FORMAT);
                return null;
            }
        }
        if (dateIndex != -1) {
            if (dateIndex > catIndex && dateIndex > amtIndex && dateIndex > descIndex) {
                String[] getDate = parameters.split("/t");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
                expense.setDate(date);
                parameters = getDate[0].trim();
            } else {
                System.out.println(EditCommand.MESSAGE_ERROR_FORMAT);
                return null;
            }
        }
        if (catIndex != -1) {
            if (catIndex > amtIndex && catIndex > descIndex) {
                String[] getCategory = parameters.split("/c");
                expense.setCategory(Category.getCategory(getCategory[1].trim()));
                parameters = getCategory[0].trim();
            } else {
                System.out.println(EditCommand.MESSAGE_ERROR_FORMAT);
                return null;
            }
        }
        if (amtIndex != -1) {
            if (amtIndex > descIndex) {
                String[] getAmount = parameters.split("/a");
                expense.setAmount(Double.parseDouble(getAmount[1].trim()));
                parameters = getAmount[0].trim();
            } else {
                System.out.println(EditCommand.MESSAGE_ERROR_FORMAT);
                return null;
            }
        }
        if (descIndex != -1) {
            String[] getDescription = parameters.split("/d");
            expense.setDescription(getDescription[1].trim());
        }
        return expense;
    }
}
