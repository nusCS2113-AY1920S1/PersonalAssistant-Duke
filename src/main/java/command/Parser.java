package command;

import storage.Storage;
import common.AlphaNUSException;
import task.TaskList;
import project.Fund;
import ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Parser that parses input from the user.
 */
public class Parser {
    public static Instruction instr = new Instruction();
    public Process process = new Process();

    public Parser() throws AlphaNUSException {
    }


    /**
     * Method that parses input from the user and executes processes based on the input.
     *
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.

     * @param ui       Ui that interacts with the user.
     * @param storage  command.Storage for the Tasklist.
     * @param list     CommandList.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws AlphaNUSException if input is not valid.
     */
    public boolean parse(String input, TaskList tasklist, Ui ui, Fund fund,
                                Storage storage, ArrayList<String> list, Set<String> dict) {
        try {
            input = trimInput(input);
            process.projectManager.updateDict(dict);
            storage.writeToDictFile(dict);
            if (instr.isBye(input)) {
                storage.writeToProjectsFile(process.projectManager.projectmap);
                storage.writeToFundFile(fund);
                storage.writeToDictFile(dict);
                storage.writeTocurrentprojectnameFile(process.projectManager.currentprojectname);
                storage.writeToTaskListFile(tasklist);
                ui.byeMessage();
                ui.getIn().close();
                return true;
            } else if (instr.isUndo(input)) {
                process.commandHistory(input, ui, storage);
                process.undo(storage, ui);
            } else if (instr.isLoad(input)) {
                process.backupProjects(ui, fund, storage, list, tasklist);
            } else if (instr.isRedo(input)) {
                process.commandHistory(input, ui, storage);
                process.redo(storage, ui);
            } else if (instr.isViewhistory(input)) {
                process.viewhistory(input, ui, storage);
            } else if (instr.isHistory(input)) {
                process.history(ui,storage);
            } else if (instr.isListProjects(input)) {
                process.listProjects(ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddProject(input)) {
                process.commandHistory(input, ui, storage);
                process.addProject(input, ui, storage, fund);
            } else if (instr.isDeleteProject(input)) {
                process.deleteProject(input, ui, storage, fund);
                process.commandHistory(input, ui, storage);
            } else if (instr.isGoToProject(input)) {
                process.goToProject(input, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isList(input)) {
                ui.printList(tasklist, "list");
                process.commandHistory(input, ui, storage);
            } else if (instr.isDone(input)) {
                process.done(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isTodo(input)) {
                process.addTodo(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isDeadline(input)) {
                process.addDeadline(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isDoAfter(input)) {
                process.doAfter(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
                //Storage.save(tasklist.returnArrayList());
            } else if (instr.isDeletePayment(input)) {
                process.deletePayment(input, ui, storage, dict);
                process.commandHistory(input, ui, storage);
                //storage.save(tasklist.returnArrayList());
            } else if (instr.isFindPayment(input)) {
                process.findPayment(input, storage, ui);
                process.commandHistory(input, ui, storage); 
            } else if (instr.isFindPayee(input)) {
                process.findPayee(input, storage, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isFindTask(input)) {
                process.findTask(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            }  else if (instr.isListTasks(input)) {
                process.listTasks(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isListPayments(input)) {
                process.listPayments(input, storage, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isListPayees(input)) {
                process.listPayees(input, storage, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isWithinPeriodTask(input)) {
                process.within(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isSnooze(input)) {
                process.snooze(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isPostpone(input)) {
                process.postpone(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isReschedule(input)) {
                process.reschedule(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isDeleteTask(input)) {
                process.deleteTask(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isViewSchedule(input)) {
                process.viewSchedule(input, tasklist, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isEdit(input)) {
                process.edit(input,ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddPayment(input)) {
                process.addPayment(input, ui, storage, dict);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddPayee(input)) {
                process.addPayee(input, ui, storage);
                process.commandHistory(input, ui, storage);
            } else if (instr.isDeletePayee(input)) {
                process.deletePayee(input, ui, storage);
                process.commandHistory(input, ui, storage);
            } else if (instr.istotalcost(input)) {
                process.totalCost(input, ui, storage);
                process.commandHistory(input, ui, storage);
            } else if (instr.isSetFund(input)) {
                process.setFund(input, ui, fund, storage);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAddFund(input)) {
                process.addFund(input, ui, fund, storage);
                process.commandHistory(input, ui, storage);
            } else if (instr.isAssignFund(input)) {
                process.assignFund(input, ui, fund);
                process.commandHistory(input, ui, storage);
            } else if (instr.isReduceBudget(input)) {
                process.reduceBudget(input, ui, fund);
                process.commandHistory(input, ui, storage);
            } else if (instr.isShowFund(input)) {
                process.showFund(input, ui, fund);
                process.commandHistory(input, ui, storage);
            } else if (instr.isResetFund(input)) {
                process.resetFund(input, ui, fund);
                process.commandHistory(input, ui, storage);
            } else if (instr.isShowBudget(input)) {
                process.showBudget(input, ui);
                process.commandHistory(input, ui, storage);
            } else if (instr.isHelp(input)) {
                ui.printHelpMessage();
            } else if (instr.isReminder(input)) {
                process.reminder(ui, storage);
                process.commandHistory(input, ui, storage);
            } else {
                throw new AlphaNUSException("\t" + "OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (AlphaNUSException | NullPointerException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Remove whitespace in front and back of input.
     * @param input Input from user.
     * @return Returns trimmed input.
     */
    private String trimInput(String input) {
        return input.trim();
    }
}
    
