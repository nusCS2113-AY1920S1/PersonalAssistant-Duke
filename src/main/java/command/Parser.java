package command;

import common.DukeException;
import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * Parser that parses input from the user.
 */
public class Parser {
    private static Instruction instr = new Instruction();
    private static Process process = new Process();

    /**
     * Method that parses input from the user and executes processes based on the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage for the Tasklist.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws DukeException if input is not valid.
     */
    public static boolean parse(String input, TaskList tasklist, Ui ui,
                                Storage storage, HashMap<String, Payee> managermap) {
        try {
            if (instr.isBye(input)) {
                //print bye message
                ui.byeMessage();
                ui.getIn().close();
                return true;

            } else if (instr.isList(input)) {
                //print out current list
                ui.printList(tasklist, "list");
            } else if (instr.isDone(input)) {
                process.done(input, tasklist, ui);

            } else if (instr.isDeadline(input)) {
                process.deadline(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (instr.isDoAfter(input)) {
                process.doAfter(input, tasklist, ui);
                Storage.save(tasklist.returnArrayList());
            } else if (instr.isDelete(input)) {
                process.delete(input, managermap, ui);
                storage.save(tasklist.returnArrayList());

            } else if (instr.isFind(input)) {
                // process.find(input, tasklist, ui);
            } else if (instr.isWithinPeriodTask(input)) {
                process.within(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            } else if (instr.isSnooze(input)) {
                process.snooze(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            /*
            `} else if (instr.isPostpone(input)) {
                process.postpone(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());`
            */
            } else if (instr.isReschedule(input)) {
                // process.reschedule(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());
            } else if (instr.isViewSchedule(input)) {
                process.viewSchedule(input, tasklist, ui);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isReminder(input)) {
                //process.reminder(input, tasklist, ui);
            } else if (instr.isEdit(input)) {
                // process.edit(input,tasklist,ui);
            } else if (instr.isPayment(input)) {
                process.payment(input, managermap, ui);
            } else if (instr.isgetpayee(input)) {
                process.findPayee(input, ui, managermap);
            } else if (instr.isPayee(input)) {
                process.payee(input, managermap, ui);
            } else {
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            ui.exceptionMessage(e.getMessage());
        }
        return false;
    }
    /**
     * Processes the find command and outputs a list of tasks containing the word.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processFind(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] splitspace = input.split(" ", 2);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.getDescription().contains(splitspace[1])) {
                    findlist.addTask(tasks);
                }
            }
            ui.printList(findlist, "find");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    private static void processEdit(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitedit = splitspace[1].split(" d/", 2);
            int nedit = Integer.parseInt(splitedit[0]) - 1;
            String description = splitedit[1];
            tasklist.get(nedit).setDescription(description);
            ui.printEditMessage(tasklist.get(nedit));
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        }
    }
}
    
