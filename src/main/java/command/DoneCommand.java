package command;

import exception.DukeException;
import storage.Storage;
import task.Recurring;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * command.Command to mark task as done
 */
public class DoneCommand extends Command {
    private int num;

    /**
     * Marks task as done.
     * @param splitStr tokenized user input
     * @throws DukeException if format not followed
     */
    public DoneCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add the index of the task you have completed");
        }
        num = Integer.parseInt(splitStr[1]);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (num < 1 || num > tasks.size()) {
            throw new DukeException("☹ OOPS!!! That task is not in your list");
        }
        tasks.get(num - 1).markAsDone();
        if (tasks.get(num - 1).toString().contains("[R]")) {
            String tempDesc = tasks.get(num - 1).getDesc();
            String tempFreq = tasks.get(num - 1).getFreq();
            Date originalDate = tasks.get(num - 1).getBy();
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy hhmm");

            if (tempFreq.equals("DAY")) {
                Calendar c = Calendar.getInstance();
                c.setTime(originalDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date newDate = c.getTime();
                String strDate = sdfDate.format(newDate);
                Recurring recurring = tasks.get(num - 1).recreate(tempDesc, strDate, tempFreq);
                tasks.add(recurring);
            } else if (tempFreq.equals("WEEK")) {
                Calendar c = Calendar.getInstance();
                c.setTime(originalDate);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                Date newDate = c.getTime();
                String strDate = sdfDate.format(newDate);
                Recurring recurring = tasks.get(num - 1).recreate(tempDesc, strDate, tempFreq);
                tasks.add(recurring);
            } else if (tempFreq.equals("MONTH")) {
                Calendar c = Calendar.getInstance();
                c.setTime(originalDate);
                c.add(Calendar.MONTH, 1);
                Date newDate = c.getTime();
                String strDate = sdfDate.format(newDate);
                Recurring recurring = tasks.get(num - 1).recreate(tempDesc, strDate, tempFreq);
                tasks.add(recurring);
            }
            ui.addToOutput("I've also repeated the same task for the following " + tempFreq.toLowerCase() + "!");
        }
        storage.saveToFile(tasks);
    }
}
