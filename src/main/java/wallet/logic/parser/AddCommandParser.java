package wallet.logic.parser;

import wallet.contact.Contact;
import wallet.logic.command.AddCommand;
import wallet.record.Expense;
import wallet.task.Deadline;
import wallet.task.DoWithinPeriod;
import wallet.task.Event;
import wallet.task.Task;
import wallet.task.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddCommandParser implements Parser<AddCommand> {

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

        default:

        }

        return null;
    }

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

    private Contact parseContact(String input) throws ArrayIndexOutOfBoundsException {
        Contact contact = null;

        String[] info = input.split(" ", 3);
        contact = new Contact(info[0], info[1], info[2]);

        return contact;
    }

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
