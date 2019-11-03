package duke.parser;

import duke.command.*;
import duke.dukeexception.DukeException;
import duke.task.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a parser that breaks down user input into commands.
 */
public class Parser {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MINUS_ONE = -1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int SIX = 6;
    private static final String EMPTY_STRING = "";
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
        boolean getDate = false;
        if (sentence.equals("list")) {
            return new ListCommand();
        } else if (sentence.equals("priority")) {
            return new ListPriorityCommand();
        } else if (arr.length > ZERO && (arr[ZERO].equals("done")
                || arr[ZERO].equals("delete") || arr[ZERO].equals("del"))) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The task number cannot be empty.");
            } else {
                int tasknum = Integer.parseInt(arr[ONE]) - ONE;
                if (tasknum < ZERO || tasknum >= items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                } else {
                    if (arr[ZERO].equals("done")) {
                        if (items.get(tasknum).toString().contains("[A]")) {
                            String tempString = items.get(tasknum).toString();
                            tempString = tempString.split(": ", TWO)[ONE];
                            tempString = tempString.split("\\)")[ZERO];

                            if (!items.isTaskDone(tempString)) {
                                throw new DukeException("     (>_<) OOPS!! Task requirements has yet to be completed!"
                                        + " please complete task [" + tempString + "] before marking this as done!");
                            }
                        }
                        return new DoneCommand(tasknum);
                    } else { //delete
                        return new DeleteCommand(tasknum);
                    }
                }
            } //@@author talesrune
        } else if (arr.length > ZERO && arr[ZERO].equals("find")) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The keyword cannot be empty.");
            } else {
                if (arr[ONE].trim().isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The keyword cannot be empty.");
                } else {
                    return new FindCommand(arr[ONE]);
                }
            }
        } else if (arr.length > ZERO && arr[ZERO].equals("filter")) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The task's type cannot be empty.");
            } else {
                if (arr[ONE].trim().isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The task's type cannot be empty.");
                } else {
                    return new FilterCommand(arr[ONE]);
                }
            }
        } else if (arr.length > ZERO && arr[ZERO].equals("notes")) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The task number cannot be empty.");
            } else {
                int tasknum = Integer.parseInt(arr[ONE]) - ONE;
                if (tasknum < ZERO || tasknum >= items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                } else if (arr.length < THREE) {
                    throw new DukeException("     (>_<) OOPS!!! Insufficient parameters. "
                            + "Format: notes <tasknum> <type> <notes description>");
                } else {
                    int typeOfNotes = MINUS_ONE;
                    String notesDesc = "";
                    for (int i = TWO; i < arr.length; i++) {
                        if (i == TWO) {
                            if (arr[i].trim().isEmpty()
                                    || (!arr[i].equals("/add") && !arr[i].equals("/delete")
                                    && !arr[i].equals("/show"))) {
                                throw new DukeException("     (>_<) OOPS!!! Unable to find either "
                                        + "/add, /delete, or /show.");
                            } else {
                                if (arr[i].equals("/add")) {
                                    typeOfNotes = ONE;
                                } else if (arr[i].equals("/delete")) {
                                    typeOfNotes = TWO;
                                    break;
                                } else {
                                    typeOfNotes = THREE;
                                    break;
                                }
                            }
                        } else {
                            notesDesc += arr[i] + " ";
                        }
                    }
                    notesDesc = notesDesc.trim();
                    if (typeOfNotes == THREE) {
                        return new ShowNotesCommand(tasknum);
                    } else if (typeOfNotes == TWO) {
                        return new DeleteNotesCommand(tasknum);
                    } else if (typeOfNotes == ONE && notesDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The notes description of a "
                                + arr[ZERO] + " cannot be empty.");
                    } else if (typeOfNotes != MINUS_ONE) {
                        return new AddNotesCommand(notesDesc,tasknum);
                    } else {
                        throw new DukeException("     (>_<) OOPS!!! There is something wrong "
                                + " when trying to add notes");
                    }
                }
            }   //@@author
        } else if (arr.length > ZERO && arr[ZERO].equals("todo")) {
            String[] getDescription = sentence.split(" ", TWO);
            DetectDuplicate detectDuplicate = new DetectDuplicate(items);
            if (detectDuplicate.isDuplicate(getDescription[ZERO], getDescription[ONE])) {
                return new DuplicateFoundCommand();
            } else {
                for (int i = ONE; i < arr.length; i++) {
                    taskDesc += arr[i] + " ";
                }
                taskDesc = taskDesc.trim();
                if (taskDesc.isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The description of a todo cannot be empty.");
                } else {
                    Task taskObj = new Todo(taskDesc);
                    return new AddCommand(taskObj);
                }
            }
        } else if (arr.length > ZERO && (arr[ZERO].equals("deadline")
                || arr[ZERO].equals("dl"))) {
            for (int i = ONE; i < arr.length; i++) {
                if ((arr[i].trim().isEmpty() || !arr[i].substring(ZERO, ONE).equals("/")) && !getDate) {
                    taskDesc += arr[i] + " ";
                } else {
                    if (!getDate) { //detect "/"
                        getDate = true;
                    } else {
                        dateDesc += arr[i] + " ";
                    }
                }
            }
            taskDesc = taskDesc.trim();
            dateDesc = dateDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[ZERO] + " cannot be empty.");
            } else if (dateDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                        + arr[ZERO] + " cannot be empty.");
            } else {
                Task taskObj;
                taskObj = new Deadline(taskDesc, dateDesc);

                for (int i = ZERO; i < items.size(); i++) {
                    if (taskObj.getDateTime().equals(items.get(i).getDateTime()) && !items.get(i).isDone()) {
                        throw new DukeException("     (>_<) OOPS!!! The date/time for "
                                + arr[ZERO] + " clashes with " + items.get(i).toString()
                                + "\n     Please choose another date/time! Or mark the above task as Done first!");
                    }
                }
                return new AddCommand(taskObj);
            }
        } else if (arr.length > ZERO && (arr[ZERO].equals("fixedduration") || arr[ZERO].equals("fd"))) {
            //fixedduration <task> /for <duration> <unit>
            String description = "";
            String durDesc;
            int duration;
            String unit;
            for (int i = ONE; i < arr.length; i++) {
                description += arr[i] + " ";
            }
            taskDesc = description.split(" /for ")[ZERO].trim();
            durDesc = description.split(" /for ")[ONE].trim();
            DetectDuplicate detectDuplicate = new DetectDuplicate(items);

            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[ZERO] + " cannot be empty.");
            } else if (durDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of duration for "
                        + arr[ZERO] + " cannot be empty.");
            } else if (detectDuplicate.isDuplicate(arr[ZERO], taskDesc)) {
                return new DuplicateFoundCommand();
            } else {
                try {
                    duration = Integer.parseInt(durDesc.split(" ")[ZERO].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING,"Format is in: fixedduration <task> /for <duration> <unit>");
                    throw new DukeException("Format is in: fixedduration <task> /for <duration> <unit>");
                }
                unit = durDesc.split(" ")[ONE].trim();
                if (unit.isEmpty() || (!unit.toLowerCase().contains("min") && ! unit.toLowerCase().contains("h"))) {
                    throw new DukeException("Format is in: fixedduration <task> /for <duration> <unit>");
                } else {
                    if (unit.contains("min")) {
                        unit = (duration > ONE) ? "minutes" : "minute";
                    } else if (unit.contains("h")) {
                        unit = (duration > ONE) ? "hours" : "hour";
                    }
                    FixedDuration fixedDuration = new FixedDuration(taskDesc, duration, unit);
                    return new AddCommand(fixedDuration);
                }
            }
        } else if (arr.length > ZERO && (arr[ZERO].equals("setpriority") || arr[ZERO].equals("sp"))) {
            //fixedduration <taskNum> <priority>
            String description = "";

            int taskNum;
            int priority;
            for (int i = ONE; i < arr.length; i++) {
                description += arr[i] + " ";
            }

            String[] holder = description.split(" ");
            if (holder.length < TWO) {
                throw new DukeException("     (>_<) OOPS!!! Format is in: setpriority <taskNum> <Priority>");
            } else {
                try {
                    taskNum = Integer.parseInt(holder[ZERO].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING,"The task number must be an integer");
                    throw new DukeException("The task number must be an integer");
                }

                if (taskNum <= ZERO || taskNum > items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                }

                try {
                    priority = Integer.parseInt(holder[ONE].trim());
                } catch (Exception e) {
                    logr.log(Level.WARNING,"The priority must be an integer");
                    throw new DukeException("The priority must be an integer");
                }

                if (!((priority > ZERO) && (priority < SIX))) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid priority! (1 - High ~ 5 - Low).");
                }

                return new SetPriorityCommand(taskNum, priority);
            }

        } else if (arr.length > ZERO && arr[ZERO].equals("findpriority")) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The target priority cannot be empty.");
            } else {
                int target;
                if (arr[ONE].trim().isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The target priority cannot be empty.");
                } else {
                    try {
                        target = Integer.parseInt(arr[ONE]);
                    } catch (Exception e) {
                        logr.log(Level.WARNING,"The target priority must be an integer");
                        throw new DukeException("The target priority must be an integer");
                    }

                    if (!((target > ZERO) && (target < SIX))) {
                        throw new DukeException("     (>_<) OOPS!!! Invalid target priority! (1 ~ 5).");
                    }
                    return new FindTasksByPriorityCommand(target);
                }
            }
        } else if (arr.length > ZERO && arr[ZERO].equals("remind")) {
            //remind <taskNumber> /in <howManyDays>
            String afterTaskDesc = "";
            boolean detectBackSlash = false;
            int duration;
            for (int i = ONE; i < arr.length; i++) {
                if ((arr[i].trim().isEmpty() || !arr[i].substring(ZERO, ONE).equals("/")) && !detectBackSlash) {
                    taskDesc += arr[i] + " ";
                } else {
                    if (!detectBackSlash) {
                        detectBackSlash = true;
                    } else {
                        afterTaskDesc += arr[i] + " ";
                    }
                }
            }
            taskDesc = taskDesc.trim();
            afterTaskDesc = afterTaskDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[ZERO] + " cannot be empty.");
            } else if (afterTaskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description for "
                        + arr[ZERO] + " cannot be empty.");
            } else {
                duration = Integer.parseInt(taskDesc.split("/in", TWO)[ZERO].trim()) - ONE;
                int howManyDays = Integer.parseInt(afterTaskDesc);
                return new RemindCommand(duration, howManyDays);
            }  //@@author talesrune
        } else if (arr.length > ZERO && (arr[ZERO].equals("update"))) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The task number cannot be empty.");
            } else {
                int tasknum = Integer.parseInt(arr[ONE]) - ONE;
                if (tasknum < ZERO || tasknum >= items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                } else if (arr.length < FOUR) {
                    throw new DukeException("     (>_<) OOPS!!! Insufficient parameters. "
                            + "Format: update <tasknum> <type> <desc or date>");
                } else {
                    int typeOfUpdate = MINUS_ONE;
                    String typeDesc = "";
                    for (int i = TWO; i < arr.length; i++) {
                        if (i == TWO) {
                            if (arr[i].trim().isEmpty()
                                    || (!arr[i].equals("/desc")
                                    && !arr[i].equals("/date")
                                    && !arr[i].equals("/type"))) {
                                throw new DukeException("     (>_<) OOPS!!! Unable to find either "
                                        + "/date, /desc, or /type.");
                            } else {
                                if (arr[i].equals("/desc")) {
                                    typeOfUpdate = ONE;
                                } else if (arr[i].equals("/date")) {
                                    typeOfUpdate = TWO;
                                } else { //equals /type
                                    typeOfUpdate = THREE;
                                }
                            }
                        } else {
                            if (typeOfUpdate == ONE) {
                                taskDesc += arr[i] + " ";
                            } else if (typeOfUpdate == TWO) {
                                dateDesc += arr[i] + " ";
                            } else { //type of update is number 3
                                typeDesc += arr[i] + " ";
                            }
                        }
                    }
                    taskDesc = taskDesc.trim();
                    dateDesc = dateDesc.trim();
                    typeDesc = typeDesc.trim();
                    if (typeOfUpdate == ONE && taskDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of a "
                                + arr[ZERO] + " cannot be empty.");
                    } else if (typeOfUpdate == TWO && dateDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                                + arr[ZERO] + " cannot be empty.");
                    } else if (typeOfUpdate == THREE && typeDesc.isEmpty()) {
                        throw new DukeException("     (>_<) OOPS!!! The description of type for "
                                + arr[ZERO] + " cannot be empty.");
                    } else if (typeOfUpdate != MINUS_ONE) {
                        return new UpdateCommand(taskDesc, dateDesc, typeDesc, typeOfUpdate, tasknum);
                    } else {
                        throw new DukeException("     (>_<) OOPS!!! There is something wrong "
                                + " when trying to update");
                    }
                }
            }   //@@author e0318465
        } else if (arr.length > ZERO && (arr[ZERO].equals("addcontact") || arr[ZERO].equals("ac"))) {
            String[] userInput = sentence.split(" ",TWO);
            String[] contactDetails = userInput[ONE].split(",");
            try {
                Contacts contactObj = new Contacts(contactDetails[ZERO], contactDetails[ONE],
                                          contactDetails[TWO], contactDetails[THREE]);
                return new AddContactsCommand(contactObj, contactList);
            } catch (Exception e) {
                logr.log(Level.WARNING,"Format is in: addcontact <name>, <contact>, <email>, <office>",e);
                throw new DukeException("Format is in: addcontact <name>, <contact>, <email>, <office>");
            }
        } else if (sentence.equals("listcontacts") || sentence.equals("lc") || sentence.equals(("listcontact"))) {
            return new ListContactsCommand(contactList);
        } else if (arr.length > ZERO && (arr[ZERO].equals("deletecontact") || arr[ZERO].equals("dc"))) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The contact index cannot be empty.");
            } else {
                try {
                    Integer.parseInt(arr[ONE]); //Catches for non integer value
                    return new DeleteContactCommand(Integer.parseInt(arr[ONE]) - ONE, contactList);
                } catch (NumberFormatException e) {
                    throw new DukeException("     Input is not an integer value!");
                }
            }
        } else if (arr.length > ZERO && arr[ZERO].equals("findcontact") || arr[ZERO].equalsIgnoreCase("fc")) {
            String[] keyword = sentence.split(" ", TWO);
            if (arr.length == ONE || keyword[ONE].trim().isEmpty() || keyword[ONE].trim().equals(",")) {
                throw new DukeException("     (>_<) OOPS!!! The keyword cannot be empty.");
            } else {
                return new FindContactCommand(keyword[ONE].toLowerCase(), contactList);
            }
            //@@author
        } else if (arr.length > ZERO && arr[ZERO].equals("budget")) { //@@author maxxyx96
            try {
                String budgetCommandString = sentence.split(" ", TWO)[ONE];
            } catch (Exception e) {
                throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                        + "\nIt should be: budget <new/add/minus/reset/view> <amount> ");
            }
            String budgetCommandString = sentence.split(" ", TWO)[ONE];
            budgetCommandString = trim(budgetCommandString);
            String budgetCommand = budgetCommandString.split(" ", TWO)[ZERO];
            budgetCommand = trim(budgetCommand);
            if (budgetCommand.equals("view")) {
                return new ViewBudgetCommand(budgetList);
            } else {
                try {
                    String budgetRemark = EMPTY_STRING;
                    String budgetAmount = budgetCommandString.split(" ", TWO)[ONE];
                    if (isSplittable(budgetAmount, " ")) {
                        budgetRemark = budgetAmount.split(" ", TWO)[ONE];
                        budgetAmount = budgetAmount.split(" ")[ZERO];
                    }
                    if (budgetCommand.equals("new") || budgetCommand.equals("reset")) {
                        return new ResetBudgetCommand(budgetList, Float.parseFloat(budgetAmount));
                    } else if (budgetCommand.equals("add") || budgetCommand.equals("+")) {
                        return new AddBudgetCommand(budgetList, Float.parseFloat(budgetAmount), budgetRemark);
                    } else if (budgetCommand.equals("minus") || budgetCommand.equals("-")) {
                        return new AddBudgetCommand(budgetList, -Float.parseFloat(budgetAmount), budgetRemark);
                    } else {
                        throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                                + "\n     It should be more like: "
                                                + "\n     budget <+/-/reset/view> <amount> <desc(Optional)>");
                    }
                } catch (Exception p) {
                    throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                            + "\n     It should be more like: "
                            + "\n     budget <+/-/reset/view> <amount> <desc(Optional)>");
                }
            }
        } else if (sentence.equals("backup")) {
            return new BackupCommand(); //@@author
        } else if (sentence.equals("bye") || sentence.equals("exit")) {
            return new ExitCommand();
        } else {
            throw new DukeException("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}