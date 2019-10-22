package duke.parser;

import duke.command.Command;
import duke.command.FindCommand;
import duke.command.FilterCommand;
import duke.command.ListCommand;
import duke.command.FindTasksByPriorityCommand;
import duke.command.DuplicateFoundCommand;
import duke.command.UpdateCommand;
import duke.command.DoneCommand;
import duke.command.RemindCommand;
import duke.command.AddCommand;
import duke.command.BackupCommand;
import duke.command.ExitCommand;
import duke.command.ListPriorityCommand;
import duke.command.AddMultipleCommand;
import duke.command.SetPriorityCommand;
import duke.command.DeleteCommand;
import duke.command.AddBudgetCommand;
import duke.command.DeleteContactCommand;
import duke.command.ListContactsCommand;
import duke.command.AddContactsCommand;
import duke.command.ResetBudgetCommand;
import duke.command.ViewBudgetCommand;
import duke.dukeexception.DukeException;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Repeat;
import duke.task.DoAfter;
import duke.task.FixedDuration;
import duke.task.DetectDuplicate;
import duke.task.Contacts;
import duke.task.BudgetList;
import java.util.ArrayList;

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

    /**
     * Generates a command based on the user input.
     *
     * @param sentence User input.
     * @param items The task list that contains a list of tasks.
     * @param budgetList The list that contains a list of budget.
     * @return Command to be executed afterwards.
     * @throws Exception  If there is an error interpreting the user input.
     */
    public static Command parse(String sentence, TaskList items, BudgetList budgetList) throws Exception {
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
            }
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
                || arr[ZERO].equals("dl") || arr[ZERO].equals("event"))) {
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
                if (arr[ZERO].equals("deadline") || arr[ZERO].equals("dl")) {
                    taskObj = new Deadline(taskDesc, dateDesc);
                } else {
                    taskObj = new Event(taskDesc, dateDesc);
                }

                for (int i = ZERO; i < items.size(); i++) {
                    if (taskObj.getDateTime().equals(items.get(i).getDateTime()) && !items.get(i).isDone()) {
                        throw new DukeException("     (>_<) OOPS!!! The date/time for "
                                + arr[ZERO] + " clashes with " + items.get(i).toString()
                                + "\n     Please choose another date/time! Or mark the above task as Done first!");
                    }
                }
                return new AddCommand(taskObj);
            }
        } else if (arr.length > ZERO && (arr[ZERO].equals("doafter") || arr[ZERO].equals("da"))) {
            //doafter <task> /after <pre-requisite task>
            String afterTaskDesc = "";
            boolean detectBackSlash = false;
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
                throw new DukeException("     (>_<) OOPS!!! The description of Task for "
                        + arr[ZERO] + " cannot be empty.");
            } else {
                String currentTasks = items.getList();
                if (currentTasks.contains(afterTaskDesc)) {
                    Task taskObj;
                    taskObj = new DoAfter(taskDesc, afterTaskDesc);
                    return new AddCommand(taskObj);
                } else {
                    throw new DukeException("(>_<) OOPS!!! You cant set a "
                            + arr[ZERO] + " task for a task that is not in the list!");
                }
            }
        } else if (arr.length > ZERO && (arr[ZERO].equals("repeat") || arr[ZERO].equals("rep"))) {
            //repeat <task> /from <date time> /for 3 <day/week/month>
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
                String repeatSettings;
                int repeatTimes;
                String repeatPeriod;
                try {
                    repeatSettings = dateDesc.split("/for ")[ONE];
                    repeatTimes = Integer.parseInt(repeatSettings.replaceAll("[\\D]", ""));
                    repeatPeriod = repeatSettings.split(repeatTimes + " ")[ONE];

                } catch (Exception e) {
                    throw new DukeException("Format is in: repeat <task> /from <date time> "
                            + "/for <repeat times> <days/weeks>");
                }

                ArrayList<Task> repeatList = new ArrayList<>();
                String timeDesc = dateDesc.split(" ", THREE)[ONE];
                for (int i = ZERO; i < repeatTimes; i++) {
                    Task taskObj;
                    taskObj = new Repeat(taskDesc, dateDesc);
                    dateDesc = DateParser.add(dateDesc, repeatPeriod) + " " + timeDesc;
                    repeatList.add(taskObj);

                    for (int j = ZERO; j < items.size(); j++) {
                        if (taskObj.getDateTime().equals(items.get(j).getDateTime()) && !items.get(j).isDone()) {
                            throw new DukeException("     (>_<) OOPS!!! The date/time for "
                                    + arr[ZERO] + " clashes with " + items.get(j).toString()
                                    + "\n     Please choose another date/time! Or mark the above task as Done first!");
                        }
                    }
                }
                return new AddMultipleCommand(repeatList);
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
                    throw new DukeException("Format is in: fixedduration <task> /for <duration> <unit>");
                }
                unit = durDesc.split(" ")[ONE].trim();
                if (unit.isEmpty() || (!unit.toLowerCase().contains("min") && ! unit.toLowerCase().contains("h"))) {
                    throw new DukeException("Format is in: fixedduration <task> /for <duration> <unit>");
                } else {
                    if (unit.contains("min")) {
                        unit = (duration > 1) ? "minutes" : "minute";
                    } else if (unit.contains("h")) {
                        unit = (duration > 1) ? "hours" : "hour";
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
                    throw new DukeException("The task number must be an integer");
                }

                if (taskNum <= ZERO || taskNum > items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                }

                try {
                    priority = Integer.parseInt(holder[ONE].trim());
                } catch (Exception e) {
                    throw new DukeException("The priority must be an integer");
                }

                if (!((priority > ZERO) && (priority < 6))) {
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
                        throw new DukeException("The target priority must be an integer");
                    }

                    if (!((target > ZERO) && (target < 6))) {
                        throw new DukeException("     (>_<) OOPS!!! Invalid target priority! (1 ~ 5).");
                    }
                    return new FindTasksByPriorityCommand(target);
                }
            }
        } else if (arr.length > ZERO && arr[ZERO].equals("remind")) {
            //remind <taskNumber> /in <howManyDays>
            String description = "";
            String durDesc;
            int duration;
            String unit;
            for (int i = ONE; i < arr.length; i++) {
                description += arr[i] + " ";
            }
            if (description.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[ZERO] + " cannot be empty.");
            }

            duration = Integer.parseInt(description.split("/in", TWO)[ZERO].trim()) - ONE;
            String in = description.split(" /in ", TWO)[ONE].trim();
            int howManyDays = Integer.parseInt(in.split(" ", TWO)[ZERO].trim());
            return new RemindCommand(duration, howManyDays);
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
                    } else {
                        return new UpdateCommand(taskDesc, dateDesc, typeDesc, typeOfUpdate, tasknum);
                    }
                }
            }   //@@author e0318465
        } else if (arr.length > ZERO && (arr[ZERO].equals("addcontact") || arr[ZERO].equals("ac"))) {
            String[] userInput = sentence.split(" ",TWO);
            String[] contactDetails = userInput[ONE].split(",");
            try {
                Contacts contactObj = new Contacts(contactDetails[ZERO], contactDetails[ONE],
                                          contactDetails[TWO], contactDetails[THREE]);
                return new AddContactsCommand(contactObj);
            } catch (Exception e) {
                throw new DukeException("Format is in: addcontact <name>, <contact>, <email>, <office>");
            }
        } else if (sentence.equals("listcontacts") || sentence.equals("lc")) {
            return new ListContactsCommand();
        } else if (arr.length > ZERO && (arr[ZERO].equals("deletecontact") || arr[ZERO].equals("dc"))) {
            if (arr.length == ONE) {
                throw new DukeException("     (>_<) OOPS!!! The contact index cannot be empty.");
            } else {
                return new DeleteContactCommand(Integer.parseInt(arr[ONE]) - ONE);
            }  //@@author
        } else if (arr.length > ZERO && arr[ZERO].equals("budget")) {
            try {
                String budgetCommandString = sentence.split(" ", TWO)[ONE];
            } catch (Exception e) {
                throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                        + "It should be: budget <new/add/minus/reset/view> <amount> ");
            }
            String budgetCommandString = sentence.split(" ", TWO)[ONE];
            String budgetCommand = budgetCommandString.split(" ", TWO)[ZERO];
            if (budgetCommand.trim().equals("view")) {
                return new ViewBudgetCommand(budgetList);
            } else {
                try {
                    String budgetAmount = budgetCommandString.split(" ", TWO)[ONE];
                    if (budgetCommand.trim().equals("new") || budgetCommand.trim().equals("reset")) {
                        return new ResetBudgetCommand(budgetList, Float.parseFloat(budgetAmount));
                    } else if (budgetCommand.trim().equals("add") || budgetCommand.trim().equals("+")) {
                        return new AddBudgetCommand(budgetList, Float.parseFloat(budgetAmount));
                    } else if (budgetCommand.trim().equals("minus") || budgetCommand.trim().equals("-")) {
                        return new AddBudgetCommand(budgetList, -Float.parseFloat(budgetAmount));
                    } else {
                        throw new DukeException("     (>_<) OoPS!!! Invalid Budget Command. "
                                                + "It should be: budget <new/add/minus/reset/view> <amount> ");
                    }
                } catch (Exception p) {
                    throw new DukeException("     (>_<) OoPS!!! Invalid amount! "
                                            + "Please enter a numerical/decimal after command!");
                }
            }
        } else if (sentence.equals("backup")) {
            return new BackupCommand();
        } else if (sentence.equals("bye") || sentence.equals("exit")) {
            return new ExitCommand();
        } else {
            throw new DukeException("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}