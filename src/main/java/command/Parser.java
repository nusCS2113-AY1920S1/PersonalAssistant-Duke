package command;

import common.AlphaNUSException;
import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import project.Fund;
import project.Project;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Parser that parses input from the user.
 */
public class Parser {
    private static Instruction instr = new Instruction();
    private static Process process = new Process();

    private static Project currentProject = null;

    /**
     * Method that parses input from the user and executes processes based on the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage for the Tasklist.
     * @param commandList List of input commands.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws AlphaNUSException if input is not valid.
     */

    public static boolean parse(String input, TaskList tasklist, Ui ui, Fund fund, Storage storage, ArrayList<String> commandList,
                                HashMap<String, Payee> managermap, HashMap<String, Project> projectmap) {
        try {
            if (instr.isBye(input)) {
                //print bye message
                ui.byeMessage();
                ui.getIn().close();
                return true;
            } else if (instr.isHistory(input)) {
                process.history(ui,commandList, storage);
            } else if (instr.isAddProject(input)) {
                process.commandHistory(input, ui, storage);
                if (currentProject == null) {
                    currentProject = process.addProject(input, ui, projectmap);
                } else {
                    process.addProject(input, ui, projectmap);
                }
            } else if (instr.isDeleteProject(input)) {
                Project deletedProject = process.deleteProject(input, ui, projectmap);
                process.commandHistory(input, ui, storage);
                if (currentProject == deletedProject) {
                    currentProject = null;
                }
            } else if (instr.isGoToProject(input)) {
                if (currentProject == null) {
                    process.noProject(ui);
                }
                currentProject = process.goToProject(input, ui, projectmap);
                process.commandHistory(input, ui, storage);
            } else if (instr.isList(input)) {
                ui.printList(tasklist, "list");
                process.commandHistory(input, ui, storage);
            } else if (instr.isDone(input)) {
                process.done(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isDeadline(input)) {
                process.deadline(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());

            } else if (instr.isDoAfter(input)) {
                process.doAfter(input, tasklist, ui);
                //Storage.save(tasklist.returnArrayList());
            } else if (instr.isDeletePayment(input)) {
                process.deletePayment(input, managermap, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());

            } else if (instr.isFind(input)) {
                // process.find(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isWithinPeriodTask(input)) {
                process.within(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isSnooze(input)) {
                process.snooze(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());
            /*
            `} else if (instr.isPostpone(input)) {
                process.postpone(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());`
            */
            } else if (instr.isReschedule(input)) {
                // process.reschedule(input, tasklist, ui);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isViewSchedule(input)) {
                process.viewSchedule(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isReminder(input)) {
                //process.reminder(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isEdit(input)) {
                // process.edit(input,tasklist,ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddPayment(input)) {
                process.addPayment(input, managermap, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isgetpayee(input)) {
                process.findPayee(input, ui, managermap);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddPayee(input)) {
                process.addPayee(input, managermap, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isDeletePayee(input)) {
                process.deletePayee(input, managermap, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isInvoice(input)) {
                process.inVoice(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isHistory(input)) {
                process.commandHistory(input, ui,commandList, storage);
            } else if (instr.isSetFund(input)) {
                process.setFund(input, ui, fund);
            } else if (instr.isAddFund(input)) {
                process.addFund(input, ui, fund);
            } else {
                throw new AlphaNUSException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (AlphaNUSException e) {
            process.homePageMessage(currentProject.projectname, projectmap.size(), ui);
        } catch (NullPointerException e) {
            process.homePageMessage(null, projectmap.size(), ui);
        }
        return false;
    }
}
    
