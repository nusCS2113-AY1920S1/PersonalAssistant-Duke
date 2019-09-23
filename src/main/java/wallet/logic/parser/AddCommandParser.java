package wallet.logic.parser;

import wallet.logic.command.AddCommand;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Deadline;
import wallet.model.task.DoWithinPeriod;
import wallet.model.task.Event;
import wallet.model.task.Task;
import wallet.model.task.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        case "todo":
        case "event":
        case "deadline":
        case "dowithin":
            Task task = parseTask(arguments[0], arguments[1]);
            if (task != null) {
                return new AddCommand(task);
            } else {
                break;
            }

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
            return null;
        }
        return null;
    }

    /**
     * Returns an Expense object.
     *
     * @param input A string input.
     * @return The Expense object.
     * @throws NumberFormatException Wrong format.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     */
    private Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        Expense expense = null;

        boolean isRecurring = input.contains("/r");
        if (isRecurring) {
            String[] getRec = input.split("/r");
            String freq = getRec[1].trim().toUpperCase();
            String[] getCat = getRec[0].split("/cat");
            String cat = getCat[1].trim();
            String[] getDate = getCat[0].split("/on");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
            String[] getDesc = getDate[0].split("\\$");

            if (freq.equals("DAILY") || freq.equals("WEEKLY") || freq.equals("MONTHLY")) {
                expense = new Expense(getDesc[0].trim(), date, Double.parseDouble(getDesc[1].trim()), cat, true, freq);
            } else {
                System.out.println("☹ OOPS!!! The options for recurrence (/r) are \"daily, weekly or monthly\"");
            }
        } else {
            String[] getCat = input.split("/cat");
            String cat = getCat[1].trim();
            String[] getDate = getCat[0].split("/on");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
            String[] getDesc = getDate[0].split("\\$");

            expense = new Expense(getDesc[0].trim(), date, Double.parseDouble(getDesc[1].trim()), cat, false, "NULL");
        }

        return expense;
    }

    /**
     * Returns a Loan object.
     *
     * @param input The string after "loan".
     * @return The Loan object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     * @throws ParseException ParseException.
     */
    private Loan parseLoan(String input) throws ArrayIndexOutOfBoundsException, ParseException {
        Loan loan = null;
        Boolean isLend = false;

        String[] info = input.split(" ", 4);
        String description = info[0];
        double amount = Double.parseDouble(info[1].replace("$",""));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse(info[2].trim(), formatter);

        if (info[3].equals("/l")) {
            isLend = true;
        } else if (info[3].equals("/b")) {
            isLend = false;
        }
        loan = new Loan(description, createdDate, amount, isLend, false);
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
        Contact contact = null;

        String[] info = input.split(" ", 3);
        contact = new Contact(info[0], info[1], info[2]);

        return contact;
    }

    /**
     * Returns a Task Object based on command and input.
     *
     * @param command A string command.
     * @param input A string input.
     * @return The Task object.
     * @throws ArrayIndexOutOfBoundsException Out of index.
     * @throws ParseException ParseException.
     */
    private Task parseTask(String command, String input) throws ArrayIndexOutOfBoundsException, ParseException {
        Task task = null;

        String[] info;

        if (input.length() == 0) {
            System.out.println("☹ OOPS!!! The description of " + command + " cannot be empty");
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        if (command.equals("todo")) {
            task = new Todo(input);
        } else if (command.equals("deadline")) {
            info = input.split("/by");
            Date date = sdf.parse(info[1].trim());
            task = new Deadline(info[0].trim(), date);
        } else if (command.equals("event")) {
            info = input.split("/at");
            Date date = sdf.parse(info[1].trim());
            task = new Event(info[0].trim(), date);
        } else if (command.equals("dowithin")) {
            info = input.split("/from");
            String temp = info[0];
            info = info[1].split("/to");
            Date dateStart = sdf.parse(info[0].trim());
            Date dateEnd = sdf.parse(info[1].trim());
            task = new DoWithinPeriod(temp.trim(), dateStart, dateEnd);
        }

        return task;
    }
}
