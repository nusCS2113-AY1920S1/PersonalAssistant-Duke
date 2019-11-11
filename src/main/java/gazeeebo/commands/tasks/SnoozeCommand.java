//@@author mononokehime14

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TasksPageStorage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;

import java.io.IOException;

import gazeeebo.exception.DukeException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;


public class SnoozeCommand extends Command {
    /**
     * This method snooze the date of deadline and event.
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of find command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list,
                        Ui ui,
                        Storage storage,
                        Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask,
                        TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.length() == 6) {
                throw new DukeException("OOPS!!! The object of a snoozing cannot be null.");
            } else {
                TriviaStorage triviaStorage = new TriviaStorage();
                triviaManager.learnInput(ui.fullCommand, triviaStorage);
                int index = Integer.parseInt(ui.fullCommand.substring(6).trim()) - 1;
                if (index > list.size() - 1 || index < 0) {
                    throw new DukeException("Please input correct task index");
                }
                String description = list.get(index).description;
                System.out.println("You are snoozing this task: "
                        + list.get(index).description);
                System.out.println("Please indicate how much time you want to snooze");
                ui.readCommand();
                if (ui.fullCommand.split(" ").length != 4) {
                    throw new DukeException("Please follow th correct input format");
                }
                int year = Integer.parseInt(ui.fullCommand.split(" ")[0]);
                int day = Integer.parseInt(ui.fullCommand.split(" ")[2]);
                int month = Integer.parseInt(ui.fullCommand.split(" ")[1]);
                int hour = Integer.parseInt(ui.fullCommand.split(" ")[3]);

                if (list.get(index).listFormat().contains("by")) {
                    String date = list.get(index).toString().split("\\|")[3].substring(4);
                    LocalDateTime newDate = LocalDateTime
                            .parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    newDate = newDate.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour);
                    String newBy = newDate
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Task snoozedDeadline = new Deadline(description, newBy);
                    list.remove(index);
                    list.add(snoozedDeadline);
                    System.out.println("Okay. I've prolonged this task's deadline: ");
                    System.out.println(snoozedDeadline.listFormat());
                } else {
                    String date = list.get(index).toString()
                            .split("\\|")[3].substring(4)
                            .split(" ")[0];
                    String start = list.get(index).toString()
                            .split("\\|")[3].substring(4)
                            .split(" ")[1].split("-")[0];
                    String end = list.get(index).toString()
                            .split("\\|")[3].substring(4
                    ).split(" ")[1].split("-")[1];
                    LocalDate newDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalTime newStart = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
                    LocalTime newEnd = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm:ss"));
                    newDate = newDate.plusYears(year).plusMonths(month).plusDays(day);
                    newStart = newStart.plusHours(hour);
                    newEnd = newEnd.plusHours(hour);
                    String newAt = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            + " " + newStart.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                            + "-" + newEnd.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    Event snoozedEvent = new Event(description, newAt);
                    list.remove(index);
                    list.add(snoozedEvent);
                    System.out.println("Okay. I've prolonged this task's time: ");
                    System.out.println(snoozedEvent.listFormat());
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                TasksPageStorage tasksPageStorage = new TasksPageStorage();
                tasksPageStorage.writeToSaveFile(sb.toString());
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("snooze");
        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}