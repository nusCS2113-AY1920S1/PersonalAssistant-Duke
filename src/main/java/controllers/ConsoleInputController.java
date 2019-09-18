package controllers;

import exceptions.DukeException;
import models.commands.DeleteCommand;
import models.commands.DoneCommand;
import models.tasks.IRecurring;
import models.tasks.ITask;
import models.tasks.Recurring;
import models.tasks.TaskList;
import views.CLIView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConsoleInputController implements IViewController {

    private CLIView consoleView;
    private TaskFactory taskFactory;
    private RecurringFactory recurringFactory;
    private TaskList taskList;
    private String filePath = "src/main/saves/savefile.txt";

    /**
     * Constructor.
     * @param view : takes in a View model, in this case a command line view.
     */
    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        this.taskFactory = new TaskFactory();
        this.taskList = new TaskList();
        this.recurringFactory = new RecurringFactory();
    }

    private void checkRecurring() {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMMM yyyy");
        Date date = new Date();
        ArrayList<IRecurring> allRecurringTasks = this.taskList.getAllRecurringTasks();
        for (IRecurring recurringTask : allRecurringTasks) {
            long diff = date.getTime() - recurringTask.getStartDate().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diff == 0) {
                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDateTime = localDateTime.plusDays(7);
                Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                String dueDate = formatter.format(newDate);
                String description = recurringTask.getRecurringDescription();
                Recurring newRecurring = new Recurring(description, dueDate, newDate);

                this.taskList.deleteFromRecurring(recurringTask);
                ArrayList<ITask> searchedTasks = this.taskList.getSearchedTasks("search "
                                                    + recurringTask.getRecurringDescription());
                for (ITask searchedTask : searchedTasks) {
                    if (searchedTask.getInitials().equals("R")) {
                        this.taskList.deleteFromList(searchedTask);
                    }
                }
                this.taskList.addToRecurringList(newRecurring, newRecurring);
                this.taskList.addToList(newRecurring);
            } else if (diff > 0) {
                Date newDate = new Date();
                while (diff > 0) {
                    Date oldDate = recurringTask.getStartDate();
                    LocalDateTime localDateTime = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    localDateTime = localDateTime.plusDays(7);
                    newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    diff = date.getTime() - newDate.getTime();
                }
                String dueDate = formatter.format(newDate);
                String description = recurringTask.getRecurringDescription();
                Recurring newRecurring = new Recurring(description, dueDate, newDate);
                this.taskList.deleteFromRecurring(recurringTask);
                ArrayList<ITask> searchedTasks = this.taskList.getSearchedTasks("search "
                                                + recurringTask.getRecurringDescription());
                for (ITask searchedTask : searchedTasks) {
                    if (searchedTask.getInitials().equals("R")) {
                        this.taskList.deleteFromList(searchedTask);
                    }
                }
                this.taskList.addToRecurringList(newRecurring, newRecurring);
                this.taskList.addToList(newRecurring);
            }
        }
    }

    /**
     * Method that is called upon receiving commands from CLI.
     * @param input : Input typed by user into CLI
     */
    @Override
    public void onCommandReceived(String input) {
        checkRecurring();

        if (input.equals("bye")) {
            consoleView.end();
        } else if (input.equals("list")) {
            consoleView.printAllTasks(taskList);
        } else if (input.contains("done")) {
            DoneCommand doneCommand = new DoneCommand(input);
            consoleView.markDone(taskList, doneCommand);
            saveData();
        } else if (input.contains("delete")) {
            DeleteCommand deleteCommand = new DeleteCommand(input);
            consoleView.deleteTask(taskList, deleteCommand);
            saveData();
        } else if (input.contains("find")) {
            try {
                consoleView.findTask(taskList, input);
            } catch (ArrayIndexOutOfBoundsException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        } else if (input.contains("remind")) {
            try {
                consoleView.remindTask(taskList, input);
            } catch (ParseException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        } else if (input.length() == 17 && input.substring(0,8).equals("schedule")) {
            try {
                consoleView.listSchedule(taskList, input);
            } catch (ParseException e) {
                System.out.println("error in scheduling");
            }
        } else if (input.substring(0, 9).equals("recurring")) {
            try {
                Recurring newRecurringTask = recurringFactory.createTask(input);
                boolean anomaly = taskList.addToRecurringList(newRecurringTask, newRecurringTask);
                consoleView.addMessage(newRecurringTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        } else {
            try {
                ITask newTask = taskFactory.createTask(input);
                boolean anomaly = taskList.addToList(newTask);
                consoleView.addMessage(newTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        }
    }
    // TODO refactor saving data and reading data to repository/database

    /**
     * Method that is called in order to saveData to persistent storage.
     */
    public void saveData() {
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(taskList);

            out.flush();
            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that is called when reading data from persistent storage.
     * If file read is empty, will throw EOFException which is suppressed.
     * If file read is corrupted, will throw IOException which is suppressed also.
     */
    public void readData() {
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);

            this.taskList = (TaskList) in.readObject();

            in.close();
            file.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getSuppressed();
        }
    }
}
