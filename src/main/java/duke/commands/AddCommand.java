package duke.commands;


import duke.ConflictChecker;
import duke.DoAfterList;
import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.tasks.BetweenTask;
import duke.tasks.Deadline;
import duke.tasks.DoAfter;
import duke.tasks.Event;
import duke.tasks.FixedDurationTask;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.ToDo;


/**
 * A class representing the command to add tasks to the task list.
 */
public class AddCommand extends Command<TaskList> {

    /**
     * Constructor for the command to add a task to the task list.
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddCommand(String message) {
        this.message = message;
    }

    /**
     * Modifies the task list in use and returns the messages intended to be displayed.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        String identifier;
        try {
            identifier = message.substring(0, 4);
        } catch (Exception e) {
            throw new DukeException("","other");
        }
        switch (identifier) {
        case "todo": {
            ToDo todo;
            if (message.length() < 5 || !message.substring(4,5).equals(" ")) {
                throw new DukeException(message);
            }
            try {
                todo = new ToDo(message.trim().substring(5));
                taskList.add(todo);
                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), todo);
            } catch (Exception e) {
                throw new DukeException(message, "todo"); //empty to-do
            }
        }
        case "dead": {
            if (message.length() < 9 || !message.substring(4,9).equals("line ")) { //exception if not fully spelt
                throw new DukeException(message);
            }
            Deadline deadline;
            try {
                String[] sections = message.substring(9).split(" /by ");
                deadline = new Deadline(sections[0], sections[1]);
                taskList.add(deadline);
                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), deadline);
            } catch (Exception e) {
                throw new DukeException(message,"deadline");
            }
        }
        case "even": {
            if (message.length() < 6 || !message.substring(4,6).equals("t ")) { //exception if not fully spelt
                throw new DukeException(message);
            }
            Event event;
            try {
                String[] sections = message.substring(6).split(" /at ");
                String[] startEnd = sections[1].split("-");
                String start =  startEnd[0];
                String end = startEnd[1];
                ConflictChecker conflictChecker1 = new ConflictChecker(taskList);
                event = (Event) taskList.get_first_e(sections,0);
                if (event.hasDate()) {
                    if (conflictChecker1.is_conflict(event)) {
                        throw new DukeException("", "conflict");
                    } else {
                        taskList.add(event);
                    }
                } else {
                    taskList.add(event);
                }

                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), event);
            } catch (Exception e) {
                throw new DukeException("", "conflict");
            }
        }
        case "doaf": {
            if (message.length() < 8 || !message.substring(4,8).equals("ter ")) { //exception if not fully spelt
                throw new DukeException(message);
            }
            DoAfter doAfter;
            try {
                String[] sections = message.substring(8).split(" /after ");
                int previousTaskNumber = Integer.parseInt(sections[1]);
                doAfter = new DoAfter(sections[0], previousTaskNumber, taskList.getSize() + 1);
                taskList.add(doAfter);
                DoAfterList.add(previousTaskNumber);
                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), doAfter);
            } catch (Exception e) {
                throw new DukeException(message, "doafter");
            }
        }
        case "chan": {
            try {
                String[] sections = message.substring(7).split(" /to ");
                int itemToDelete = Integer.parseInt(sections[0]) - 1;
                if (itemToDelete < taskList.getSize()) {
                    Task item = taskList.getTaskIndex(itemToDelete);
                    if (item.getType() == "E") {
                        Event event;
                        String itemDescription = item.getDescription();
                        String status = item.getStatusIcon();
                        String[] sections1 = {itemDescription, sections[1]};
                        event = (Event) taskList.get_first_e(sections1, 0);
                        if (status == "v") {
                            event.setDone();
                        }
                        taskList.remove(itemToDelete);

                        taskList.add(event);
                        //storage.updateFile(taskList);
                        return ui.formatAdd(taskList.getTaskList(), event);
                    } else if (item.getType() == "D") {
                        String itemDescription = item.getDescription();
                        String status = item.getStatusIcon();
                        Deadline deadline;
                        try {
                            deadline = new Deadline(itemDescription, sections[1]);
                            taskList.remove(itemToDelete);

                            if (status == "v") {
                                deadline.setDone();
                            }
                            taskList.add(deadline);
                            //storage.updateFile(taskList);
                            return ui.formatAdd(taskList.getTaskList(), deadline);

                        } catch (Exception e) {
                            throw new DukeException(message, "deadline");
                        }
                    } else {
                        //not a event or deadline so we need either
                        throw new DukeException(message, "event");

                    }
                } else {
                    throw new DukeException(message, "event");
                }

            } catch (Exception e) {
                throw new DukeException(message, "event");
            }
        }
        case "betw": {
            if (message.length() < 8 || !message.substring(4, 8).equals("een ")) {

                throw new DukeException(message);
            }
            BetweenTask betweenTask;
            try {
                String[] sections = message.substring(8).split("/between");
                String[] periods = sections[1].split("and");
                betweenTask = new BetweenTask(sections[0].trim(), periods[0].trim(), periods[1].trim());
                taskList.add(betweenTask);
                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), betweenTask);
            } catch (Exception e) {
                throw new DukeException(message, "between");
            }
        }
        case "recu": {
            if (message.length() < 5 || !message.substring(4, 6).equals("r ")) {
                throw new DukeException(message);
            }
            RecurringTask recurringTask;
            try {
                String[] sections = message.split(" ");
                String frequency;
                String description;
                String date;
                String time;
                if (sections[1].equals("daily")
                        || sections[1].equals("weekly")
                        || sections[1].equals("monthly")
                        || sections[1].equals("yearly")) {
                    frequency = sections[1];

                    //form back the content
                    String result = "";
                    for (int i = 2; i < sections.length; i++) {
                        result += sections[i];
                    }

                    String[] subContent = result.split("/on");
                    description = subContent[0].trim();
                    String[] subContent2 = subContent[1].split("/at");
                    date = subContent2[0].trim();  //this must be in dd/mm/yy specific format
                    if (subContent2.length == 2) {
                        time = subContent2[1].trim();  //this could be anything
                    } else {
                        time = "";
                    }
                    int day;
                    int month;
                    int year;
                    String[] dateData = date.split("/");
                    day = Integer.parseInt(dateData[0].trim());
                    month = Integer.parseInt(dateData[1].trim());
                    year = Integer.parseInt(dateData[2].trim());
                    if (!RecurringTask.isDateVaid(day, month, year)) {
                        throw new DukeException("");
                    }
                    recurringTask = new RecurringTask(description, date, time, frequency);
                    taskList.add(recurringTask);
                    //storage.updateFile(taskList);
                    return ui.formatAdd(taskList.getTaskList(), recurringTask);
                } else {
                    //invalid frequency input
                    throw new DukeException("");
                }
            } catch (Exception e) {
                throw new DukeException(message, "recur");
            }

        }
        case "fixe": {
            if (message.length() < 14 || !message.substring(4, 14).equals("dDuration ")) {
                throw new DukeException(message);
            }
            try {
                String[] sections = message.substring(14).split(" /need ");
                Task fixedDurationTask = new FixedDurationTask(sections[0], sections[1]);
                taskList.add(fixedDurationTask);
                //storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), fixedDurationTask);
            } catch (Exception e) {
                throw new DukeException(message, "fixedDuration");
            }
        }
        default: {
            throw new DukeException(message);
        }
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

}
