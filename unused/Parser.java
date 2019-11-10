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
import duke.command.RemindCommand;
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

//@@author gervaiseang-unused
/**
 * Code is not used as we are narrowing down the number of features in our program to only
 * include the main features that the user may use inside the program.
 *
 * Represents a parser that breaks down user input into commands.
 */
public class Parser {
    if (!emptyString &&arr[Numbers.ZERO.value].equals("remind")) {
        //remind <taskNumber> /in <howManyDays>
        String afterTaskDesc = "";
        boolean detectBackSlash = false;
        int duration;
        for (int i = Numbers.ONE.value; i < arr.length; i++) {
            if ((arr[i].trim().isEmpty()
                    || !arr[i].substring(Numbers.ZERO.value, Numbers.ONE.value).equals("/")) && !detectBackSlash) {
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
            throw new DukeException("     (>_<) OOPS!!! The description of a "
                    + arr[Numbers.ZERO.value] + " cannot be empty.");
        } else if (afterTaskDesc.isEmpty()) {
            throw new DukeException("     (>_<) OOPS!!! The description for "
                    + arr[Numbers.ZERO.value] + " cannot be empty.");
        } else {
            duration = Integer.parseInt(taskDesc.split("/in",
                    Numbers.TWO.value)[Numbers.ZERO.value].trim()) - Numbers.ONE.value;
            int howManyDays = Integer.parseInt(afterTaskDesc);
            return new RemindCommand(duration, howManyDays);
        }
    }
}