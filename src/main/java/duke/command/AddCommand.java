package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a command class to add a task. The AddCommand class
 * extends from the Command class to represent user instruction
 * to add a new ToDo, FixedDurationTask, Deadline or Event
 * task to the TaskList.
 */
public class AddCommand extends Command {
    /**
     * A new task to be added
     */
    private Task newTask;
    private Boolean isClash = false;

    /**
     * Constructs a AddCommand object.
     *
     * @param newTask Specifies the task to be added.
     */
    public AddCommand(Task newTask) {
        super();
        this.newTask = newTask;
    }

    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            ArrayList<Task> tasksInTheList = tasks.fullTaskList();
            ArrayList<Task> clashTasksInTheList = new ArrayList<Task>();

            /**
             * Check whether the tasks in the list are clashed , if yes then store the clashed tasks into
             * a new array list
             */
            for (Task t : tasksInTheList) {
                if ((t instanceof Deadline || t instanceof Event) && (newTask instanceof Deadline || newTask instanceof Event) ){
                    if (t.getDate().equals(newTask.getDate()) && !t.isDone()) {
                        clashTasksInTheList.add(t);
                        isClash = true;
                    }
                }
            }


            if (isClash) {
                ui.showClashWarning(clashTasksInTheList , newTask);
                Scanner input = new Scanner(System.in);
                boolean isValidUserInput = false ;
                /**
                 * Check if the user have enter the correct input , this check will continue
                 * until the user has enter the correct input
                 */
                while (!isValidUserInput) {
                    String userInput = input.nextLine();
                    if (userInput.equals("Y") || userInput.equals("N") ) {
                        isValidUserInput = true;
                        if (userInput.equals("Y")) {
                            tasks.addTask(newTask);
                            ui.taskAdded(newTask, tasks.getSize());
                            storage.save(tasks.fullTaskList());
                        } else {
                            System.out.println("Alright , I have aborted the task.");
                        }
                    } else {
                        System.out.println("Please enter Y or N only ! (Cap Sensitive) ");
                    }
                }
            }else{
                tasks.addTask(newTask);
                ui.taskAdded(newTask, tasks.getSize());
                storage.save(tasks.fullTaskList());
            }
        } catch (DukeException e) {
            throw new DukeException("Fails to add task. " + e.getMessage());
        }
    }
}
