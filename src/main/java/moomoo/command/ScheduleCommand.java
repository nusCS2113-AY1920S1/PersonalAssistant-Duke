package moomoo.command;

import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Map;

/**
 * Represents the command to create a scheduled payment event in advance.
 */
public class ScheduleCommand extends Command {
    private static Pattern DATE_PATTERN = Pattern.compile(
            "^29/02/(0[48]|[2468][048]|[13579][26])$"//leap year for feb 29
            + "|^(0[1-9]|1[0-9]|2[0-8])/02/\\d{2}$"//28 days of feb
            + "|^(0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/\\d{2}$"//months with 31 days
            + "|^(0[1-9]|[12][0-9]|30)/(0[469]|11)/\\d{2}$");//months with 30 days
    private static Pattern AMOUNT_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public ScheduleCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        /**
         * List of scheduled payments
         */
        if (input.length() == 13 && input.contains("list")) {
            Iterator calIt = calendar.calendar.entrySet().iterator();
            String output = "Scheduled Payments\n";
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
            Map<Date, ArrayList<String>> sorted = new TreeMap<>();
            while (calIt.hasNext()) {
                Map.Entry entry = (Map.Entry)calIt.next();
                String sdate = (String) entry.getKey();
                try {
                    Date date = formatter.parse(sdate);
                    @SuppressWarnings("unchecked")
                    ArrayList<String> schedules = (ArrayList<String>) entry.getValue();
                    sorted.put(date,schedules);
                } catch (ParseException e) {
                    System.out.println("Invalid date input format.");
                }
            }

            Iterator sortIt = sorted.entrySet().iterator();
            while (sortIt.hasNext()) {
                Map.Entry entry = (Map.Entry)sortIt.next();
                Date date = (Date) entry.getKey();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
                String sdate = df.format(date);
                output += sdate + "\n";
                @SuppressWarnings("unchecked")
                ArrayList<String> schedules = (ArrayList<String>) entry.getValue();
                for (int i = 0; i < schedules.size(); i++) {
                    output += schedules.get(i) + "\n";
                }
                output += "\n";
            }
            Ui.setOutput(output);
            Ui.showResponse();
            return;
        }
        if (input.length() < 8) {
            throw new MooMooException("OOPS!!! To create a schedule payment, "
                    + "please indicate the d/<date in dd/mm/yyyy> a/<amount> n/<description of payment>.");
        }
        input = input.substring(8);
        if (!input.contains("d/")) {
            throw new MooMooException("MOOOO!!! Please indicate the scheduled date using d/<dd/mm/yy>");
        } else if (!input.contains("a/")) {
            throw new MooMooException("MOOOO!!! Please indicate the amount to be paid using a/<amount>");
        } else if (!input.contains("n/")) {
            throw new MooMooException("MOOOO!!! Please indicate the type of payment using n/<description of payment>");
        }

        /*
        add scheduled tasks on a particular date to a list
         */
        String[] arr = input.split(" ", 4);
        String task;
        String date;
        task = arr[3];
        date = arr[1];
        date = date.replace("d/", "");
        if (!dateMatches(date)) {
            throw new MooMooException("MOOO!!! Invalid date input!\n"
                    + "Check if your month is within 01-12.\n"
                    + "Check if your day input is valid for that month.\n"
                    + "Check if your year is a leap year if your day is Feb 29.");
        }
        String amount;
        amount = arr[2];
        amount = amount.replace("a/", "");
        if (!amountMatches(amount)) {
            throw new MooMooException("MOOO!!! Only numbers accepted for amount.");
        }
        task = task.replace("n/", "") + " " + amount;
        if (calendar.calendar.containsKey(date)) {
            ArrayList<String> dayTasks = calendar.calendar.get(date);
            dayTasks.add(task);
            calendar.calendar.replace(date, dayTasks);
        } else {
            calendar.addToCalendar(date, task);
        }
        String blankSpace = " ";
        int blank = 31 - date.length();
        for (int j = 1; j <= blank; j++) {
            blankSpace += " ";
        }
        String[] money = task.split(" ");
        int index = money.length - 1;
        task = money[0];
        for (int i = 1; i < index; i++) {
            task += " " + money[i];
        }
        String blank2 = " ";
        blank = 31 - task.length();
        for (int j = 0; j < blank; j++) {
            blank2 += " ";
        }
        String dollar = money[index];
        if (task.length() >= 31) {
            task = task.substring(0,28) + "...";
        }
        String blank3 = " ";
        blank = 29 - dollar.length();
        for (int i = 0; i < blank; i++) {
            blank3 += " ";
        }
        String cow =
                "._______________________________________.\n"
                + "| ___  ___ _  _ ___ ___  _   _ _    ___ |\n"
                + "|/ __|/ __| || | __|   \\| | | | |  | __||\n"
                + "|\\__ \\ (__| __ | _|| |) | |_| | |__| _| |\n"
                + "||___/\\___|_||_|___|___/ \\___/|____|___||\n"
                + "|                                       |\n"
                + "|Date : " + date + blankSpace + "|\n"
                + "|Task : " + task + blank2 + "|\n"
                + "|Amount : " + dollar + blank3 + "|\n"
                + ".---------------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";
        Ui.setOutput(cow);
        storage.saveScheduleToFile(calendar);
    }

    public boolean dateMatches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

    public boolean amountMatches(String amount) {
        return AMOUNT_PATTERN.matcher(amount).matches();
    }
}
