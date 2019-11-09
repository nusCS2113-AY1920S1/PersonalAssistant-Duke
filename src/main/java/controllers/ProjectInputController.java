package controllers;

import static util.constant.ConstantHelper.COMMAND_ADD_MEMBER;
import static util.constant.ConstantHelper.COMMAND_DELETE_MEMBER;
import static util.constant.ConstantHelper.COMMAND_DELETE_TASK;
import static util.constant.ConstantHelper.COMMAND_EDIT_MEMBER;
import static util.constant.ConstantHelper.COMMAND_EDIT_TASK;
import static util.constant.ConstantHelper.COMMAND_EDIT_TASK_REQ;
import static util.constant.ConstantHelper.COMMAND_VIEW_TASKS;
import static util.constant.ConstantHelper.COMMAND_VIEW_TASK_REQ;
import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import models.member.IMember;
import models.member.Member;
import models.member.NullMember;
import models.project.Project;
import models.reminder.IReminder;
import models.reminder.Reminder;
import models.task.ITask;
import models.task.Task;
import repositories.ProjectRepository;
import util.ParserHelper;
import util.date.DateTimeHelper;
import util.factories.MemberFactory;
import util.factories.ReminderFactory;
import util.factories.TaskFactory;
import util.json.JsonConverter;
import util.log.ArchDukeLogger;
import util.uiformatter.AssignmentViewHelper;
import util.uiformatter.CommandHelper;
import util.uiformatter.ViewHelper;

public class ProjectInputController implements IController {
    private ProjectRepository projectRepository;
    private MemberFactory memberFactory;
    private TaskFactory taskFactory;
    private boolean isManagingAProject;
    private ViewHelper viewHelper;
    private CommandHelper commandHelper;
    private JsonConverter jsonConverter = new JsonConverter();
    private Project projectToManage;

    /**
     * Constructor for ProjectInputController takes in a View model and a ProjectRepository.
     * ProjectInputController is responsible for handling user input when user chooses to manage a project.
     * @param projectRepository The object holding all projects.
     */
    public ProjectInputController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.memberFactory = new MemberFactory();
        this.taskFactory = new TaskFactory();
        this.isManagingAProject = true;
        this.viewHelper = new ViewHelper();
        this.commandHelper = new CommandHelper();
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    @Override
    public String[] onCommandReceived(String input) {
        ArchDukeLogger.logInfo(ProjectInputController.class.getName(), "[onCommandReceived] User input: '"
                + input + "'");
        int projectNumber;
        try {
            projectNumber = Integer.parseInt(input);
        } catch (NumberFormatException err) {
            isManagingAProject = false;
            return new String[] {"Input is not a number! Please input a proper project index!"};
        }
        this.projectToManage = projectRepository.getItem(projectNumber);
        isManagingAProject = true;
        return new String[] {"Please enter a new command:"};
    }

