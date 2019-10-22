package command;

import common.AlphaNUSException;
import common.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import project.Project;
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

    private static Project currentProject = null;

    /**
     * Method that parses input from the user and executes processes based on the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage for the Tasklist.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws AlphaNUSException if input is not valid.
     */
    public static boolean parse(String input, TaskList tasklist, Ui ui,
                                Storage storage, HashMap<String, Payee> managermap, HashMap<String, Project> projectmap) {
        try {
            if (instr.isBye(input)) {
                //print bye message
                ui.byeMessage();
                ui.getIn().close();
                return true;
            } else if (instr.isAddProject(input)) {
                if (currentProject == null){
                    currentProject = process.addProject(input, ui, projectmap);
                } else {
                    process.addProject(input, ui, projectmap);
                }
            } else if (instr.isDeleteProject(input)) {
                Project deletedProject = process.deleteProject(input, ui, projectmap);
                if (currentProject == deletedProject) {
                    currentProject = null;
                }
            } else if (instr.isGoToProject(input)) {
                currentProject = process.goToProject(input, ui, projectmap);
            }

            else if (currentProject == null) {
                process.noProject(ui);
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
            } else if (instr.isDeletePayment(input)) {
                process.deletePayment(input, managermap, ui);
                //storage.save(tasklist.returnArrayList());

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
            } else if (instr.isAddPayment(input)) {
                process.addPayment(input, managermap, ui);
            } else if (instr.isgetpayee(input)) {
                process.findPayee(input, ui, managermap);
            } else if (instr.isAddPayee(input)) {
                process.addPayee(input, managermap, ui);
            } else if (instr.isDeletePayee(input)) {
                process.deletePayee(input, managermap, ui);
            } else if (instr.isInvoice(input)) {
                process.inVoice(input, tasklist, ui);
            } else {
                throw new AlphaNUSException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            process.homePageMessage(currentProject.projectname, projectmap.size(), ui);
        } catch (AlphaNUSException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (NullPointerException e) {
            process.homePageMessage(null, projectmap.size(), ui);
        }
        return false;
    }
}
    
