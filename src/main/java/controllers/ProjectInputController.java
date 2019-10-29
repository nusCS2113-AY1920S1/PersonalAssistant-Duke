package controllers;

import models.project.IProject;
import models.project.Project;
import models.member.IMember;
import models.member.Member;
import models.task.ITask;
import models.task.Task;
import repositories.ProjectRepository;
import util.AssignmentViewHelper;
import util.ParserHelper;
import util.ViewHelper;
import util.factories.MemberFactory;
import util.factories.TaskFactory;
import util.log.DukeLogger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProjectInputController implements IController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private MemberFactory memberFactory;
    private boolean isManagingAProject;
    private ViewHelper viewHelper;

    /**
     * Constructor for ProjectInputController takes in a View model and a ProjectRepository.
     * ProjectInputController is responsible for handling user input when user chooses to manage a project.
     * @param projectRepository The object holding all projects.
     */
    public ProjectInputController(ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.memberFactory = new MemberFactory();
        this.isManagingAProject = true;
        this.viewHelper = new ViewHelper();
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    @Override
    public String[] onCommandReceived(String input) {
        //DukeLogger.logInfo(ProjectInputController.class, "Managing project: " + input);
        int projectNumber;
        try {
            projectNumber = Integer.parseInt(input);
        } catch (NumberFormatException err) {
            isManagingAProject = false;
            return new String[] {"Input is not a number! Please input a proper project index!"};
        }
        Project projectToManage = projectRepository.getItem(projectNumber);
        isManagingAProject = true;
        return manageProject(projectToManage);
    }

    /**
     * Manages the project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable giving status of whether the exit command is entered.
     */
    private String[] manageProject(Project projectToManage) {
        if (manageProjectInput.hasNextLine()) {
            String projectFullCommand = manageProjectInput.nextLine();
            DukeLogger.logInfo(ProjectInputController.class, "Managing:"
                    + projectToManage.getDescription() + ",input:'"
                    + projectFullCommand + "'");
            if (projectFullCommand.matches("exit")) {
                isManagingAProject = false;
                return projectExit(projectToManage);
            } else if (projectFullCommand.matches("add member.*")) {
                return projectAddMember(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("edit member.*")) {
                return projectEditMember(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("delete member.*")) {
                return projectDeleteMember(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("view members.*")) {
                return projectViewMembers(projectToManage);
            } else if (projectFullCommand.matches("role.*")) {
                return projectRoleMembers(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("view credits.*")) {
                return projectViewCredits(projectToManage);
            } else if (projectFullCommand.matches("add task.*")) {
                return projectAddTask(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("view tasks.*")) {
                return projectViewTasks(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("view assignments.*")) {
                return projectViewAssignments(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("view task requirements.*")) { // need to refactor this
                return projectViewTaskRequirements(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("edit task requirements.*")) {
                return projectEditTaskRequirements(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("edit task.*")) {
                return projectEditTask(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("delete task.*")) {
                return projectDeleteTask(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("assign task.*")) {
                return projectAssignTask(projectToManage, projectFullCommand);
            } else if (projectFullCommand.matches("bye")) {
                return end();
            } else {
                return new String[] {"Invalid command. Try again!"};
            }
        }
        return new String[] {"Please enter a command."};
    }

    /**
     * Adds roles to Members in a Project.
     * @param projectToManage : The project specified by the user.
     * @param projectFullCommand : User input.
     */
    public String[] projectRoleMembers(Project projectToManage, String projectFullCommand) {
        String parsedCommands = projectFullCommand.substring(5);
        String[] commandOptions = parsedCommands.split(" -n ");
        if (commandOptions.length != 2) {
            return new String[] {"Wrong command format! Please enter role INDEX -n ROLE_NAME"};
        }
        int memberIndex = Integer.parseInt(commandOptions[0]);
        IMember selectedMember = projectToManage.getMembers().getMember(memberIndex);
        selectedMember.setRole(commandOptions[1]);
        return new String[] {"Successfully changed the role of " + selectedMember.getName() + " to "
                                + selectedMember.getRole() + "."};
    }

    /**
     * Adds a member to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAddMember(Project projectToManage, String projectCommand) {
        if (projectCommand.length() < 11) {
            return new String[] {"Add member command minimum usage must be \"add member -n NAME\"!",
                                "Please refer to user guide for additional details."};
        }
        String memberDetails = projectCommand.substring(11);
        int numberOfCurrentMembers = projectToManage.getNumOfMembers();
        memberDetails = memberDetails + " -x " + numberOfCurrentMembers;
        IMember newMember = memberFactory.create(memberDetails);
        if (newMember.getName() != null) {
            projectToManage.addMember((Member) newMember);
            return new String[] {"Added new member to: " + projectToManage.getDescription(), ""
                    + "Member details " + newMember.getDetails()};
        } else {
            return new String[] {"Name cannot be empty! Please follow the add command format in user guide!",
                    "\"add member -n NAME\" is the minimum requirement for add member command"};
        }
    }

    /**
     * Updates the details of a given member in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditMember(Project projectToManage, String projectCommand) {
        try {
            int memberIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
            if (projectToManage.getNumOfMembers() >= memberIndexNumber && memberIndexNumber > 0) {
                String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("-"));
                projectToManage.editMember(memberIndexNumber,updatedMemberDetails);
                return new String[] { "Updated member details with the index number " + memberIndexNumber};
            } else {
                return new String[] {"The member index entered is invalid."};
            }
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            return new String[] {"Please enter the updated member details format correctly."};
        }
    }

    /**
     * Deletes a member from the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectDeleteMember(Project projectToManage, String projectCommand) {
        int memberIndexNumber = Integer.parseInt(projectCommand.substring(14).split(" ")[0]);
        if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
            Member memberToRemove = projectToManage.getMembers().getMember(memberIndexNumber);
            projectToManage.removeMember(memberToRemove);
            return new String[] {"Removed member with the index number " + memberIndexNumber};
        } else {
            return new String[] {"The member index entered is invalid."};
        }
    }

    /**
     * Displays all the members in the current project.
     * Can be updated later on to include more information (tasks etc).
     * @param projectToManage The project specified by the user.
     */
    public String[] projectViewMembers(Project projectToManage) {
        ArrayList<String> allMemberDetailsForTable = projectToManage.getMembers().getAllMemberDetailsForTable();
        String header = "Members of " + projectToManage.getDescription() + ":";
        allMemberDetailsForTable.add(0, header);
        DukeLogger.logDebug(ProjectInputController.class, allMemberDetailsForTable.toString());
        ArrayList<ArrayList<String>> tablesToPrint = new ArrayList<>();
        tablesToPrint.add(allMemberDetailsForTable);
        return viewHelper.consolePrintTable(tablesToPrint);
    }

    /**
     * Displays the members’ credits, their index number, name, and name of tasks completed.
     * @param projectToManage The project specified by the user.
     */
    public String[] projectViewCredits(IProject projectToManage) {
        ArrayList<String> allCredits = projectToManage.getCredits();
        DukeLogger.logDebug(ProjectInputController.class, allCredits.toString());
        if (allCredits.isEmpty()) {
            allCredits.add(0, "There are no members in this project.");
        } else {
            allCredits.add(0, "Here are all the member credits: ");
        }
        return allCredits.toArray(new String[0]);
    }


    /**
     * Adds a task to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAddTask(Project projectToManage, String projectCommand) {
        try {
            TaskFactory taskFactory = new TaskFactory();
            ITask newTask = taskFactory.createTask(projectCommand.substring(9));
            if (newTask.getDetails() != null) {
                projectToManage.addTask((Task) newTask);
                return new String[] {"Added new task to the list."};
            }
            return new String[] {"Failed to create new task. Please ensure all "
                        + "necessary parameters are given"};

        } catch (NumberFormatException | ParseException e) {
            return new String[] {"Please enter your task format correctly."};
        }
    }

    /**
     * Updates the task details of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditTask(Project projectToManage, String projectCommand) {
        try {
            int taskIndexNumber = Integer.parseInt(projectCommand.substring(10).split(" ")[0]);
            String updatedTaskDetails = projectCommand.substring(projectCommand.indexOf("-"));

            if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                projectToManage.editTask(taskIndexNumber, updatedTaskDetails);
                return new String[] { "The task has been updated!" };
            }
            return new String[] {"The task index entered is invalid."};

        } catch (NumberFormatException e) {
            return new String[] {"Please enter your task format correctly."};
        }
    }

    /**
     * Deletes a task from the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectDeleteTask(Project projectToManage, String projectCommand) {
        int taskIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
        if (projectToManage.getNumOfTasks() >= taskIndexNumber) {
            String removedTaskString = "Removed " + projectToManage.getTask(taskIndexNumber).getTaskName();
            projectToManage.removeTask(taskIndexNumber);
            return new String[] {removedTaskString};
        } else {
            return new String[] {"The task index entered is invalid."};
        }
    }

    /**
     * Updates the task requirements of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectEditTaskRequirements(Project projectToManage, String projectCommand) {
        try {
            int taskIndexNumber = Integer.parseInt(projectCommand.substring(23).split(" ")[0]);
            String updatedTaskRequirements = projectCommand.substring(projectCommand.indexOf("-"));
            if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                projectToManage.editTaskRequirements(taskIndexNumber,updatedTaskRequirements);
                return new String[] {"The requirements of your specified task has been updated!"};
            }
            return new String[] {"The task index entered is invalid."};
        } catch (NumberFormatException e) {
            return new String[] {"Task index is missing! Please input a proper task index!"};
        }
    }

    /**
     * Displays the tasks in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectViewTaskRequirements(Project projectToManage, String projectCommand) {
        if (projectCommand.length() < 23) {
            return new String[] {"Please indicate the index of the task to be viewed."};
        } else {
            try {
                int taskIndex = Integer.parseInt(projectCommand.substring(23));
                if (projectToManage.getNumOfTasks() >= taskIndex && taskIndex > 0) {
                    if (projectToManage.getTask(taskIndex).getNumOfTaskRequirements() == 0) {
                        return new String[] {"This task has no specific requirements."};
                    } else {
                        ArrayList<String> taskRequirements = projectToManage.getTask(taskIndex).getTaskRequirements();
                        return taskRequirements.toArray(new String[0]);
                    }
                }
                return new String[] {"The task index entered is invalid."};
            } catch (NumberFormatException e) {
                return new String[] {"Input is not a number! Please input a proper task index!"};
            }
        }
    }

    /**
     * Manages the assignment to and removal of tasks from members.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectAssignTask(Project projectToManage, String projectCommand) {
        AssignmentController assignmentController = new AssignmentController(projectToManage);
        assignmentController.assignAndUnassign(projectCommand.substring(12));
        ArrayList<String> errorMessages = assignmentController.getErrorMessages();
        ArrayList<String> successMessages = assignmentController.getSuccessMessages();
        errorMessages.addAll(successMessages);
        return errorMessages.toArray(new String[0]);
    }

    /**
     * Displays list of assignments according to specifications of user.
     * @param projectToManage The project to manage.
     * @param projectFullCommand The full command by the user.
     */
    private String[] projectViewAssignments(Project projectToManage, String projectFullCommand) {
        String input = projectFullCommand.substring(18);
        if (input.charAt(0) == 'm') {
            return projectViewMembersAssignments(projectToManage, projectFullCommand.substring(20));
        } else if (input.charAt(0) == 't') {
            return projectViewTasksAssignments(projectToManage, projectFullCommand.substring(20));
        }
        return null;
    }

    /**
     * Displays all the tasks in the given project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public String[] projectViewTasks(Project projectToManage, String projectCommand) {
        if (("view tasks").equals(projectCommand)) {
            HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers = projectToManage.getTasksAndAssignedMembers();
            ArrayList<ArrayList<String>> tableToPrint = new ArrayList<>();
            ArrayList<String> allTaskDetailsForTable
                    = projectToManage.getTasks().getAllTaskDetailsForTable(tasksAndAssignedMembers);
            allTaskDetailsForTable.add(0, "Tasks of " + projectToManage.getDescription() + ":");
            DukeLogger.logDebug(ProjectInputController.class, allTaskDetailsForTable.toString());
            tableToPrint.add(allTaskDetailsForTable);
            return viewHelper.consolePrintTable(tableToPrint);
        } else if (projectCommand.length() >= 11) {
            String sortCriteria = projectCommand.substring(11);
            HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers = projectToManage.getTasksAndAssignedMembers();
            ArrayList<String> allTaskDetails =
                    projectToManage.getTasks().getAllSortedTaskDetails(tasksAndAssignedMembers,sortCriteria);

            DukeLogger.logDebug(ProjectInputController.class, allTaskDetails.toString());
            return allTaskDetails.toArray(new String[0]);
        }
        return null;
    }

    /**
     * Prints a list of members' individual list of tasks.
     * @param projectToManage the project being managed.
     * @param projectCommand The command by the user containing index numbers of the members to view.
     */
    public String[] projectViewMembersAssignments(Project projectToManage, String projectCommand) {
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validMembers = parserHelper.parseMembersIndexes(projectCommand,
            projectToManage.getNumOfMembers());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            return parserHelper.getErrorMessages().toArray(new String[0]);
        }
        return AssignmentViewHelper.getMemberOutput(validMembers,
            projectToManage).toArray(new String[0]);
    }

    /**
     * Prints a list of tasks and the members assigned to them.
     * @param projectToManage The project to manage.
     * @param projectCommand The user input.
     */
    private String[] projectViewTasksAssignments(Project projectToManage, String projectCommand) {
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validTasks = parserHelper.parseTasksIndexes(projectCommand,
            projectToManage.getNumOfTasks());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            return parserHelper.getErrorMessages().toArray(new String[0]);
        }
        return AssignmentViewHelper.getTaskOutput(validTasks,
            projectToManage).toArray(new String[0]);
    }

    /**
     * Exits the current project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable specifying the exit status.
     */
    public String[] projectExit(Project projectToManage) {
        return new String[] {"Exited project: " + projectToManage.getDescription()};
    }

    public boolean getIsManagingAProject() {
        return isManagingAProject;
    }

    public String[] end() {
        return new String[] {"Bye. Hope to see you again soon!"};
    }
}
