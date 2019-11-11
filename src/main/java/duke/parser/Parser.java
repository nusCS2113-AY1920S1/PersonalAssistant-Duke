package duke.parser;

import duke.command.BackupCommand;
import duke.command.ExitCommand;
import duke.command.ListPriorityCommand;
import duke.command.UndoBudgetCommand;
import duke.command.Command;
import duke.command.SetPriorityCommand;
import duke.command.DeleteCommand;
import duke.command.FilterCommand;
import duke.command.FindTasksByPriorityCommand;
import duke.command.FindTasksByDateCommand;
import duke.command.ListCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.AddCommand;
import duke.command.ShowNotesCommand;
import duke.command.AddNotesCommand;
import duke.command.DeleteNotesCommand;
import duke.command.DuplicateFoundCommand;
import duke.command.HelpCommand;
import duke.command.UpdateCommand;
import duke.command.ViewBudgetCommand;
import duke.command.ResetBudgetCommand;
import duke.command.AddContactsCommand;
import duke.command.ListContactsCommand;
import duke.command.DeleteContactCommand;
import duke.command.FindContactCommand;
import duke.command.AddBudgetCommand;
import duke.dukeexception.DukeException;
import duke.enums.ErrorMessages;
import duke.enums.Numbers;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.BudgetList;
import duke.task.ContactList;
import duke.task.DetectDuplicate;
import duke.task.FixedDuration;
import duke.task.Contacts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a parser that breaks down user input into commands.
 */
public class Parser {

    private static final String EMPTY_STRING = "";
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static DateParser dateParser = new DateParser();

    //@@author maxxyx96
    /**
     * Trims the whitespaces of an input.
     *
     * @param input input to be trimmed.
     * @return Returns the trimmed input.
     */
    private static String trim(String input) {
        return input.trim();
    }

