package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.util.ArrayList;

public class HelpCommand extends Command {
    private ArrayList<String> commands = new ArrayList<>();

    /**
     * Add list of available commands to an ArrayList.
     *
     * @param commands ArrayList of commands available to user.
     * @return updated ArrayList of commands.
     */
    private ArrayList<String> createCommandList(ArrayList<String> commands) {
        // TODO implement ArrayList of String array [instruction Desc, instruction format] for extension
        // TODO make a command available to user Help by uncommenting your task after implementation
        commands.add("Add Deadline\t\tdeadline DESCRIPTION /by DD-MM-YYYY HH:MM");
        commands.add("Add ToDo\t\t\ttodo DESCRIPTION");
        commands.add("Add Event\t\tevent DESCRIPTION /from DD-MM-YYYY HH:MM /to DD-MM-YYYY HH:MM");
        //commands.add("Do-After\t\t\tdo-after INDEX DESCRIPTION");
        commands.add("Recurring\t\trecurring DESCRIPTION");
        //commands.add("Tentative\t\ttentative DESCRIPTION");
        commands.add("List\t\t\t\tlist");
        commands.add("Done\t\t\t\tdone INDEX");
        commands.add("Delete\t\t\tdelete INDEX");
        commands.add("Find\t\t\t\tfind DESCRIPTION");
        //commands.add("Filter\t\t\tfilter CATEGORY");
        //commands.add("Threshold\t\tthreshold HH");
        //commands.add("Color Code\t\tcolorcode INDEX #RRGGBB");
        //commands.add("Summary\t\t\tsummary");
        commands.add("Sort\t\t\t\tsort");
        //commands.add("View Undone\t\tviewUndone");
        commands.add("Schedule\t\tschedule");
        commands.add("Snooze\t\t\tsnooze");
        commands.add("View Time Slots\tviewFree");
        //commands.add("Week View\t\tviewWeek");
        //commands.add("Calendar View\tviewCalendar");
        //commands.add("Set Reminder\treminder");
        commands.add("Exit Program\tbye");

        return commands;
    }

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param tasks   Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        createCommandList(commands);
        ui.printHelpCommands(commands);
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true if ExitCommand is called, false otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
