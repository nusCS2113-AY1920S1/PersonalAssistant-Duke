package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author e0318465-unused
/**
 * Code is not used as we are narrowing down the number of features in our program to only
 * include the main features that the user may use inside the program.
 *
 * Represents a recursive task that stores the same description but across different dates.
 */
public class Repeat extends Task {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MINUS_ONE = -1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int TWENTY_ONE = 21;
    private static final int TWENTY_TWO = 22;
    private static final int TWENTY_THREE = 23;
    private static final int THIRTY_ONE = 31;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    protected Date from;
    protected String[] suf = { "st", "nd", "rd", "th" };
    protected SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Creates a repeated task with the description of task and date/time.
     *
     * @param description The description of the task.
     * @param from The date/time of the task.
     * @throws ParseException If there is an error converting the date/time.
     */
    public Repeat(String description, String from) throws ParseException {
        super(description);
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(from);
            this.from = dateTime;
        } catch (ParseException e) {
            logr.log(Level.WARNING,"Error reading date/time, please use this format \"d/MM/yyyy HHmm\"", e);
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        SimpleDateFormat datetimeFormat2 = new SimpleDateFormat("MMMMM yyyy, h:mm a");
        SimpleDateFormat datetimeFormat3 = new SimpleDateFormat("MMMMM yyyy, ha");
        String displayDT = "";

        int day = Integer.parseInt(new SimpleDateFormat("d").format(from));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(from));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(from);
        } else {
            displayDT = datetimeFormat3.format(from);
        }
        int sufIndex = MINUS_ONE;

        if (day == ONE || day == TWENTY_ONE || day == THIRTY_ONE) {
            sufIndex = ZERO;
        } else if (day == TWO || day == TWENTY_TWO) {
            sufIndex = ONE;
        } else if (day == THREE || day == TWENTY_THREE) {
            sufIndex = TWO;
        } else if (day > THREE && day < THIRTY_ONE) {
            sufIndex = THREE;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[R]" + super.toString() + " (Last day of schedule: " + displayDT + ")";
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String to be displayed.
     */
    @Override
    public String toStringGui() {
        SimpleDateFormat datetimeFormat2 = new SimpleDateFormat("MMMMM yyyy, h:mm a");
        SimpleDateFormat datetimeFormat3 = new SimpleDateFormat("MMMMM yyyy, ha");
        String displayDT = "";

        int day = Integer.parseInt(new SimpleDateFormat("d").format(from));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(from));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(from);
        } else {
            displayDT = datetimeFormat3.format(from);
        }
        int sufIndex = MINUS_ONE;

        if (day == ONE || day == TWENTY_ONE || day == THIRTY_ONE) {
            sufIndex = ZERO;
        } else if (day == TWO || day == TWENTY_TWO) {
            sufIndex = ONE;
        } else if (day == THREE || day == TWENTY_THREE) {
            sufIndex = TWO;
        } else if (day > THREE && day < THIRTY_ONE) {
            sufIndex = THREE;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[R]" + super.toStringGui() + " (Last day of schedule: " + displayDT + ")";
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date.
     */
    @Override
    public String getDateTime() {
        String datetimeStr = datetimeFormat.format(from);
        return datetimeStr;
    }

    /**
     * Sets the date of the task.
     */
    @Override
    public void setDateTime(String from) throws Exception {
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(from);
            this.from = dateTime;
        } catch (ParseException e) {
            logr.log(Level.WARNING,"Error reading date/time, please use this format \"d/MM/yyyy HHmm\"", e);
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        String datetimeStr = datetimeFormat.format(from);
        return "R|" + super.toFile() + "|" + datetimeStr;
    }
}

/**
 * Represents a command that adds multiple tasks.
 */
