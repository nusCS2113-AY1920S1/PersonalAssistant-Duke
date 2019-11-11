package command;

import storage.Storage;
import common.AlphaNUSException;
import common.CommandFormat;
import task.TaskList;
import payment.Payee;
import payment.PaymentManager;
import payment.Payments;
import payment.Status;
import project.Fund;
import project.Project;
import project.ProjectManager;
import task.Deadline;
import task.DoAfterTasks;
import task.Task;
import task.WithinPeriodTask;
import ui.Ui;

import java.lang.instrument.IllegalClassFormatException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Calendar;

public class Process {
    private SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private CommandFormat commandformat = new CommandFormat();
    ProjectManager projectManager = new ProjectManager();
    private payment.Status status;

    private static final int MAX_FUND = 500000;
    private static final int MIN_FUND = 0;
    private static ArrayList<String> canUndoRedo = new ArrayList<String>();

    static {
        canUndoRedo.add("add payment");
        canUndoRedo.add("delete payment");
        canUndoRedo.add("add payee");
        canUndoRedo.add("delete payee");
    }

    Process() throws AlphaNUSException {
    }

    //@@author karansarat
    /**
     * Trims leading and trailing whitespace of an array of strings.
     * @param arr The array of Strings to clean.
     * @return cleanArr The array of Strings after cleaning.
     */
    private String[] cleanStrStr(String[] arr) {
        String[] cleanArr = arr.clone();
        for (int i = 0; i < arr.length; i++) {
            cleanArr[i] = arr[i].trim();
        }
        return cleanArr;
    }

    //===========================* Project *================================

    //@@author leowyh
    /**
     * Processes the list project command to list all existing projects in the projectmap.
     * @param ui Ui that interacts with the user.
     * @return
     */
    public void listProjects(Ui ui) {
        ArrayList<Project> projectslist = projectManager.listProjects();
        if (projectslist.isEmpty()) {
            ui.printNoProjectMessage();
            return;
        }
        ui.printProjectsList(projectslist, projectManager.currentprojectname);
    }

    //@@author leowyh
    /**
     * Processes the add project command to add a new project to the projectmap.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @return
     */
    public void addProject(String input, Ui ui, Storage storage, Fund fund) {
        try {
            String[] splitproject = input.split("pr/", 2);
            splitproject = cleanStrStr(splitproject);
            String[] splitamount = splitproject[1].split("am/", 2);
            splitamount = cleanStrStr(splitamount);
            //input validity check
            if (splitamount.length != 2) {
                ui.exceptionMessage("\t" + "Incorrect input" + "\n"
                        + "\t" + "Correct Format: " + commandformat.addProjectFormat());
                return;
            }

            String projectname = splitamount[0];
            String inputamount = splitamount[1];

            if (projectname.isEmpty()) {
                ui.exceptionMessage("\t" + "Project name cannot be empty!" + "\n"
                        + "\t" + "Correct Format: " + commandformat.addProjectFormat());
                return;
            }
            if (projectManager.projectmap.containsKey(projectname)) {
                ui.exceptionMessage("\t" + "Project already exists!");
                return;
            }

            if (inputamount.isEmpty()) {
                Project newProject = projectManager.addProject(projectname);
                int projectsize = projectManager.projectmap.size();
                ui.printAddProject(newProject, projectsize);
            } else {
                double projectamount = Double.parseDouble(inputamount);
                if (projectamount < MIN_FUND) {
                    ui.exceptionMessage("     :( OOPS!!! Please enter a positive value. ");
                } else if (projectamount > MAX_FUND) {
                    ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000. ");
                    return;
                }
                if (fund.getFundRemaining() >= projectamount) {
                    fund.takeFund(projectamount);
                    Project newProject = projectManager.addProject(projectname, projectamount);
                    int projectsize = projectManager.projectmap.size();
                    ui.printAddProject(newProject, projectsize);
                } else {
                    ui.exceptionMessage("     :( OOPS!!! There is not enough fund. "
                            + "Please decrease the amount of fund assigned");
                    System.out.print(fund.giveFund());
                }
            }
        } catch (NumberFormatException e) {
            ui.exceptionMessage("\t" + "Amount of funds should be a number!");
        } catch (IndexOutOfBoundsException e) {
            ui.exceptionMessage(e.getMessage());
        }
    }

