package wallet.logic.parser;

import wallet.model.contact.Contact;
import wallet.logic.command.AddCommand;
import wallet.model.record.Expense;
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
            System.out.println(AddCommand.MESSAGE_USAGE);
            return null;
        }
        return null;
    }

    private Expense parseExpense(String input) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        boolean isRecurring = input.contains("/r");
        String[] arguments = input.split("\\$");
        String desc = arguments[0].trim();
        arguments = arguments[1].split(" ", 2);
        Double amount = Double.parseDouble(arguments[0].trim());
        String cat;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String freq = "NULL";

        if (arguments[1].contains("/on")) {
            arguments = arguments[1].split(" ", 2);
            cat = arguments[0].trim();
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
            cat = arguments[1].trim();
        }
        Expense expense = new Expense(desc, date, amount, cat, isRecurring, freq);
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