//public class AddMultipleCommand extends Command {
//    protected ArrayList<Task> tasks;
//
//    /**
//     * Creates a command with the specified task list.
//     *
//     * @param tasks The array list of tasks.
//     */
//    public AddMultipleCommand(ArrayList<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//    /**
//     * Executes a command that adds the tasks into task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that they are added successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        for (Task curTask : tasks) {
//            items.add(curTask);
//        }
//        ui.showAdd(items);
//    }
//
//    /**
//     * Executes a command that adds the tasks and priority into task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param priorities Priority level of task.
//     * @param ui To tell the user that it is executed successfully.
//     */
//    public void execute(TaskList items, PriorityList priorities, Ui ui) {
//        for (Task curTask : tasks) {
//            items.add(curTask);
//        }
//        ui.showAdd(items);
//        priorities.addMultiDefaultPriority(tasks.size());
//    }
//
//    /**
//     * Executes a command that adds the tasks into task list and outputs the result (GUI).
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that they are added successfully.
//     * @return String to be outputted to the user.
//     */
//    @Override
//    public String executeGui(TaskList items, Ui ui) {
//        for (Task curTask : tasks) {
//            items.add(curTask);
//        }
//
//        String str = Ui.showAddGui(items);
//        return str;
//    }
//
//    /**
//     * Executes a command that overwrites existing storage with the updated task list.
//     * (Not in use)
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is executed successfully.
//     * @param storage The storage to be overwritten.
//     */
//    @Override
//    public void executeStorage(TaskList items, Ui ui, Storage storage) {
//    }
//}

/**
 * Code snippet from Parser
 */
//        else if (arr.length > ZERO && (arr[ZERO].equals("repeat") || arr[ZERO].equals("rep"))) {
//            //repeat <task> /from <date time> /for 3 <day/week/month>
//            for (int i = ONE; i < arr.length; i++) {
//                if ((arr[i].trim().isEmpty() || !arr[i].substring(ZERO, ONE).equals("/")) && !getDate) {
//                    taskDesc += arr[i] + " ";
//                } else {
//                    if (!getDate) { //detect "/"
//                        getDate = true;
//                    } else {
//                        dateDesc += arr[i] + " ";
//                    }
//                }
//            }
//            taskDesc = taskDesc.trim();
//            dateDesc = dateDesc.trim();
//
//            if (taskDesc.isEmpty()) {
//                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[ZERO] + " cannot be empty.");
//            } else if (dateDesc.isEmpty()) {
//                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
//                        + arr[ZERO] + " cannot be empty.");
//            } else {
//                String repeatSettings;
//                int repeatTimes;
//                String repeatPeriod;
//                try {
//                    repeatSettings = dateDesc.split("/for ")[ONE];
//                    repeatTimes = Integer.parseInt(repeatSettings.replaceAll("[\\D]", ""));
//                    repeatPeriod = repeatSettings.split(repeatTimes + " ")[ONE];
//
//                } catch (Exception e) {
//                    logr.log(Level.WARNING,"Format is in: repeat <task> /from <date time> "
//                            + "/for <repeat times> <days/weeks>", e);
//                    throw new DukeException("Format is in: repeat <task> /from <date time> "
//                            + "/for <repeat times> <days/weeks>");
//                }
//
//                ArrayList<Task> repeatList = new ArrayList<>();
//                String timeDesc = dateDesc.split(" ", THREE)[ONE];
//                for (int i = ZERO; i < repeatTimes; i++) {
//                    Task taskObj;
//                    taskObj = new Repeat(taskDesc, dateDesc);
//                    dateDesc = DateParser.add(dateDesc, repeatPeriod) + " " + timeDesc;
//                    repeatList.add(taskObj);
//
//                    for (int j = ZERO; j < items.size(); j++) {
//                        if (taskObj.getDateTime().equals(items.get(j).getDateTime()) && !items.get(j).isDone()) {
//                            throw new DukeException("     (>_<) OOPS!!! The date/time for "
//                                    + arr[ZERO] + " clashes with " + items.get(j).toString()
//                                    + "\n     Please choose another date/time! Or mark the above task as Done first!");
//                        }
//                    }
//                }
//                return new AddMultipleCommand(repeatList);
//            }
//        }

/**
 * Test Case fot Repeat is part of ParserTest.
 * Just add the Command line under the list that tests for other commands as well.
 */
//class ParserTest {
//    Ui ui = new Ui();
//    TaskList items = new TaskList();
//    BudgetList budgetList = new BudgetList();
//    ContactList contactList = new ContactList();
//
//    @Test
//    void parserTest() throws Exception {
//        Task task = new Todo("Hi");
//        Task task2 = new Todo("there");
//        items.add(task);
//        items.add(task2);
//
//        Command cmd = Parser.parse("repeat this /from 10/05/2019 1234 /for 3 days", items, budgetList, contactList);
//        assertTrue(cmd instanceof AddMultipleCommand);
//    }
//}