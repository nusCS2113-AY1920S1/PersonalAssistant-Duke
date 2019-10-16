package command;

import common.DukeException;
import common.TaskList;
import payment.Payee;
import ui.Ui;
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
            } else if(instr.isInvoice(input)){
                process.inVoice(input, tasklist, ui);
            }else {
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            ui.exceptionMessage(e.getMessage());
        }
        return false;
    }
}
    