    //@@author leowyh
    /**
     * Processes the delete project command to delete a project from the projectmap.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void deleteProject(String input, Ui ui, Storage storage, Fund fund) throws AlphaNUSException {
        try {
            String[] split = input.split("pr/", 2);
            split = cleanStrStr(split);
            if (split.length != 2) {
                ui.exceptionMessage("\t" + "Incorrect input" + "\n"
                        + "\t" + "Correct Format: " + commandformat.deleteProjectFormat());
                return;
            }
            String projectname = split[1];
            if (projectname.isEmpty()) {
                ui.exceptionMessage("\t" + "Project name cannot be empty!" + "\n"
                        + "\t" + "Correct Format: " + commandformat.deleteProjectFormat());
                return;
            }
            if (!projectManager.projectmap.containsKey(projectname)) {
                ui.exceptionMessage("\t" + "Project does not exist!");
                return;
            }
            double budget = projectManager.projectmap.get(projectname).budget;
            fund.addFund(budget);
            Project deletedProject = projectManager.deleteProject(projectname);
            int projectsize = projectManager.projectmap.size();
            ui.printDeleteProject(deletedProject, projectsize, fund);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("\t" + ":( OOPS!!! Wrong input format!"
                    + "\n\tCorrect input format is: delete project pr/PROJECT_NAME");
        }
    }

    //@@author leowyh
    /**
     * Processes the goto project command to set a project in the projectmap
     * as the current project that the user is working on.
     *
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    //@return Returns the Project object of the project that was gone to.
    public void goToProject(String input, Ui ui) {
        try {
            String[] split = input.split(" ", 2);
            split = cleanStrStr(split);
            if (split.length != 2) {
                ui.gotoExceptionMessage("\t" + "Incorrect input format\n" + "\t"
                    + "Correct Format: " + commandformat.gotoProjectFormat(), projectManager.listProjects());
                return;
            }

            int projectindex = Integer.parseInt(split[1]) - 1;
            if (projectManager.projectmap.isEmpty()) {
                ui.printNoProjectMessage();
                return;
            }
            String currentprojectname = projectManager.gotoProject(projectindex);
            ui.printGoToProject(currentprojectname);
        } catch (NumberFormatException e) {
            ui.gotoExceptionMessage("\t" + "Please make sure that the index is an Integer\n"
                    + "\t" + "Correct Format: " + commandformat.gotoProjectFormat(), projectManager.listProjects());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.gotoExceptionMessage("\t" + "No existing project with that index\n"
                    + "\t" + "Correct Format: " + commandformat.gotoProjectFormat(), projectManager.listProjects());
        }
    }

    //@@author lijiayu980606
    /**
     * Process the set fund command to set a fund to all projects.
     * Command format: set fund am/AMOUNT_OF_FUND.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void setFund(String input, Ui ui, Fund fund, Storage storage) {
        try {
            String[] split = input.split("am/", 2);
            Double amount = Double.parseDouble(split[1]);
            if (amount < MIN_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value. ");
                return;
            } else if (amount > MAX_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000. ");
                return;
            } else {
                if (fund.getFund() == 0.0) {
                    fund.setFund(amount);
                    ui.printSetFundMessage(fund);
                } else {
                    ui.exceptionMessage("     :( OOPS!!! The fund is set already. "
                            + "Please use change fund command to modify instead.");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The amount of fund must be "
                + "a positive number and mustser not be empty!");
        }
    }

    //@@author lijiayu980606
    /**
     * Process the add fund command to add fund value to all projects
     * Command format: add fund add/AMOUNT_OF_FUND.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void addFund(String input, Ui ui, Fund fund, Storage storage) {
        try {
            String[] split = input.split("add/", 2);
            Double amount = Double.parseDouble(split[1]);
            if (amount < MIN_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value. ");
            } else if (amount > MAX_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000. ");
            } else if (fund.getFund() + amount >= MAX_FUND) {
                ui.exceptionMessage("     :( Oops!!! The total fund will be above the limit. You can still add "
                        + (MAX_FUND - fund.getFund()) + "dollars to your current fund!");
            } else {
                fund.addFund(amount);
                ui.printAddFundMessage(fund, amount);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The amount of fund must be"
                + "a positive number and mustser not be empty!");
        }
    }

    //@@author lijiayu980606
    /**
     * Process the add fund command to add fund value to specific project.
     * Command Format: assign budget pr/PROJECT_NAME am/AMOUNT_OF_FUND.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void assignFund(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("pr/| am/");
            String projectname = split[1];
            Double amount = Double.parseDouble(split[2]);
            if (!projectManager.projectmap.containsKey(projectname)) {
                System.out.println("\t" + "Project does not exist!");
                return;
            } else if (amount < MIN_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value. ");
            } else if (amount > MAX_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000. ");
            } else {
                if (fund.getFundRemaining() >= amount) {
                    fund.takeFund(amount);
                    projectManager.assignBudget(projectname, amount);
                    ui.printAssignFundMessage(fund, amount, projectManager.projectmap.get(projectname));
                } else {
                    ui.exceptionMessage("     :( OOPS!!! There is not enough fund. "
                            + "Please decrease the amount of fund assigned)");
                    System.out.print(fund.giveFund());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command "
                            + "format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no project with that name yet, "
                            + "please add the project first!");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The amount of fund must be"
                + "a positive number and must not be empty!");
        }
    }

    //@@author lijiayu980606
    /**
     * Show the current fund status.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void showFund(String input, Ui ui, Fund fund) {
        System.out.print(Ui.line);
        System.out.print(fund.giveFund());
        System.out.print(Ui.line);
    }

    //@@author lijiayu980606
    /**
     * Process the change fund command to add fund value to all projects.
     * TODO future implementation: user need to key in password to enable this action.
     * Command format: change fund new/AMOUNT_OF_FUND.
     * @param input Input from the user.
     * @param ui Ui that interacts with the user.
     * @param fund the total fund the that the organisation owns
     */
    public void resetFund(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("new/", 2);
            Double newFund = Double.parseDouble(split[1]);
            if (newFund < MIN_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value. ");
            } else if (newFund > MAX_FUND) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000. ");
            } else if (fund.getFundTaken() > newFund) {
                ui.exceptionMessage("     :( Oops!!! new fund should not be more than the sum of assigned budgets!\n "
                        + "   You have assigned " + (fund.getFundTaken()) + " dollars to your projects!");
            } else {
                fund.setFund(newFund);
                ui.printResetFundMessage(fund, newFund);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The amount of fund must be a positive"
                + "number and must not be empty!");
        }
    }


    //@@author leowyh

    /**
     * Processes the backup command to load sample data from storage for PE testing.
     * @param ui Ui that interacts with the user.
     * @param fund the total fund the that the organisation owns.
     * @param storage Storage for managing data to be stored on local disk.
     * @param commandlist History of commands.
     * @throws AlphaNUSException for reading errors from json file
     */
    public void backupProjects(Ui ui, Fund fund, Storage storage, ArrayList<String> commandlist, TaskList taskList)
                                throws AlphaNUSException {
        Fund backupfund = storage.readFromBackupFundFile();
        fund.loadFund(backupfund.getFund(), backupfund.getFundTaken(), backupfund.getFundRemaining());
        LinkedHashMap<String, Project> projectmap = storage.readFromBackupProjectsFile();
        projectManager.loadBackup(projectmap);
        TaskList tasklist = storage.readFromBackupTaskListFile();
        taskList.loadBackup(tasklist);
        ArrayList<String> backupcommandlist = storage.readFromBackupCommandsFile();
        commandlist.clear();
        commandlist.addAll(backupcommandlist);
        ui.printBackupComplete(projectmap.size(), backupfund);
    }


    //@@author lijiayu980606
    /**
     * Process the reduce budget command to reduce fund assigned to a specific project.
     * Command Format: reduce budget pr/PROJECT_NAME am/AMOUNT_OF_FUND.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param fund  the total fund the that the organisation owns
     */
    public void reduceBudget(String input, Ui ui, Fund fund) {
        try {
            String[] split = input.split("pr/| am/");
            String projectname = split[1];
            Double amount = Double.parseDouble(split[2]);
            if (!projectManager.projectmap.containsKey(projectname)) {
                System.out.println("\t" + ":( OOPS!!! Project does not exist!");
                return;
            } else if (amount < 0 || amount > 500000) {
                ui.exceptionMessage("     :( OOPS!!! Please enter a positive value of no more than 500,000.  ");
            } else {
                double newbudget = projectManager.projectmap.get(projectname).budget - amount;
                if (newbudget < projectManager.projectmap.get(projectname).spending) {
                    ui.exceptionMessage("     :( OOPS!!! The remaining budget is not sufficient.  ");
                    showBudget("show budget pr/" + projectname, ui);
                    return;
                }
                projectManager.projectmap.get(projectname).budget = newbudget;
                projectManager.projectmap.get(projectname).remaining -= amount;
                fund.retrieveFund(amount);
                ui.printReduceBudgetMessage(fund, amount, projectManager.projectmap.get(projectname), projectname);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no project with that name, please add the project first!");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The amount of fund must be a positive number and must not be empty!");
        }
    }

    //===========================* Deadline *================================

    //@@author lijiayu980606
    /**
     * Processes the add todo command.
     * format: add todo d/DESCRIPTION.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void addTodo(String input, TaskList tasklist, Ui ui) {
        try {
            String[] split = input.split("d/");
            String description = split[1];
            Task todo = new Task(description);
            tasklist.addTask(todo);
            ui.printAddedMessage(todo,tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. Correct format: add todo d/DESCRIPTION");
        }
    }

    //@@author lijiayu980606
    /**
     * Processes the add deadline command.
     * format: add deadline d/DESCRIPTION by/DATE.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void addDeadline(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split("d/|by/");
            String taskDescription = splitspace[1];
            String date = splitspace[2];
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date ddl = sdf.parse(date);
            Deadline deadline = new Deadline(taskDescription, ddl);
            tasklist.addTask(deadline);
            ui.printAddedMessage(deadline, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. "
                    + "Correct format: add deadline d/DESCRIPTION by/DATE");
        } catch (ParseException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong date format. Correct format: dd-MM-yyyy");
        }
    }

    /**
     * Processes the done command and sets the task specified as done.
     * format: done id/ID.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void done(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split("id/", 2);
            int numdone = Integer.parseInt(arr[1]) - 1;
            if (numdone > tasklist.size()) {
                ui.exceptionMessage("     :( OOPS!!! Required task is not found.");
                return;
            }
            tasklist.get(numdone).setDone();
            ui.printDoneMessage(numdone, tasklist);

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. Correct format: done id/ID");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The id must be a positive number and must not be empty!");
        }
    }

    /**
     * Processes the delete task command and removes task from tasklist.
     * format: delete id/ID.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void deleteTask(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split("id/", 2);
            int id = Integer.parseInt(splitspace[1]) - 1;
            if (id >= tasklist.size()) {
                ui.exceptionMessage("     :( OOPS!!! Required task is not found.");
                return;
            }
            ui.printDeleteTaskMessage(id, tasklist);
            tasklist.deleteTask(id);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format! Correct format: delete id/ID");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The id must be a positive number and must not be empty!");
        }
    }

    /**
     * Processes the delete task command and removes task from tasklist.
     * format: find task key/KEY_WORD.
     * @param input Input from the user.
     * @param taskList Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void findTask(String input, TaskList taskList, Ui ui) {
        try {
            String[] split = input.split("key/", 2);
            String keyword = split[1];
            TaskList resultList = new TaskList();
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).getDescription().contains(keyword)) {
                    resultList.addTask(taskList.get(i));
                }
            }
            if (resultList.size() == 0) {
                ui.exceptionMessage("     No matching tasks!");
                return;
            }
            ui.printList(resultList,"find");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format! Correct format: find task key/KEY_WORD");
        }
    }

    /**
     * Processes the list tasks command.
     * format: list tasks.
     * @param input Input from the user.
     * @param taskList Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void listTasks(String input, TaskList taskList, Ui ui) {
        try {
            if (taskList.size() == 0) {
                ui.exceptionMessage("     :( OOPS!!! The tasklist is empty for now.");
                return;
            }
            ui.printList(taskList,"list");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format! Correct format: list tasks");
        }
    }

    //@@author
    /**
     * Processes the View Schedule command and outputs the schedule for the specific date entered in the input.
     * format: view schedule d/DATE.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void viewSchedule(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] split = input.split("d/", 2);
            String datestring = split[1];
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(datestring);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.getType().equals("T")) {
                    continue;
                }
                if (sdf.format(date).equals(tasks.getDateStr())) {
                    findlist.addTask(tasks);
                }
            }
            if (findlist.size() == 0) {
                ui.exceptionMessage("     No schedule on that day!");
                return;
            }
            ui.printList(findlist, "View Schedule");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong command format. Correct format: view schedule d/DATE");
        } catch (ParseException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong date format. Correct format: dd-MM-yyyy");
        }
    }

    /**
     * Processes the DoAfter command and adds a task,
     * which has to be done after another task or a specific date and time,
     * to the user's Tasklist.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void doAfter(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String taskTime = splittime[1];
            if (taskTime.contains("/")) {
                Date formattedtime = dateformat.parse(taskTime);
                DoAfterTasks after = new DoAfterTasks(taskDescription, dateformat.format(formattedtime));
                tasklist.addTask(after);
                ui.printAddedMessage(after, tasklist);
            } else {
                DoAfterTasks after = new DoAfterTasks(taskDescription, taskTime);
                tasklist.addTask(after);
                ui.printAddedMessage(after, tasklist);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! The description of a DoAfter cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     :( OOPS!!! Format of time is wrong. ");
        }
    }

    /**
     * Processes the within command and adds a withinPeriodTask to the user's Tasklist.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    public void within(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String[] splitand = splittime[1].split("and ", 2);
            String taskstart = splitand[0];
            String taskend = splitand[1];
            Date formattedtimestart = dateformat.parse(taskstart);
            Date formattedtimeend = dateformat.parse(taskend);
            WithinPeriodTask withinPeriodTask = new WithinPeriodTask(taskDescription,
                    dateformat.format(formattedtimestart), dateformat.format(formattedtimeend));
            tasklist.addTask(withinPeriodTask);
            ui.printAddedMessage(withinPeriodTask, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! The description of a withinPeriodTask cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     :( OOPS!!! Format of time is wrong.");
        }
    }


    //@@author lijiayu980606
    /**
     * Process the snooze command and automatically postpone the selected deadline task by 1 hour.
     * format: snooze id/ID.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void snooze(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split("id/", 2);
            int id = Integer.parseInt(arr[1]) - 1;
            if (id >= tasklist.size()) {
                ui.exceptionMessage("     :( OOPS!!! Required task is not found.");
                return;
            } else if (tasklist.get(id).getType().equals("D")) {
                Date formattedtime = tasklist.get(id).getDate();
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(formattedtime);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date newDate = calendar.getTime();
                tasklist.get(id).setDate(newDate);
                ui.printSnoozeMessage(tasklist.get(id));
            } else {
                ui.exceptionMessage("     :( OOPS!!! Please select a deadline type task to snooze.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. Correct format: snooze id/ID ");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The id and number of days to snooze must be "
                    + "a positive integer and must not be empty!");
        }
    }

    //@@author lijiayu980606
    /**
     * Process the postpone command and postpone the selected deadline task by required number of hours.
     * format: postpone id/ID n/DAYS.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void postpone(String input, TaskList tasklist, Ui ui) {
        try {
            String[] split = input.split("id/| n/", 3);
            int id = Integer.parseInt(split[1]) - 1;
            int delaydays = Integer.parseInt(split[2]);
            if (id >= tasklist.size()) {
                ui.exceptionMessage("     :( OOPS!!! Required task is not found.");
                return;
            } else if (tasklist.get(id).getType().equals("D")) {
                Date formattedtime = tasklist.get(id).getDate();
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(formattedtime);
                calendar.add(Calendar.DAY_OF_MONTH, delaydays);
                Date newDate = calendar.getTime();
                tasklist.get(id).setDate(newDate);
                ui.printPostponeMessage(tasklist.get(id));
            } else {
                ui.exceptionMessage("     :( OOPS!!! Please select a deadline type task to postpone.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. "
                    + "Correct format:'postpone id/ID n/DAYS'");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The id and number of days to postpone must be "
                    + "a positive integer and must not be empty!");
        }
    }


    //@@author lijiayu980606
    /**
     * Process the reschedule command and reschedule the selected deadline task.
     * format: reschedule id/ID d/DATE.
     * @param input    Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui       Ui that interacts with the user.
     */
    public void reschedule(String input, TaskList tasklist, Ui ui) {
        try {
            String[] split = input.split("id/| d/", 3);
            int id = Integer.parseInt(split[1]) - 1;
            String delay = split[2];
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (id >= tasklist.size()) {
                ui.exceptionMessage("     :( OOPS!!! Required task is not found.");
                return;
            } else if (tasklist.get(id).getType().equals("D")) {
                Date formattedtime = sdf.parse(delay);
                tasklist.get(id).setDate(formattedtime);
                ui.printRescheduleMessage(tasklist.get(id));
            } else {
                ui.exceptionMessage("     :( OOPS!!! Please select a deadline type task to reschedule.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. "
                    + "Correct format: reschedule id/ID d/DATE");
        } catch (NumberFormatException e) {
            ui.exceptionMessage("     :( OOPS!!! The id must be positive integer and must not be empty!");
        } catch (ParseException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong date format. Correct format: dd-MM-yyyy");
        }
    }


    //===========================* Payments *================================
    //@@karansarat
    /**
     * Splits the user input according to regex to get the information needed.
     * 
     * @param input    raw user input
     * @param cmd      the command the user invoked
     * @param limit    the String.split limit
     * @param regex    the String.split regex
     * @param expected expected order of arguments
     * @param ui       used to print exception message
     * @return an array with the split string
     */
    private String[] splitInput(String input, String cmd, int limit, String regex, String expected, Ui ui) {
        String[] splitspace = input.split(cmd, limit);
        String[] splitpayments = splitspace[1].split(regex);
        splitpayments = cleanStrStr(splitpayments);
        return splitpayments;
    }
    
    /**
     * Processes the edit command, amends the data of a payee or payment already existing in the records.
     * INPUT FORMAT: edit p/PAYEE v/INVOICE f/FIELD r/REPLACEMENT
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void edit(String input, Ui ui) {
        try {
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            if (input.contains("i/")) {
                String[] splitpayments = splitInput(input, "edit ", 2, "p/|i/|f/|r/", "pifr", ui);
                PaymentManager.editPayment(splitpayments[1], splitpayments[2],
                    splitpayments[3], splitpayments[4], managermap, ui);
            } else {
                String[] splitpayments = splitInput(input, "edit ", 2, "p/|f/|r/", "pfr", ui);
                PaymentManager.editPayee(splitpayments[1], splitpayments[2],
                    splitpayments[3], managermap, ui);
            }
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format"
                    + "The input format is: edit p/PAYEE v/INVOICE f/FIELD r/REPLACEMENT");
        }
    }

    /**
     * Processes the delete command.
     * INPUT FORMAT: delete payment p/payee i/item.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param storage the Storage.
     * @throws AlphaNUSException for reading errors from json file
     *
     */


    public void deletePayment(String input, Ui ui, Storage storage, Set<String> dict) throws AlphaNUSException {
        String payeename = new String();
        try {
            BeforeAfterCommand.beforeCommand(projectManager, storage);
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            String[] split = splitInput(input, "payment ", 2, "p/|i/", "pi", ui);
            payeename = split[1];
            String itemname = split[2];
            Payments deleted = PaymentManager.deletePayments(payeename, itemname, managermap, dict);
            projectManager.projectmap.get(deleted.project).retrieveBudget(deleted.cost);
            ui.printDeletePaymentMessage(deleted, managermap.get(payeename).payments.size());
            BeforeAfterCommand.afterCommand(projectManager, storage);
        } catch (ArrayIndexOutOfBoundsException | AlphaNUSException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format\n"
                + "     The correct input format is:[delete payment p/PAYEE i/ITEM]");
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! Payment not found, check item field again!");
        } catch (IllegalAccessError e) {
            ui.exceptionMessage("     :( OOPS!!! Payee name provided is not correct!");
            ui.printSuggestion(dict, input, payeename);
        } catch (NullPointerException e) {
            ui.exceptionMessage("     ☹ OOPS!!!");
        }
    }

    /**
     * Processes the add payment command, saves a new payment under a specified payee.
     * INPUT FORMAT: add payment p/payee i/item c/111 v/invoice
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     */
    public void addPayment(String input, Ui ui, Storage storage, Set<String> dict) {
        try {
            BeforeAfterCommand.beforeCommand(projectManager, storage);
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            String currentprojectname = projectManager.currentprojectname;
            String[] splitpayments = splitInput(input, "payment ", 2, "p/|i/|c/|v/", "picv", ui);
            splitpayments = cleanStrStr(splitpayments);
            String payee = splitpayments[1];
            String item = splitpayments[2];
            double cost = Double.parseDouble(splitpayments[3]);
            if (cost < 0) {
                ui.exceptionMessage("     :( OOPS!!! The cost should be a positive number.");
            }
            if (cost > projectManager.projectmap.get(currentprojectname).getRemaining()) {
                ui.printInsufficientBudget(projectManager);
            } else {
                projectManager.projectmap.get(currentprojectname).addPayment(cost);
                String invoice = splitpayments[4];
                Payments payment = PaymentManager.addPayments(currentprojectname, payee, 
                    item, cost, invoice, managermap, dict);
                int paymentsSize = managermap.get(payee).payments.size();
                ui.printAddPaymentMessage(payment, paymentsSize, currentprojectname);
                BeforeAfterCommand.afterCommand(projectManager, storage);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (IllegalAccessError e) {
            ui.exceptionMessage("     ☹ OOPS!!! Payment for this item is already recorded!");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no payee with that name yet, please add the payee first!");
        } catch (AlphaNUSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the add payee command, saves a new payee inside managermap.
     * INPUT FORMAT: add payee p/payee e/email m/matricNum ph/phoneNum
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @param storage used to read from dict.json.
     */
    public void addPayee(String input, Ui ui, Storage storage) {
        String currProjectName = new String();
        try {
            BeforeAfterCommand.beforeCommand(projectManager, storage);
            currProjectName = projectManager.currentprojectname;
            if (currProjectName == null) {
                throw new IllegalAccessError();
            }
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            String[] splitpayments = splitInput(input, "payee ", 2, "p/|e/|m/|ph/", "pemph", ui);
            String payeename = splitpayments[1];
            String email = splitpayments[2];
            String matricNum = splitpayments[3];
            String phoneNum = splitpayments[4];
            Payee payee = PaymentManager.addPayee(currProjectName, payeename, 
                email, matricNum, phoneNum, managermap);
            int payeesize = managermap.size();
            ui.printAddPayeeMessage(payee, payeesize);
            BeforeAfterCommand.afterCommand(projectManager, storage);
        } catch (AlphaNUSException | ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (IllegalAccessError e) {
            ui.exceptionMessage("     :( OOPS!!! Please select a project using the goto command first!");

        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no payee with that name in Project " 
                + currProjectName + " yet, please add the payee first!");
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is a payee with that name in the record!");
        } 
    }

    /**
     * Processes the delete payee command, saves a new payee inside managermap.
     * INPUT FORMAT: delete payee p/payee
     * 
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @throws AlphaNUSException for reading errors from json file
     */
    public void deletePayee(String input, Ui ui, Storage storage) throws AlphaNUSException {
        try {
            BeforeAfterCommand.beforeCommand(projectManager, storage);
            String payeename = new String();
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            String currentprojectname = projectManager.currentprojectname;
            String[] splitspace = input.split("payee ", 2);
            String[] splitpayments = splitspace[1].split("p/");
            splitpayments = cleanStrStr(splitpayments);
            payeename = splitpayments[1];
            Payee payee = PaymentManager.deletePayee(payeename, managermap);
            int payeesize = managermap.size();
            ui.printdeletePayeeMessage(splitpayments[1], payee, payeesize, currentprojectname);
            double totalspending = 0;
            for (Payments p : payee.payments) {
                totalspending += p.cost;
            }
            projectManager.projectmap.get(currentprojectname).addBudget(totalspending);
            projectManager.projectmap.get(currentprojectname).retrieveBudget(totalspending);
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, payeename);
            BeforeAfterCommand.afterCommand(projectManager, storage);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format (refer to user guide)");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no payee with that name yet, please add the payee first!");
        } catch (AlphaNUSException e) {
            ui.exceptionMessage("     :( OOPS!!! Please input the correct command format!"
                    + "The correct format is [delete payee p/PAYEE_NAME]");
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no payee with that name yet, please add the payee first!");
        }
    }

    /**
     * Processes the find command and outputs a list of payments from the payee name
     * given.
     * format: find payee p/PAYEE
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @throws AlphaNUSException for reading errors from json file
     */
    @SuppressWarnings("unchecked")
    public void findPayee(String input, Storage storage, Ui ui) throws AlphaNUSException {
        String payee = new String();
        try {
            String[] splitpayments = input.split("p/");
            splitpayments = cleanStrStr(splitpayments);
            payee = splitpayments[1];
            LinkedHashMap<String, Project> projectMapClone = (LinkedHashMap<String, Project>) 
                projectManager.projectmap.clone();
            Payee found = PaymentManager.findPayee(projectMapClone, projectManager.currentprojectname, payee);
            ui.printFoundMessage(found);
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no such payee in the records");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, payee);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format! Correct format: find payee p/PAYEE");
        }
    }

    /**
     * Processes the find command and outputs a list of payments from the payee name
     * given.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @throws AlphaNUSException for reading errors from json file
     */
    @SuppressWarnings("unchecked")
    public void findPayment(String input, Storage storage, Ui ui) throws AlphaNUSException {
        String payee = new String();
        String item = new String();
        try {
            String[] split = splitInput(input, "payment ", 2, "p/|i/", "pi", ui);
            payee = split[1];
            item = split[2];
            LinkedHashMap<String, Project> projectMapClone = (LinkedHashMap<String, Project>) 
                projectManager.projectmap.clone();
            Payee foundPayee = PaymentManager.findPayee(projectMapClone, projectManager.currentprojectname, payee);
            for (Payments found : foundPayee.payments) {
                if (found.item.equals(item)) {
                    ui.printFoundMessage(found);
                    return;
                }
            }
            throw new IllegalAccessError();
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     ☹ OOPS!!! There is no such payee in the records");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, payee);
        } catch (IllegalAccessError e) {
            ui.exceptionMessage("     ☹ OOPS!!! Payment not found, check item field again!");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, item);
        }
    }

    /**
     * Processes the find command and outputs a list of payments from the payee name given.
     * @param input Input from the user.
     * @param ui    Ui that interacts with the user.
     * @throws AlphaNUSException for reading errors from json file
     */
    public void listPayments(String input, Storage storage, Ui ui) throws AlphaNUSException {
        String prName = new String();
        String payeeName = new String();
        try {
            if (input.contains("pr/")) {
                String[] splitspace = input.split("payments ", 2);
                String[] splitpayments = splitspace[1].split("pr/");
                splitpayments = cleanStrStr(splitpayments);
                prName = splitpayments[1];
                if (!projectManager.projectmap.containsKey(prName)) {
                    throw new IllegalCallerException();
                }
                projectManager.gotoProject(prName);
            } else if (input.contains("p/")) {
                String[] splitspace = input.split("payments ", 2);
                String[] splitpayments = splitspace[1].split("p/");
                splitpayments = cleanStrStr(splitpayments);
                HashMap<String, Payee> managerMap = projectManager.getCurrentProjectManagerMap();
                if (!managerMap.containsKey(splitpayments[1])) {
                    payeeName = splitpayments[1];
                    throw new IllegalArgumentException();
                }
                ui.printPaymentList(splitpayments[1], managerMap.get(splitpayments[1]).payments);
                return;
            }
            HashMap<String, Payee> managerMap = projectManager.getCurrentProjectManagerMap();
            ArrayList<ArrayList<Payments>> listOfPayments = PaymentManager.listOfPayments(managerMap);
            prName = projectManager.currentprojectname;

            int count = 0;
            for (ArrayList<Payments> lists : listOfPayments) {
                if (lists.isEmpty()) {
                    count++;
                    continue;
                }
                ui.printPaymentList(prName, lists, lists.get(0).status);
            }
            if (count == 3) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! There are no payees with that name!");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, payeeName);
        } catch (IllegalCallerException e) {
            ui.exceptionMessage("     :( OOPS!!! There are no projects with that name!");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, prName);
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! Please select a project using the goto command first!");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! There are no payments to list!");
        }
    }

    /**
     * Outputs and prints a list of payees based on user input.
     * @param input user input as a string
     * @param storage to read from dict.json
     * @param ui user interface used to print messages
     * @throws AlphaNUSException for reading errors from json file
     */
    public void listPayees(String input, Storage storage, Ui ui) throws AlphaNUSException {
        String prName = new String();
        try {
            if (input.contains("pr/")) {
                String[] splitspace = input.split("payees ", 2);
                String[] splitpayments = splitspace[1].split("pr/");
                splitpayments = cleanStrStr(splitpayments);
                prName = splitpayments[1];
                if (!projectManager.projectmap.containsKey(prName)) {
                    throw new IllegalArgumentException();
                }
                projectManager.gotoProject(splitpayments[1]);
            }
            HashMap<String, Payee> managerMap = projectManager.getCurrentProjectManagerMap();
            if (managerMap.isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            ui.printPayeeList(managerMap);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! There are no payees to list!");
        } catch (IllegalArgumentException e) {
            ui.exceptionMessage("     :( OOPS!!! There are no such projects!");
            Set<String> dict = storage.readFromDictFile();
            ui.printSuggestion(dict, input, prName);
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! Please select a project using the goto command first!");
        }
    }

    //@@author lijiayu980606
    /**
     * Process total cost command and calculate the total cost of a payee.
     * Command format: total cost p/PAYEE_NAME
     * @param input Input from the user.
     * @param ui Ui that interacts with the user.
     * @param storage storage of the programme.
     */
    public void totalCost(String input, Ui ui, Storage storage) {
        try {
            String[] split = input.split("p/", 2);
            String payeeName = split[1];
            HashMap<String, Payee> managermap = projectManager.getCurrentProjectManagerMap();
            String currentprojectname = projectManager.currentprojectname;
            double totalcost = 0;
            for (Payments p : managermap.get(payeeName).payments) {
                totalcost += p.cost;
            }
            ui.printTotalCostMessage(payeeName, totalcost, currentprojectname);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input format. Correct input format: total cost p/PAYEE_NAME");
        }
    }

    //@@author lijiayu980606
    /**
     * reminder of the payments based on the status and deadline.
     * @param ui Ui that interacts with the user.
     * @param storage storage of the programme.
     */
    public void reminder(Ui ui, Storage storage) throws AlphaNUSException {
        ArrayList<Project> projectslist = projectManager.listProjects();
        ArrayList<Payments> approved = new ArrayList<>();
        ArrayList<Payments> tobesorted = new ArrayList<>();
        if (projectslist.isEmpty()) {
            ui.printNoProjectMessage();
            return;
        }
        for (Project project:projectslist) {
            HashMap<String, Payee> managermap = project.managermap;
            for (Payee payee : managermap.values()) { // iterate through the payees
                for (Payments payment : payee.payments) { // iterate through the payments
                    if (payment.status == Status.APPROVED) {
                        approved.add(payment);
                    } else {
                        tobesorted.add(payment);
                    }
                }
            }
        }
        Collections.sort(tobesorted);
        ui.printReminderMessage(tobesorted);
    }

    //@@author lijiayu980606
    /**
     * Process show budget function and show the budget status for a chosen project.
     * Input Format: show budget pr/PROJECT_NAME
     * @param input Input from the user.
     * @param ui Ui that interacts with the user.
     */
    public void showBudget(String input, Ui ui) {
        try {
            String[] split = input.split("pr/");
            String projectname = split[1];
            projectManager.gotoProject(projectname);
            String currentproject = projectManager.currentprojectname;
            Project p = projectManager.projectmap.get(currentproject);
            System.out.print(Ui.line);
            System.out.println("\t The budget for this project is as follows:");
            System.out.println("\t Total budget: " + p.getBudget());
            System.out.println("\t Spent budget: " + p.getSpending());
            System.out.println("\t Remaining budget: " + p.getRemaining());
            System.out.print(Ui.line);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     :( OOPS!!! Wrong input error!"
                    + "The correct input format is: show budget pr/PROJECT_NAME");
        } catch (NullPointerException e) {
            ui.exceptionMessage("     :( OOPS!!! There is no project with that name, please add the project first!");
        }
    }
    //===========================* Command History *================================

    //@@author E0373902
    /**
     * processes the input command and stores it in a json file.
     * @author E0373902
     * @param input Input from the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage that stores the input commands entered by the user.
     */

    public void commandHistory(String input, Ui ui, Storage storage) throws AlphaNUSException {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        String commandTime = input + " ~ " + formattedDate;
        storage.writeToCommandsFile(commandTime);
    }

    /**
     * prints all the input commands stored in the json file.
     * @author E0373902
     * @param ui Ui that interacts with the user.
     * @param storage Storage that stores the input commands entered by the user.
     */
    public void history(Ui ui, Storage storage) throws AlphaNUSException {
        ArrayList<String> commandList = new ArrayList<String>();
        commandList = storage.readFromCommandsFile();
        ui.printHistoryList(commandList);
    }

    /**
     * prints the input commands within the period given by the user.
     * @author E0373902
     * @param input Input from the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage that stores the input commands entered by the user.
     */
    public void viewhistory(String input, Ui ui, Storage storage) throws ParseException, AlphaNUSException {
        try {
            ArrayList<String> commandList = new ArrayList<String>();
            String[] splitspace = input.split(" ", 3);
            String[] splitslash = splitspace[2].split("/", 2);
            String[] splitdates = splitslash[1].split(" ", 3);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date1 = splitdates[0];
            Date dateFirst = sdf.parse(date1);
            String date2 = splitdates[2];
            Date dateSecond = sdf.parse(date2);
            commandList = storage.readFromCommandsFile();
            ArrayList<String> viewhistory = new ArrayList<String>();
            for (int i = 0; i < commandList.size(); i = i + 1) {
                String token = null;
                String token1 = null;
                String[] splitDateCommand = commandList.get(i).split("~", 2);
                for (int j = 0; j < splitDateCommand.length; j = j + 1) {
                    token = splitDateCommand[j];
                }
                String[] splitDateTime = token.split(" ", 3);
                for (int k = 0; k < splitDateTime.length; k = k + 1) {
                    if (k == 1) {
                        token1 = splitDateTime[k];
                    }
                }
                Date dateCommand = sdf.parse(token1);
                if ((dateCommand.compareTo(dateFirst)) >= 0) {
                    if ((dateCommand.compareTo(dateSecond)) <= 0) {
                        viewhistory.add(commandList.get(i));
                    }
                }
            }
            ui.printviewHistoryList(viewhistory, date1, date2);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("\t" + ":( OOPS!!! Wrong input format!"
                    + "\n\tCorrect input format is: view history h/DATE_1 to DATE_2");
        }
    }

    /**
     * undoes the previous command entered by the user.
     * @author E0373902
     * @param ui Ui that interacts with the user.
     * @param storage Storage that stores the project map.
     */
    public void undo(Storage storage, Ui ui) throws AlphaNUSException {
        ArrayList<String> commandList = new ArrayList<>();
        commandList = storage.readFromCommandsFile();
        String command = commandList.get(commandList.size() - 2);
        //projectManager.projectmap = UndoRedoStack.undo();
        int count = 0;
        for (int i = 0; i < canUndoRedo.size(); i = i + 1) {
            if (command.contains(canUndoRedo.get(i))) {
                projectManager.projectmap = storage.readFromUndoFile();
                ui.undoMessage();
            } else {
                count = count + 1;
            }
        }
        if (count == canUndoRedo.size()) {
            ui.cantUndomessage();
        }
    }

    
    /**
     * redoes the previous command entered by the user.
     * @author E0373902
     * @param ui Ui that interacts with the user.
     * @param storage Storage that stores the project map.
     */
    public void redo(Storage storage, Ui ui) throws AlphaNUSException {
        ArrayList<String> commandList = new ArrayList<>();
        commandList = storage.readFromCommandsFile();
        String command1 = commandList.get(commandList.size() - 2);
        if(command1.contains("undo")) {
            projectManager.projectmap = storage.readFromRedoFile();
            ui.redoMessage();
        } else {
            ui.cantRedomessage();
        }
    }
}
