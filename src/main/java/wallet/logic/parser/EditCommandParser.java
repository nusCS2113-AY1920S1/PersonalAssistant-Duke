package wallet.logic.parser;

import wallet.logic.command.EditCommand;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The EditCommandParser class helps to
 * change user input String into appropriate parameters.
 */
public class EditCommandParser implements Parser<EditCommand> {

    @Override
    public EditCommand parse(String input) {
        String[] arguments = input.split(" ", 2);
        switch (arguments[0]) {
        case "expense":
            Expense expense = parseExpense(arguments[1]);
            if (expense != null) {
                return new EditCommand(expense);
            } else {
                break;
            }

        case "loan":
            break;

        case "contact":
            Contact contact = parseContact(arguments[1]);
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
    private Contact parseContact(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {

        String[] arguments = input.split(" ", 2);
        if (arguments.length == 2) {

            String[] parameters = arguments[1].split(" ");
            try {
                int id = Integer.parseInt(arguments[0].trim());
                ContactParserHelper contactHelper = new ContactParserHelper();
                Contact contact = contactHelper.updateInput(parameters);
                contact.setId(id);
                return contact;
            } catch (NumberFormatException e) {
                return null;
            }

        }
        return null;

    }

    /**
     * Parses the parameters of expense to be edited.
     */
    public Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        Expense expense = new Expense();

        String[] arguments = input.split(" ", 2);
        int id = Integer.parseInt(arguments[0].trim());
        expense.setId(id);
        String parameters = arguments[1].trim();
        if (parameters.contains("/r")) {
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
        }
        if (parameters.contains("/t")) {
            String[] getDate = parameters.split("/t");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
            expense.setDate(date);
            parameters = getDate[0].trim();
        }
        if (parameters.contains("/c")) {
            String[] getCategory = parameters.split("/c");
            expense.setCategory(Category.getCategory(getCategory[1].trim()));
            parameters = getCategory[0].trim();
        }
        if (parameters.contains("/a")) {
            String[] getAmount = parameters.split("/a");
            expense.setAmount(Double.parseDouble(getAmount[1].trim()));
            parameters = getAmount[0].trim();
        }
        if (parameters.contains("/d")) {
            String[] getDescription = parameters.split("/d");
            expense.setDescription(getDescription[1].trim());
        }

        return expense;
    }
}