    /**
     * Manages the project.
     * @return Boolean variable giving status of whether the exit command is entered.
     */
    public String[] manageProject(String projectFullCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[manageProject]");
        String[] responseToView;
        ArchDukeLogger.logInfo(ProjectInputController.class.getName(), "Managing:"
                + this.projectToManage.getName() + ",input:'"
                + projectFullCommand + "'");
        if (projectFullCommand.matches("exit")) {
            isManagingAProject = false;
            responseToView = projectExit(this.projectToManage);
        } else if (projectFullCommand.matches("add member.*")) {
            responseToView = projectAddMember(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("edit member.*")) {
            responseToView = projectEditMember(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("delete member.*")) {
            responseToView = projectDeleteMember(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("view members.*")) {
            responseToView = projectViewMembers(this.projectToManage);
        } else if (projectFullCommand.matches("role.*")) {
            responseToView = projectRoleMembers(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("view credits.*")) {
            responseToView = projectViewCredits(this.projectToManage);
        } else if (projectFullCommand.matches("add task.*")) {
            responseToView = projectAddTask(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("view tasks.*")) {
            responseToView = projectViewTasks(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("view assignments.*")) {
            responseToView = projectViewAssignments(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("view task requirements.*")) {
            responseToView = projectViewTaskRequirements(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("edit task requirements.*")) {
            responseToView = projectEditTaskRequirements(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("edit task.*")) {
            responseToView = projectEditTask(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("delete task.*")) {
            responseToView = projectDeleteTask(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("assign task.*")) {
            responseToView = projectAssignTask(this.projectToManage, projectFullCommand);
        } else if (projectFullCommand.matches("add reminder.*")) {
            responseToView = projectAddReminder(this.projectToManage,projectFullCommand);
        } else if (projectFullCommand.matches("view reminders"))  {
            responseToView = projectViewReminder(this.projectToManage);
        } else if (projectFullCommand.matches("edit reminder.*")) {
            responseToView = projectEditReminder(this.projectToManage,projectFullCommand);
        } else if (projectFullCommand.matches("delete reminder.*")) {
            responseToView = projectDeleteReminder(this.projectToManage,projectFullCommand);
        } else if (projectFullCommand.matches(".*mark reminder.*")) {
            responseToView = projectSetReminderStatus(this.projectToManage,projectFullCommand);
        } else if (projectFullCommand.matches("view")) {
            responseToView = projectViewSelf(this.projectToManage);
        } else if (projectFullCommand.matches("agenda")) {
            responseToView = projectViewCalender(this.projectToManage);
        } else if (projectFullCommand.matches("help")) {
            responseToView = projectHelp();
        } else if (projectFullCommand.matches("bye")) {
            return end();
        } else {
            return new String[] {"Invalid command. Try again!"};
        }
        jsonConverter.saveProject(this.projectToManage);
        return responseToView;
    }

    private String[] projectViewCalender(Project projectToManage) {
        HashMap<Integer, Integer> currentMonthTasks = projectRepository.getAllTasksInCurrentMonth(projectToManage);
        return viewHelper.consolePrintCalender(currentMonthTasks);
    }

    private String[] projectViewSelf(Project projectToManage) {
        return viewHelper.consolePrintTable(projectRepository.getProjectDetailsForTable(projectToManage),
                DEFAULT_HORI_BORDER_LENGTH);
    }

    private String[] projectHelp() {
        return viewHelper.consolePrintTable(commandHelper.getCommandsForProject(), DEFAULT_HORI_BORDER_LENGTH);
    }

    /**
     * Adds roles to Members in a Project.
     * @param projectToManage : The project specified by the user.
     * @param projectCommand : User input.
     */
    public String[] projectRoleMembers(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectRoleMembers] User input: '"
                + projectCommand + "'");
        String parsedCommands = projectCommand.substring(5);
        String[] commandOptions = parsedCommands.split(" -n ");
        if (commandOptions.length != 2) {
            return new String[] {"Wrong command format! Please enter role INDEX -n ROLE_NAME"};
        }
        int memberIndex = Integer.parseInt(commandOptions[0]);
        IMember selectedMember = projectToManage.getMemberList().getMember(memberIndex);
        if (selectedMember.getClass() != NullMember.class) {
            selectedMember.setRole(commandOptions[1]);
            return new String[] {"Successfully changed the role of " + selectedMember.getName() + " to "
                    + selectedMember.getRole() + "."};
        }
        return new String[] {selectedMember.getDetails()};
    }

    //@@author iamabhishek98
    /**
     * Adds a member to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAddMember(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectToManage] User input: '"
                + projectCommand + "'");
        if (projectCommand.length() < COMMAND_ADD_MEMBER.length()) {
            return new String[] {"Add member command minimum usage must be \"add member -n NAME\"!",
                                 "Please refer to user guide for additional details."};
        }
        String memberDetails = projectCommand.substring(COMMAND_ADD_MEMBER.length());
        int numberOfCurrentMembers = projectToManage.getNumOfMembers();
        memberDetails = memberDetails + " -x " + numberOfCurrentMembers;
        IMember newMember = memberFactory.create(memberDetails);
        if (newMember.getName() != null) {
            if (projectToManage.memberExists(newMember)) {
                return new String[] {"The member you have tried to add already exists!",
                    "Member name: " + newMember.getName(),
                    "Please ensure that each member has a different name."};
            } else {
                projectToManage.addMember((Member) newMember);
                return new String[]
                    {"Added new member to: " + projectToManage.getName(),
                    "Member details " + newMember.getDetails()};
            }
        } else {
            return new String[] {newMember.getDetails()};
        }
    }

    //@@author iamabhishek98
    /**
     * Updates the details of a given member in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditMember(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectEditMember] User input: '"
                + projectCommand + "'");
        try {
            int memberIndexNumber =
                Integer.parseInt(projectCommand.substring(COMMAND_EDIT_MEMBER.length()).split(" ")[0]);
            if (projectToManage.getNumOfMembers() >= memberIndexNumber && memberIndexNumber > 0) {
                String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("-"));
                String output = projectToManage.editMember(memberIndexNumber,updatedMemberDetails);
                return new String[] { output };
            } else {
                return new String[] {"The member index entered is invalid."};
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectEditMember] "
                    + "Please enter the updated member details format correctly.");
            return new String[] {"Please enter the updated member details format correctly."};
        }
    }

    //@@author iamabhishek98
    /**
     * Deletes a member from the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectDeleteMember(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectDeleteMember] User input: '"
                + projectCommand + "'");
        if (projectCommand.length() <= COMMAND_DELETE_MEMBER.length()) {
            return new String[] {"Can't delete members: No member index numbers detected!",
                "Please enter them as space-separated integers."};
        }
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validMemberIndexes = parserHelper.parseMembersIndexes(projectCommand.substring(14),
            projectToManage.getNumOfMembers());
        ArrayList<String> outputMessages = new ArrayList<>(parserHelper.getErrorMessages());
        if (validMemberIndexes.isEmpty()) {
            outputMessages.add("No valid member indexes. Cannot delete members.");
            return outputMessages.toArray(new String[0]);
        }
        Collections.sort(validMemberIndexes);
        Collections.reverse(validMemberIndexes);
        for (Integer index : validMemberIndexes) {
            Member memberToRemove = projectToManage.getMember(index);
            outputMessages.add("Removed member " + index + ": " + memberToRemove.getDetails());
            projectToManage.removeMember(memberToRemove);
        }
        if (!validMemberIndexes.isEmpty()) {
            outputMessages.add("Take note that the member indexes might have changed after deleting!");
        }
        return outputMessages.toArray(new String[0]);
    }

    //@@author iamabhishek98
    /**
     * Displays all the members in the current project.
     * Can be updated later on to include more information (tasks etc).
     * @param projectToManage The project specified by the user.
     */
    private String[] projectViewMembers(Project projectToManage) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectViewMembers]");
        ArrayList<String> allMemberDetailsForTable = projectToManage.getMemberList().getAllMemberDetailsForTable();
        String header = "Members of " + projectToManage.getName() + ":";
        allMemberDetailsForTable.add(0, header);
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), allMemberDetailsForTable.toString());
        return viewHelper.consolePrintTable(allMemberDetailsForTable, DEFAULT_HORI_BORDER_LENGTH);
    }

    //@@author iamabhishek98
    /**
     * Displays the members’ credits, their index number, name, and name of tasks completed.
     * @param projectToManage The project specified by the user.
     */
    private String[] projectViewCredits(Project projectToManage) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectViewCredits]");
        ArrayList<String> allCredits = projectToManage.getCredits();
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "allCredits: " + allCredits.toString());
        if (allCredits.isEmpty()) {
            allCredits.add(0, "There are no members in this project.");
        } else {
            allCredits.add(0, "Here are the credits earned by the members of this project: ");
        }
        return allCredits.toArray(new String[0]);
    }


    /**
     * Adds a task to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAddTask(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectAddTask] User input: '"
                + projectCommand + "'");
        try {
            ITask newTask = taskFactory.create(projectCommand.substring(9));
            if (newTask.getDetails() != null) {
                if (projectToManage.taskExists(newTask)) {
                    return new String[]{"The task you are trying to add already exists!",
                        "Task name: " + newTask.getTaskName(),
                        "Please ensure that each task has a different task name."};
                }
                projectToManage.addTask((Task) newTask);
                return new String[] {"Added new task to the list."};
            }
            return new String[] {"Failed to create new task. Please ensure all "
                                + "necessary parameters are given correctly.",
                                 "Task priority must be an integer between 1 to 5",
                                 "Task credit must be an integer between 0 to 100",
                                 "Date must be a valid date!"};

        } catch (NumberFormatException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectAddTask] "
                    + "Please enter your task format correctly.");
            return new String[] {"Please ensure that your task format are correct and dates are valid."};
        }
    }

    /**
     * Updates the task details of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditTask(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectEditTask] User input: '"
                + projectCommand + "'");
        try {
            if (projectCommand.length() <= COMMAND_EDIT_TASK.length()) {
                return new String[]
                {"No parameters detected. Please enter details in the following format:",
                 "TASK_INDEX [-t TASK_NAME] [-p TASK_PRIORITY] [-d TASK_DUEDATE] [-c TASK_CREDIT] [-s STATE]"};
            } 
            int taskIndexNumber =
                Integer.parseInt(projectCommand.substring(COMMAND_EDIT_TASK.length()).trim().split(" ")[0]);
            if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                if (!projectCommand.contains("-")) {
                    return new String[] {"No flags are found! Available flags for use are '-t', '-p, '-d', '-c' and "
                            + "'-s' to indicate the new task details! Refer to the user guide for more help!"};
                }
                String updatedTaskDetails = projectCommand.substring(projectCommand.indexOf("-"));
                return projectToManage.editTask(taskIndexNumber, updatedTaskDetails);
            }
            return new String[] {"The task index entered is invalid."};

        } catch (NumberFormatException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectEditTask] "
                    + "Please enter a valid number for your task index.");
            return new String[] {"Please enter a valid number for your task index."};
        }
    }

    //@@author iamabhishek98
    /**
     * Deletes a task from the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectDeleteTask(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectDeleteTask] User input: '"
                + projectCommand + "'");
        if (projectCommand.length() <= COMMAND_DELETE_TASK.length()) {
            return new String[] {"No task number detected! Please enter the task index number."};
        }
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validTaskIndexes =
            parserHelper.parseTasksIndexes(projectCommand.substring(COMMAND_DELETE_TASK.length()),
                projectToManage.getNumOfTasks());
        ArrayList<String> outputMessages = new ArrayList<>(parserHelper.getErrorMessages());
        // Sort to ensure task indexes work in the correct way
        Collections.sort(validTaskIndexes);
        Collections.reverse(validTaskIndexes);
        for (Integer index: validTaskIndexes) {
            outputMessages.add("Removed task " + index + ": " + projectToManage.getTaskIndexName(index));
            projectToManage.removeTask(index);
        }
        if (!validTaskIndexes.isEmpty()) {
            outputMessages.add("Take note that index numbers of other tasks may have changed after deleting!");
        }
        return outputMessages.toArray(new String[0]);
    }

    /**
     * Updates the task requirements of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditTaskRequirements(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
                "[projectEditTaskRequirements] User input: '" + projectCommand + "'");
        if (projectCommand.length() <= COMMAND_EDIT_TASK_REQ.length()) {
            return new String[] {"Task index is missing! Please input index of task to be edited!"};
        }
        try {
            int taskIndexNumber =
                Integer.parseInt(projectCommand.substring(COMMAND_EDIT_TASK_REQ.length()).trim().split(" ")[0]);
            if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                if (!projectCommand.contains("-")) {
                    return new String[] {"No flags are found! Please use flags such as '-r' or '-rm' to indicate "
                            + "the new requirements to be added or removed! Refer to the user guide for more help!"};
                } else {
                    String updatedTaskRequirements = projectCommand.substring(projectCommand.indexOf("-"));
                    return projectToManage.editTaskRequirements(taskIndexNumber,updatedTaskRequirements);
                }
            }
            return new String[] {"The task index entered is invalid."};
        } catch (NumberFormatException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectEditTaskRequirements] "
                    + "Task index is missing! Please input a proper task index!");
            return new String[] {"Task index is invalid! Please input a proper task index!"};
        }
    }

    /**
     * Displays the tasks in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private String[] projectViewTaskRequirements(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
                "[projectViewTaskRequirements] User input: '" + projectCommand + "'");
        if (projectCommand.length() < COMMAND_VIEW_TASK_REQ.length()) {
            return new String[] {"Please indicate the index of the task to be viewed."};
        } else {
            try {
                int taskIndex = Integer.parseInt(projectCommand.substring(COMMAND_VIEW_TASK_REQ.length()));
                if (projectToManage.getNumOfTasks() >= taskIndex && taskIndex > 0) {
                    if (projectToManage.getTask(taskIndex).getNumOfTaskRequirements() == 0) {
                        return new String[] {"This task has no specific requirements."};
                    } else {
                        ArrayList<String> taskRequirements = projectToManage.getTask(taskIndex).getTaskRequirements();
                        return viewHelper.consolePrintTable(taskRequirements, DEFAULT_HORI_BORDER_LENGTH);
                    }
                }
                return new String[] {"The task index entered is invalid."};
            } catch (NumberFormatException e) {
                ArchDukeLogger.logError(ProjectInputController.class.getName(),
                        "[projectAssignTask] Input is either not a number or too large! "
                                + "Please input a proper task index!");
                return new String[] {"Input is either not a number or too large! Please input a proper task index!"};
            }
        }
    }

    //@@author iamabhishek98
    /**
     * Displays all the tasks in the given project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private String[] projectViewTasks(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectViewTasks] User input: '"
            + projectCommand + "'");
        try {
            if (("view tasks").equals(projectCommand)) {
                HashMap<String, ArrayList<String>> tasksAndAssignedMembers
                    = projectToManage.getTasksAndAssignedMembers();
                ArrayList<ArrayList<String>> allTaskDetailsForTable
                    = projectToManage.getTaskList().getAllTaskDetailsForTable(tasksAndAssignedMembers,
                    "/PRIORITY", projectToManage);
                ArchDukeLogger.logDebug(ProjectInputController.class.getName(), allTaskDetailsForTable.toString());
                if (allTaskDetailsForTable.size() == 1 && allTaskDetailsForTable.get(0).size() == 1) {
                    ArrayList<String> taskTable = new ArrayList<>();
                    taskTable.add("Tasks of " + projectToManage.getName() + ":");
                    taskTable.add(allTaskDetailsForTable.get(0).get(0));
                    return viewHelper.consolePrintTable(taskTable, DEFAULT_HORI_BORDER_LENGTH);
                }
                return viewHelper.consolePrintMultipleTables(allTaskDetailsForTable, DEFAULT_HORI_BORDER_LENGTH, 2,
                        "Tasks of " + projectToManage.getName() + ":");
            } else if (projectCommand.length() >= COMMAND_VIEW_TASKS.length()) {
                String sortCriteria = projectCommand.substring(COMMAND_VIEW_TASKS.length());
                HashMap<String, ArrayList<String>> tasksAndAssignedMembers
                    = projectToManage.getTasksAndAssignedMembers();
                ArrayList<ArrayList<String>> allTaskDetailsForTable =
                    projectToManage.getTaskList().getAllTaskDetailsForTable(tasksAndAssignedMembers, sortCriteria,
                        projectToManage);
                ArchDukeLogger.logDebug(ProjectInputController.class.getName(), allTaskDetailsForTable.toString());
                if (allTaskDetailsForTable.size() == 0) {
                    ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectAssignTask] "
                            + "Currently there are no tasks with the specified attribute.");
                    return (new String[] {"Currently there are no tasks with the specified attribute."});
                }
                if (allTaskDetailsForTable.size() == 1 && allTaskDetailsForTable.get(0).size() == 1) {
                    ArrayList<String> taskTable = new ArrayList<>();
                    taskTable.add("Tasks of " + projectToManage.getName() + ":");
                    taskTable.add(allTaskDetailsForTable.get(0).get(0));
                    return viewHelper.consolePrintTable(taskTable, DEFAULT_HORI_BORDER_LENGTH);
                }
                return viewHelper.consolePrintMultipleTables(allTaskDetailsForTable, DEFAULT_HORI_BORDER_LENGTH, 2,
                        "Tasks of " + projectToManage.getName() + ":");
            }
        } catch (IndexOutOfBoundsException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectAssignTask] "
                + "Currently there are no tasks with the specified attribute.");
            return (new String[] {"Currently there are no tasks with the specified attribute."});
        }
        return null;
    }


    //@@author sinteary
    /**
     * Manages the assignment to and removal of tasks from members.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAssignTask(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectAssignTask] User input: '"
                + projectCommand + "'");
        AssignmentController assignmentController = new AssignmentController(projectToManage);
        assignmentController.assignAndUnassign(projectCommand);
        ArrayList<String> errorMessages = assignmentController.getErrorMessages();
        ArrayList<ArrayList<String>> successMessages = assignmentController.getSuccessMessages();
        if (!errorMessages.isEmpty()) {
            errorMessages.add(0, "Errors...");
            successMessages.add(errorMessages);
        }
        if (successMessages.isEmpty()) {
            return new String[]{"No valid assignment input detected! Please refer to the user guide for help."};
        }
        return viewHelper.consolePrintMultipleTables(successMessages,
                DEFAULT_HORI_BORDER_LENGTH, 1, "Results from task assignments:");
    }

    /**
     * Returns a list of assignments according to specifications of user.
     * @param projectToManage The project to manage.
     * @param projectCommand The full command by the user.
     */
    public String[] projectViewAssignments(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
                "[projectViewAssignments] User input: '" + projectCommand + "'");
        AssignmentViewHelper assignmentViewHelper = new AssignmentViewHelper();
        return assignmentViewHelper.viewAssignments(projectCommand, projectToManage);
    }
    //@@author

    /**
     * Exits the current project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable specifying the exit status.
     */
    private String[] projectExit(Project projectToManage) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectExit]");
        return new String[] {"Exited project: " + projectToManage.getName()};
    }

    public boolean getIsManagingAProject() {
        return isManagingAProject;
    }

    private String[] end() {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[end]");
        return new String[] {"Bye. Hope to see you again soon!"};
    }


    /**
     * Add reminder to the default list.
     * @param projectToManage The project to manage.
     * @param projectCommand The user input.
     */
    public String [] projectAddReminder(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
                "[projectAddReminder] User input: '" + projectCommand + "'");
        try {
            ReminderFactory reminderFactory = new ReminderFactory();
            IReminder newReminder = reminderFactory.createReminder(projectCommand.substring(13));
            if (newReminder.getReminderName() != null) {
                projectToManage.addReminderToList((Reminder) newReminder);
                return new String[] {"Added new reminder to the Reminder List in the project."};
            }
            return new String[] {"Failed to create new task. Please ensure all "
                    + "necessary parameters are given"};

        } catch (NumberFormatException | ParseException e) {
            ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectAddReminder] "
                    + "Please enter your reminder date format correctly.");
            return new String[] {"Please enter your reminder date format correctly."};
        }
    }

    /**
     * View reminder to the default list list of tasks and the members assigned to them.
     * @param projectToManage The project to manage.
     */
    public String [] projectViewReminder(Project projectToManage) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectViewReminder]");
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        ArrayList<String> allTaskDetailsForTable = new ArrayList<>();

        int index = 1;
        allTaskDetailsForTable.add(0, "Reminder of " + projectToManage.getName() + ":");
        if (projectToManage.getReminderListSize() == 0) {
            allTaskDetailsForTable.add(" - There are currently no reminders! -");
        } else {
            for (Reminder reminder: projectToManage.getReminderList()) {
                allTaskDetailsForTable.add(index + ". " + reminder.getStatus() + " " + reminder.getReminderName());
                allTaskDetailsForTable.add("   - Remarks: " + reminder.getReminderRemarks());
                allTaskDetailsForTable.add("   - " + dateTimeHelper.formatDateForDisplay(reminder.getReminderDate())
                        + dateTimeHelper.getDifferenceDays(reminder.getReminderDate()));
                allTaskDetailsForTable.add(" ");
                index++;
            }
            allTaskDetailsForTable.remove(allTaskDetailsForTable.size() - 1);
        }
        return viewHelper.consolePrintTable(allTaskDetailsForTable, DEFAULT_HORI_BORDER_LENGTH);
    }

    /**
     * Delete reminder from the list.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     * @return the output for the user to consume.
     */
    public String[] projectDeleteReminder(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectDeleteReminder] User input: '"
                + projectCommand + "'");
        ParserHelper parserHelper = new ParserHelper();
        int index = parserHelper.parseDeleteReminder(projectCommand);
        if (index == 0) {
            ArrayList<String> outputMessages = new ArrayList<>(parserHelper.getErrorMessages());
            return outputMessages.toArray(new String[0]);
        } else if (index > projectToManage.getReminderListSize()) {
            return new String[] {"No reminder index number found in the list! "
                    + "Please enter the correct reminder index number."};
        } else {
            String removedReminder = projectToManage.getReminder(index).getReminderName();
            projectToManage.removeReminder(index);
            return new String[] {removedReminder + " has been remove from the reminder list in the project."};
        }
    }

    /**
     * Edit reminder from the list.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     * @return the output for the user to consume.
     */
    public String[] projectEditReminder(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(), "[projectEditReminder] User input: '"
                + projectCommand + "'");
        ParserHelper parserHelper = new ParserHelper();
        int index = parserHelper.parseEditReminder(projectCommand);
        if (index == 0) {
            ArrayList<String> outputMessages = new ArrayList<>(parserHelper.getErrorMessages());
            return outputMessages.toArray(new String[0]);
        } else if (index > projectToManage.getReminderListSize()) {
            return new String[] {"No reminder index number found in the list! "
                    + "Please enter the correct reminder index number."};
        } else {

            try {
                ArrayList<String> newReminderDetails = parserHelper.parseReminderDetails(projectCommand);
                DateTimeHelper dateTimeHelper = new DateTimeHelper();

                if (!newReminderDetails.get(0).matches("--")) {
                    projectToManage.getReminder(index).setReminderName(newReminderDetails.get(0));
                }
                if (!newReminderDetails.get(1).matches("--")) {
                    projectToManage.getReminder(index).setReminderRemarks(newReminderDetails.get(1));
                }
                if (newReminderDetails.get(2) != null) {
                    projectToManage.getReminder(index)
                            .setReminderDate(dateTimeHelper.formatDate(newReminderDetails.get(2)));
                }
                return new String[] {"Your reminder have been updated."};
            } catch (NumberFormatException | ParseException e) {
                ArchDukeLogger.logError(ProjectInputController.class.getName(), "[projectEditReminder] "
                        + "Please enter your reminder date format correctly.");
                return new String[] {"Please enter your reminder date format correctly."};
            }
        }
    }

    /**
     * Set the status of the reminder.
     * @param projectToManage The project to manage.
     * @param projectCommand The user input.
     */
    public String[] projectSetReminderStatus(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
                "[projectSetReminderStatus] User input: '" + projectCommand + "'");
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<String> checkReminderDetails = parserHelper.parseCheckReminder(projectCommand);
        if (checkReminderDetails == null) {
            ArrayList<String> outputMessages = new ArrayList<>(parserHelper.getErrorMessages());
            return outputMessages.toArray(new String[0]);
        } else {
            boolean status = Boolean.parseBoolean(checkReminderDetails.get(0));
            int index = Integer.parseInt(checkReminderDetails.get(1));
            projectToManage.markReminder(status,index);
            return new String[] {projectToManage.getReminder(index).getReminderName() + " have been marked "
                    + projectToManage.getReminder(index).getStatus()};
        }
    }
}
