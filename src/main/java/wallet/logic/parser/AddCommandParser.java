package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.AddCommand;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddCommandParser implements Parser<AddCommand> {
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
            Expense expense = parseExpense(arguments[1]);
            if (expense != null) {
                return new AddCommand(expense);
            } else {
                break;
            }

        case "contact":
            Contact contact = parseContact(arguments[1]);
            if (contact != null) {
                return new AddCommand(contact);
            } else {
                break;
            }
        case "loan":
            Loan loan = parseLoan(arguments[1]);
            if (loan != null) {
                return new AddCommand(loan);
            } else {
                break;
            }
        default:
            System.out.println(AddCommand.MESSAGE_USAGE);
            return null;
        }
        return null;
    }

    /**
     * Returns an Expense object.
     *
     * @param input A string input.
     * @return The Expense object.
     * @throws NumberFormatException          Wrong format.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     */
    private Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        boolean isRecurring = input.contains("/r");
        String[] arguments = input.split("\\$");
        String desc = arguments[0].trim();
        arguments = arguments[1].split(" ", 2);
        Double amount = Double.parseDouble(arguments[0].trim());
        Category cat;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String freq = null;

        if (arguments[1].contains("/on")) {
            arguments = arguments[1].split(" ", 2);
            cat = Category.getCategory(arguments[0].trim());
            if (cat == null) {
                return null;
            }
            if (isRecurring) {
                arguments = arguments[1].split("/on");
                arguments = arguments[1].split("/r");
                date = LocalDate.parse(arguments[0].trim(), formatter);
                freq = arguments[1].trim().toUpperCase();
                if (!freq.equals("DAILY") && !freq.equals("WEEKLY") && !freq.equals("MONTHLY")) {
                    System.out.println(AddCommand.MESSAGE_ERROR_ADD_EXPENSE);
                    System.out.println(AddCommand.MESSAGE_USAGE);
                }
            } else {
                arguments = arguments[1].split("/on");
                date = LocalDate.parse(arguments[1].trim(), formatter);
            }
        } else {
            cat = Category.getCategory(arguments[1].trim());
            if (cat == null) {
                return null;
            }
        }
        Expense expense = new Expense(desc, date, amount, cat, isRecurring, freq);

        return expense;
    }

    /**
     * Returns a Loan object.
     *
     * @param input The string after "loan".
     * @return The Loan object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     * @throws ParseException                 ParseException.
     */
    private Loan parseLoan(String input) throws ArrayIndexOutOfBoundsException, ParseException {
        Loan loan = null;
        Boolean isLend = false;

        String[] info = input.split(" ", 4);
        String description = info[0];
        double amount = Double.parseDouble(info[1].replace("$", ""));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse(info[2].trim(), formatter);

        info = info[3].split("/c ");
        int contactId = Integer.parseInt(info[1]);

        if (input.contains("/l")) {
            isLend = true;
        } else if (input.contains("/b")) {
            isLend = false;
        }

        ArrayList<Contact> contactList = LogicManager.getWallet().getContactList().getContactList();
        int index = LogicManager.getWallet().getContactList().findIndexWithId(contactId);
        Contact person = new ContactList(contactList).getContact(index);
        loan = new Loan(description, createdDate, amount, isLend, false, person);
        return loan;
    }

    /**
     * Returns a Contact object.
     *
     * @param input The string after "contact".
     * @return The Contact object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     */
    private Contact parseContact(String input) throws ArrayIndexOutOfBoundsException {
        String[] info = input.split(" ");
        ContactParserHelper contactHelper = new ContactParserHelper();
        Contact contact = contactHelper.newInput(info);
        if (contact == null) {
            return null;
        } else {
            return contact;
        }
    }
}