    /**
     * Checks whether the string input can be split by a set string.
     * @param input the input to test if it is splittable.
     * @param splitWith the characters to detect splitting.
     * @return returns true if it can be split, false otherwise.
     */
    private static boolean isSplittable(String input, String splitWith) {
        try {
            input = input.split(splitWith, 2)[1];
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Converts a string amount to look like what a typical monetary amount would look like..
     *
     * @param stringAmount The amount to be converted.
     * @return the converted amount with 2 decimal places.
     */
    private static String budgetAmountFormat(String stringAmount) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        float floatAmount = Float.parseFloat(stringAmount);
        return decimalFormat.format(floatAmount);
    }
    //@@author

    //@@author e0318465
    private static void checkForEmptyContactDetail(String[] contactDetails) {
        if (contactDetails[Numbers.ZERO.value].trim().isEmpty()) {
            contactDetails[Numbers.ZERO.value] = "Nil";
        }
        if (contactDetails[Numbers.ONE.value].trim().isEmpty()) {
            contactDetails[Numbers.ONE.value] = "Nil";
        }
        if (contactDetails[Numbers.TWO.value].trim().isEmpty()) {
            contactDetails[Numbers.TWO.value] = "Nil";
        }
        if (contactDetails[Numbers.THREE.value].trim().isEmpty()) {
            contactDetails[Numbers.THREE.value] = "Nil";
        }
    }

    //@@author
    /**
     * Generates a command based on the user input.
     *
     * @param sentence User input.
     * @param items The task list that contains a list of tasks.
     * @param budgetList The list that contains a list of budget.
     * @param contactList The list of Contacts.
     * @return Command to be executed afterwards.
     * @throws Exception  If there is an error interpreting the user input.
     */
    public static Command parse(String sentence, TaskList items, BudgetList budgetList,
                                ContactList contactList) throws Exception {
        String[] arr = sentence.split(" ");
        String taskDesc = "";
        String dateDesc = "";
        boolean emptyString = arr.length < Numbers.ZERO.value;
        boolean getDate = false;
        if (sentence.trim().isEmpty()) {
            throw new DukeException(ErrorMessages.UNKNOWN_COMMAND.message);
        } else if (sentence.contains("|")) {
            throw new DukeException(ErrorMessages.AVOID_PIPELINE.message);
        } else if (sentence.equals("list")) {
            return new ListCommand();
        } else if (sentence.equals("priority") || sentence.equals("listpriority") || sentence.equals("lp")) {
            return new ListPriorityCommand();
        } else if (!emptyString && (arr[Numbers.ZERO.value].equals("done")
                || arr[Numbers.ZERO.value].equals("delete"))) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException(ErrorMessages.TASKNUM_IS_EMPTY.message);
            } else {
                int tasknum;
                try {
                    tasknum = Integer.parseInt(arr[Numbers.ONE.value]) - Numbers.ONE.value;
                } catch (NumberFormatException e) {
                    logr.log(Level.WARNING,ErrorMessages.TASKNUM_MUST_BE_INT.message);
                    throw new  DukeException(ErrorMessages.TASKNUM_MUST_BE_INT.message);
                }
                if (tasknum < Numbers.ZERO.value || tasknum >= items.size()) {
                    throw new DukeException(ErrorMessages.TASKNUM_INVALID_INT.message);
                } else {
                    if (arr[Numbers.ZERO.value].equals("done")) {
                        if (items.get(tasknum).isDone()) {
                            throw new DukeException("     (>_<) OOPS!! This task has been marked as done!");
                        }
                        return new DoneCommand(tasknum);
                    } else {
                        return new DeleteCommand(tasknum);
                    }
                }
            } //@@author talesrune
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("find")) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException(ErrorMessages.KEYWORD_IS_EMPTY.message);
            } else {
                if (arr[Numbers.ONE.value].trim().isEmpty()) {
                    throw new DukeException(ErrorMessages.KEYWORD_IS_EMPTY.message);
                } else {
                    String keyword = EMPTY_STRING;
                    for (int i = Numbers.ONE.value; i < arr.length; i++) {
                        keyword += arr[i] + " ";
                    }
                    return new FindCommand(keyword.trim());
                }
            }
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("filter")) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException(ErrorMessages.TASKTYPE_IS_EMPTY.message);
            } else {
                if (arr[Numbers.ONE.value].trim().isEmpty()) {
                    throw new DukeException(ErrorMessages.TASKTYPE_IS_EMPTY.message);
                } else {
                    return new FilterCommand(arr[Numbers.ONE.value]);
                }
            }
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("notes")) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException(ErrorMessages.TASKNUM_IS_EMPTY.message);
            } else {
                int tasknum;
                try {
                    tasknum  = Integer.parseInt(arr[Numbers.ONE.value]) - Numbers.ONE.value;
                } catch (NumberFormatException e) {
                    logr.log(Level.WARNING,ErrorMessages.TASKNUM_MUST_BE_INT.message);
                    throw new DukeException(ErrorMessages.TASKNUM_MUST_BE_INT.message);
                }

                if (tasknum < Numbers.ZERO.value || tasknum >= items.size()) {
                    throw new DukeException(ErrorMessages.TASKNUM_INVALID_INT.message);
                } else if (arr.length < Numbers.THREE.value) {
                    throw new DukeException("     (>_<) OOPS!!! Insufficient parameters. "
                            + "Format: notes <tasknum> <type> <notes description>");
                } else {
                    int typeOfNotes = Numbers.MINUS_ONE.value;
                    String notesDesc = "";
                    for (int i = Numbers.TWO.value; i < arr.length; i++) {
                        if (i == Numbers.TWO.value) {
                            if (arr[i].trim().isEmpty()
                                    || (!arr[i].equals("/add") && !arr[i].equals("/delete")
                                    && !arr[i].equals("/show"))) {
                                throw new DukeException("     (>_<) OOPS!!! Unable to find either "
                                        + "/add, /delete, or /show.");
                            } else {
                                if (arr[i].equals("/add")) {
                                    typeOfNotes = Numbers.ONE.value;
                                } else if (arr[i].equals("/delete")) {
                                    typeOfNotes = Numbers.TWO.value;
                                    break;
                                } else {
                                    typeOfNotes = Numbers.THREE.value;
                                    break;
                                }
                            }
                        } else {
                            notesDesc += arr[i] + " ";
                        }
                    }
                    notesDesc = notesDesc.trim();
                    if (typeOfNotes == Numbers.THREE.value) {
                        return new ShowNotesCommand(tasknum);
                    } else if (typeOfNotes == Numbers.TWO.value) {
                        if (items.get(tasknum).getNotes().equals("empty")) {
                            throw new DukeException("     (>_<) OOPS!!! The notes description of "
                                    + items.get(tasknum).toString() + " is already empty!");
                        }
                        return new DeleteNotesCommand(tasknum);
                    } else if (typeOfNotes == Numbers.ONE.value && notesDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The notes description of "
                                + arr[Numbers.ZERO.value] + " cannot be empty.");
                    } else if (typeOfNotes != Numbers.MINUS_ONE.value) {
                        return new AddNotesCommand(notesDesc,tasknum);
                    } else {
                        throw new DukeException("     (>_<) OOPS!!! There is something wrong "
                                + " when trying to add notes");
                    }
                }
            }   //@@author
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("todo")) {
            String[] getDescription = sentence.split(" ", Numbers.TWO.value);
            DetectDuplicate detectDuplicate = new DetectDuplicate(items);
            for (int i = Numbers.ONE.value; i < arr.length; i++) {
                taskDesc += arr[i] + " ";
            }
            taskDesc = taskDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a "
                        + arr[Numbers.ZERO.value] +  " cannot be empty.");
            } else if (detectDuplicate.isDuplicate(getDescription[Numbers.ONE.value])) {
                return new DuplicateFoundCommand();
            } else {
                Task taskObj = new Todo(taskDesc);
                return new AddCommand(taskObj);

            }
        } else if (!emptyString && (arr[Numbers.ZERO.value].equals("deadline")
                || arr[Numbers.ZERO.value].equals("dl"))) {
            for (int i = Numbers.ONE.value; i < arr.length; i++) {
                if ((arr[i].trim().isEmpty()
                        || !arr[i].equals("/by")) && !getDate) {
                    taskDesc += arr[i] + " ";
                } else {
                    if (!getDate) {
                        getDate = true;
                    } else {
                        dateDesc += arr[i] + " ";
                    }
                }
            }
            taskDesc = taskDesc.trim();
            dateDesc = dateDesc.trim();
            DetectDuplicate detectDuplicate = new DetectDuplicate(items);
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            } else if (dateDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! Ensure that you have /by before writing date/time."
                        + " The description of date/time for "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            } else if (detectDuplicate.isDuplicate(taskDesc)) {
                return new DuplicateFoundCommand();
            } else {
                dateParser.isValidDateTime(dateDesc);
                Task taskObj;
                taskObj = new Deadline(taskDesc, dateDesc);

                for (int i = Numbers.ZERO.value; i < items.size(); i++) {
                    if (taskObj.getDateTime().equals(items.get(i).getDateTime()) && !items.get(i).isDone()) {
                        throw new DukeException("     (>_<) OOPS!!! The date/time for "
                                + arr[Numbers.ZERO.value] + " clashes with " + items.get(i).toString()
                                + "\n     Please choose another date/time! Or mark the above task as Done first!");
                    }
                }
                return new AddCommand(taskObj);
            }

            //@@author Dou-Maokang
        } else if (!emptyString
                && (arr[Numbers.ZERO.value].equals("fixedduration") || arr[Numbers.ZERO.value].equals("fd"))) {
            String description = "";
            int duration;
            String unit;
            for (int i = Numbers.ONE.value; i < arr.length; i++) {
                description += arr[i] + " ";
            }
            if (description.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            }
            String[] descarr = description.split(" /for ");
            if (descarr.length < 2) {
                throw new DukeException("     (>_<) OOPS!!! The duration description of a "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            }
            taskDesc = description.split(" /for ")[Numbers.ZERO.value].trim();
            String durDesc = description.split(" /for ")[Numbers.ONE.value].trim();
            DetectDuplicate detectDuplicate = new DetectDuplicate(items);

            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            } else if (durDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of duration for "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            } else if (detectDuplicate.isDuplicate(taskDesc)) {
                return new DuplicateFoundCommand();
            } else {
                try {
                    duration = Integer.parseInt(durDesc.split(" ")[Numbers.ZERO.value].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING, ErrorMessages.FIXEDDURATION_FORMAT.message);
                    throw new DukeException(ErrorMessages.FIXEDDURATION_FORMAT.message);
                }
                String[] durationarr = durDesc.split(" ");
                if (durationarr.length < 2) {
                    throw new DukeException("     (>_<) OOPS!!! The unit of a "
                            + arr[Numbers.ZERO.value] + " cannot be empty.");
                }

                unit = durDesc.split(" ")[Numbers.ONE.value].trim();
                if (unit.isEmpty() || (!unit.toLowerCase().contains("min") && !unit.toLowerCase().contains("h"))) {
                    throw new DukeException(ErrorMessages.FIXEDDURATION_FORMAT.message);
                } else if ((!unit.toLowerCase().equals("minute"))
                        && (!unit.toLowerCase().equals("minutes"))
                        && (!unit.toLowerCase().equals("hour"))
                        && (!unit.toLowerCase().equals("hours"))) {
                    throw new DukeException("     (>_<) OOPS!!! <unit> can only be minute(s) or hour(s)!");
                } else {
                    if (unit.equals("min") || unit.equals("minutes")) {
                        unit = (duration > Numbers.ONE.value) ? "minutes" : "minute";
                    } else if (unit.contains("h")) {
                        unit = (duration > Numbers.ONE.value) ? "hours" : "hour";
                    }
                    FixedDuration fixedDuration = new FixedDuration(taskDesc, duration, unit);
                    return new AddCommand(fixedDuration);
                }
            }
        } else if (!emptyString
                && (arr[Numbers.ZERO.value].equals("setpriority") || arr[Numbers.ZERO.value].equals("sp"))) {
            String description = "";

            int taskNum;
            int priority;
            for (int i = Numbers.ONE.value; i < arr.length; i++) {
                description += arr[i] + " ";
            }

            String[] holder = description.split(" ");
            if (holder.length < Numbers.TWO.value) {
                throw new DukeException(ErrorMessages.PRIORITY_FORMAT.message);
            } else {
                try {
                    taskNum = Integer.parseInt(holder[Numbers.ZERO.value].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING,ErrorMessages.TASKNUM_MUST_BE_INT.message);
                    throw new DukeException(ErrorMessages.TASKNUM_MUST_BE_INT.message);
                }

                if (taskNum <= Numbers.ZERO.value || taskNum > items.size()) {
                    throw new DukeException(ErrorMessages.TASKNUM_INVALID_INT.message);
                }

                try {
                    priority = Integer.parseInt(holder[Numbers.ONE.value].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING,"The priority must be an integer");
                    throw new DukeException("The priority must be an integer");
                }

                if (!((priority > Numbers.ZERO.value) && (priority < Numbers.SIX.value))) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid priority! (1 - High ~ 5 - Low).");
                }

                return new SetPriorityCommand(taskNum, priority);
            }

        } else if (!emptyString
                    && (arr[Numbers.ZERO.value].equals("findpriority") || arr[Numbers.ZERO.value].equals("fp"))) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException("     (>_<) OOPS!!! The target priority cannot be empty.");
            } else {
                int target;
                if (arr[Numbers.ONE.value].trim().isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The target priority cannot be empty.");
                } else {
                    try {
                        target = Integer.parseInt(arr[Numbers.ONE.value]);
                    } catch (Exception e) {
                        logr.log(Level.WARNING,"The target priority must be an integer");
                        throw new DukeException("The target priority must be an integer");
                    }

                    if (!((target > Numbers.ZERO.value) && (target < Numbers.SIX.value))) {
                        throw new DukeException("     (>_<) OOPS!!! Invalid target priority! (1 ~ 5).");
                    }
                    return new FindTasksByPriorityCommand(target);
                }
            }
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("finddate")) {
            SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            int sufIndex = Numbers.MINUS_ONE.value;
            String description = "";

            for (int i = Numbers.ONE.value; i < arr.length; i++) {
                description += arr[i] + " ";
            }

            if (!description.contains("/on")) {
                throw new DukeException("     (>_<) OOPS!!! The format for finddate is 'finddate /on <dd/mm/yyyy>'");
            }
            String[] holder =  description.split("/on");
            if (holder.length == 1) {
                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            }

            dateDesc = description.split("/on")[Numbers.ONE.value].trim();

            if (dateDesc.trim().isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                        + arr[Numbers.ZERO.value] + " cannot be empty.");
            }
            dateParser.isValidDate(dateDesc);
            Date date;
            try {
                date = datetimeFormat.parse(dateDesc);
            } catch (Exception e) {
                logr.log(Level.WARNING, ErrorMessages.DATE_FORMAT.message, e);
                throw new DukeException(ErrorMessages.DATE_FORMAT.message);
            }
            int day = Integer.parseInt(new SimpleDateFormat("d").format(date));
            if (day % 10 == Numbers.ONE.value) {
                sufIndex = Numbers.ZERO.value;
            } else if (day % 10 == Numbers.TWO.value) {
                sufIndex = Numbers.ONE.value;
            } else if (day % 10 == Numbers.THREE.value) {
                sufIndex = Numbers.TWO.value;
            } else if (day > Numbers.THREE.value && day < Numbers.THIRTY_ONE.value) {
                sufIndex = Numbers.THREE.value;
            }

            String[] suf = { "st", "nd", "rd", "th" };
            String suffixStr = day + suf[sufIndex];
            SimpleDateFormat datetimeFormat2 = new SimpleDateFormat("MMMMM yyyy");
            String displayDT = datetimeFormat2.format(date);
            displayDT = suffixStr + " of " + displayDT;

            return new FindTasksByDateCommand(displayDT);
            //@@author talesrune
        } else if (!emptyString && (arr[Numbers.ZERO.value].equals("update"))) {
            if (arr.length == Numbers.ONE.value) {
                throw new DukeException(ErrorMessages.TASKNUM_IS_EMPTY.message);
            } else {
                int tasknum = Integer.parseInt(arr[Numbers.ONE.value]) - Numbers.ONE.value;
                if (tasknum < Numbers.ZERO.value || tasknum >= items.size()) {
                    throw new DukeException(ErrorMessages.TASKNUM_INVALID_INT.message);
                } else if (arr.length < Numbers.FOUR.value) {
                    throw new DukeException("     (>_<) OOPS!!! Insufficient parameters. "
                            + "Format: update <tasknum> <type> <desc or date>");
                } else {
                    int typeOfUpdate = Numbers.MINUS_ONE.value;
                    String typeDesc = "";
                    for (int i = Numbers.TWO.value; i < arr.length; i++) {
                        if (i == Numbers.TWO.value) {
                            if (arr[i].trim().isEmpty()
                                    || (!arr[i].equals("/desc")
                                    && !arr[i].equals("/date")
                                    && !arr[i].equals("/type"))) {
                                throw new DukeException("     (>_<) OOPS!!! Unable to find either "
                                        + "/date, /desc, or /type.");
                            } else {
                                if (arr[i].equals("/desc")) {
                                    typeOfUpdate = Numbers.ONE.value;
                                } else if (arr[i].equals("/date")) {
                                    typeOfUpdate = Numbers.TWO.value;
                                } else {
                                    typeOfUpdate = Numbers.THREE.value;
                                }
                            }
                        } else {
                            if (typeOfUpdate == Numbers.ONE.value) {
                                taskDesc += arr[i] + " ";
                            } else if (typeOfUpdate == Numbers.TWO.value) {
                                dateDesc += arr[i] + " ";
                            } else {
                                typeDesc += arr[i] + " ";
                            }
                        }
                    }
                    taskDesc = taskDesc.trim();
                    dateDesc = dateDesc.trim();
                    typeDesc = typeDesc.trim();
                    DetectDuplicate detectDuplicate = new DetectDuplicate(items);
                    if (typeOfUpdate == Numbers.ONE.value && taskDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of a "
                                + arr[Numbers.ZERO.value] + " cannot be empty.");
                    } else if (typeOfUpdate == Numbers.TWO.value && dateDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                                + arr[Numbers.ZERO.value] + " cannot be empty.");
                    } else if (typeOfUpdate == Numbers.THREE.value && typeDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of type for "
                                + arr[Numbers.ZERO.value] + " cannot be empty.");
                    } else if (detectDuplicate.isDuplicate(taskDesc)) {
                        return new DuplicateFoundCommand();
                    } else if (typeOfUpdate != Numbers.MINUS_ONE.value) {

                        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
                            if (dateDesc.equals(items.get(i).getDateTime()) && !items.get(i).isDone()) {
                                throw new DukeException("     (>_<) OOPS!!! The date/time for "
                                        + arr[Numbers.ZERO.value] + " clashes with " + items.get(i).toString()
                                        + "\n     Please choose another date/time! "
                                        + "Or mark the above task as Done first!");
                            }
                        }
                        if (typeOfUpdate == Numbers.TWO.value) {
                            dateParser.isValidDateTime(dateDesc);
                        }
                        return new UpdateCommand(taskDesc, dateDesc, typeDesc, typeOfUpdate, tasknum);
                    } else {
                        throw new DukeException("     (>_<) OOPS!!! There is something wrong "
                                + " when trying to update");
                    }
                }
            }   //@@author e0318465
        } else if (!emptyString
                && (arr[Numbers.ZERO.value].equals("addcontact") || arr[Numbers.ZERO.value].equals("ac")
                || arr[Numbers.ZERO.value].equals("addcontacts"))) {
            try {
                String[] userInput = sentence.split(" ",Numbers.TWO.value);
                String[] contactDetails = userInput[Numbers.ONE.value].split(",");
                checkForEmptyContactDetail(contactDetails);
                if (!contactDetails[Numbers.TWO.value].contains("@")
                        && !contactDetails[Numbers.TWO.value].contains("Nil")) {
                    throw new DukeException(ErrorMessages.CONTACT_FORMAT.message);
                }
                Contacts contactObj = new Contacts(contactDetails[Numbers.ZERO.value],
                        contactDetails[Numbers.ONE.value],
                        contactDetails[Numbers.TWO.value], contactDetails[Numbers.THREE.value]);
                return new AddContactsCommand(contactObj, contactList);
            } catch (Exception e) {
                logr.log(Level.WARNING, ErrorMessages.CONTACT_FORMAT.message, e);
                throw new DukeException(ErrorMessages.CONTACT_FORMAT.message);
            }
        } else if (sentence.equals("listcontact") || sentence.equals("lc") || sentence.equals(("listcontacts"))) {
            return new ListContactsCommand(contactList);
        } else if (!emptyString
                && (arr[Numbers.ZERO.value].equals("deletecontact") || arr[Numbers.ZERO.value].equals("dc")
                || arr[Numbers.ZERO.value].equals("deletecontacts"))) {
            try {
                if (arr.length == Numbers.ONE.value) {
                    throw new DukeException(ErrorMessages.CONTACT_INDEX.message);
                }
                return new DeleteContactCommand(Integer.parseInt(arr[Numbers.ONE.value]), contactList);
            } catch (NumberFormatException e) {
                throw new DukeException(ErrorMessages.NON_INTEGER_ALERT.message);
            }
        } else if (!emptyString
                && (arr[Numbers.ZERO.value].equals("findcontact") || arr[Numbers.ZERO.value].equalsIgnoreCase("fc")
                || arr[Numbers.ZERO.value].equals("findcontacts"))) {
            String[] keyword = sentence.split(" ", Numbers.TWO.value);
            if (arr.length == Numbers.ONE.value || keyword[Numbers.ONE.value].trim().isEmpty()
                    || keyword[Numbers.ONE.value].trim().equals(",")) {
                throw new DukeException(ErrorMessages.KEYWORD_IS_EMPTY.message);
            } else {
                return new FindContactCommand(keyword[Numbers.ONE.value].toLowerCase(), contactList);
            }
            //@@author
        } else if (!emptyString && arr[Numbers.ZERO.value].equals("budget")) { //@@author maxxyx96
            try {
                String budgetCommandString = sentence.split(" ", Numbers.TWO.value)[Numbers.ONE.value];
            } catch (Exception e) {
                throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                        + "\nIt should be: budget <new/add/minus/reset/view> <amount> ");
            }
            String budgetCommandString = sentence.split(" ", Numbers.TWO.value)[Numbers.ONE.value];
            budgetCommandString = trim(budgetCommandString);
            String budgetCommand = budgetCommandString.split(" ", Numbers.TWO.value)[Numbers.ZERO.value];
            budgetCommand = trim(budgetCommand);
            if (budgetCommand.equals("view") || budgetCommand.equals("list")) {
                return new ViewBudgetCommand(budgetList);
            } else if (budgetCommand.equals("undo")) {
                return new UndoBudgetCommand(budgetList);
            } else {
                try {
                    String budgetRemark = EMPTY_STRING;
                    String budgetAmount = budgetCommandString.split(" ", Numbers.TWO.value)[Numbers.ONE.value];
                    if (isSplittable(budgetAmount, " ")) {
                        budgetRemark = budgetAmount.split(" ", Numbers.TWO.value)[Numbers.ONE.value];
                        budgetAmount = budgetAmount.split(" ")[Numbers.ZERO.value];
                    }
                    budgetAmount = budgetAmountFormat(budgetAmount);
                    if (budgetCommand.equals("new") || budgetCommand.equals("reset")) {
                        return new ResetBudgetCommand(budgetList, Float.parseFloat(budgetAmount));
                    } else if (Float.parseFloat(budgetAmount) == 0) {
                        throw new DukeException("     (>_<) OoPS!!! You cant add/subtract an empty amount!");
                    } else if (budgetCommand.equals("add") || budgetCommand.equals("+")) {
                        return new AddBudgetCommand(budgetList, Float.parseFloat(budgetAmount), budgetRemark);
                    } else if (budgetCommand.equals("minus") || budgetCommand.equals("-")) {
                        return new AddBudgetCommand(budgetList, -Float.parseFloat(budgetAmount), budgetRemark);
                    } else {
                        throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                                + "\n     It should be more like: "
                                                + "\n     budget <cmd> <amount> <desc(Optional)>"
                                                + "\n     ,where cmd can be add/minus/view/reset. ");
                    }
                } catch (Exception p) {
                    throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                            + "\n     It should be more like: "
                            + "\n     budget <cmd> <amount> <desc(Optional)>"
                            + "\n     ,where cmd can be add/minus/view/reset. ");
                }
            } //@@author
        } else if (sentence.equals("backup")) {
            return new BackupCommand(); //@@author
        } else if (sentence.equals("bye") || sentence.equals("exit")) {
            return new ExitCommand();
            //@@author gervaiseang
        } else if (sentence.equals("help")) {
            return new HelpCommand();
        } else {
            throw new DukeException(ErrorMessages.UNKNOWN_COMMAND.message);
        }
    }
}