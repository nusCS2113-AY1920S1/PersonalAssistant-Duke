package controllers;

import exceptions.DukeException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import models.commands.RescheduleCommand;
import models.data.IProject;
import models.tasks.IRecurring;
import models.tasks.ITask;
import models.tasks.PeriodTask;
import models.tasks.Recurring;
import models.tasks.TaskList;
import repositories.ProjectRepository;
import views.CLIView;

public class ConsoleInputController implements IViewController {

    private CLIView consoleView;
    private TaskFactory taskFactory;
    private RecurringFactory recurringFactory;
    private PeriodTaskFactory periodTaskFactory;
    private TaskList taskList;
    private String filePath = "src/main/saves/savefile.txt";
    private ProjectRepository projectRepository;

    /**
     * Constructor.
     * @param view : takes in a View model, in this case a command line view.
     */
    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        this.taskFactory = new TaskFactory();
        this.taskList = new TaskList();
        this.recurringFactory = new RecurringFactory();
        this.periodTaskFactory = new PeriodTaskFactory();
        this.projectRepository = new ProjectRepository();
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
        Scanner inputReader = new Scanner(input);
        String command = inputReader.next();

        switch (command) {
        case "bye":
            consoleView.end();
            break;
        case "list":
            consoleView.printAllTasks(taskList);
            break;
        case "done":
        case "delete":
            if (inputReader.hasNext()) {
                switch (command) {
                    case "done":
                        consoleView.markDone(taskList, input);
                        saveData();
                        break;
                    case "delete":
                        consoleView.deleteTask(taskList, input);
                        saveData();
                        break;
                    default:
                }
            } else {
                consoleView.consolePrint("Oops! Please enter task number.");
            }
            break;
        case "find":
            try {
                consoleView.findTask(taskList, input);
            } catch (ArrayIndexOutOfBoundsException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "remind":
            try {
                consoleView.remindTask(taskList, input);
            } catch (ParseException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "schedule":
            try {
                consoleView.listSchedule(taskList, input);
            } catch (ParseException e) {
                System.out.println("Error in scheduling");
            }
            break;
        case "free":
            try {
                consoleView.findFreeSlots(taskList, input);
            } catch (ParseException e) {
                System.out.print("Wrong date time input format");
            }
            break;
        case "reschedule":
            try {
                RescheduleCommand rescheduleCommand = new RescheduleCommand(input);
                consoleView.rescheduleTask(taskList, rescheduleCommand);
                saveData();
            } catch (ArrayIndexOutOfBoundsException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "recurring":
            try {
                Recurring newRecurringTask = recurringFactory.createTask(input);
                boolean anomaly = taskList.addToRecurringList(newRecurringTask, newRecurringTask);
                consoleView.addMessage(newRecurringTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "period":
            try {
                PeriodTask newPeriodTask = periodTaskFactory.createTask(input);
                boolean anomaly = taskList.addToList(newPeriodTask);
                consoleView.addMessage(newPeriodTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "event":
        case "todo":
        case "deadline":
        case "doafter":
            try {
                ITask newTask = taskFactory.createTask(input);
                boolean anomaly = taskList.addToList(newTask);
                consoleView.addMessage(newTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "create":
            // Creation of a new project with a given name and a number of numbers
            boolean isProjectCreated = projectRepository.addToRepo(input);
            if (!isProjectCreated) {
                consoleView.consolePrint("Creation of Project failed. Please check parameters given!");
            } else {
                consoleView.consolePrint("Project created!");
            }
            break;
        case "view":
            ArrayList<IProject> allProjects = projectRepository.getAll();
            consoleView.viewAllProjects(allProjects);
            break;
        default:
            consoleView.consolePrint("Invalid inputs. Please refer to User Guide or type help!");
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
