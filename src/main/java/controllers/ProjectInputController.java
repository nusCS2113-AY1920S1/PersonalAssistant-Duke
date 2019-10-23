package controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import models.data.Project;
import models.member.IMember;
import models.member.Member;
import models.task.ITask;
import models.task.Task;
import repositories.ProjectRepository;
import util.ParserHelper;
import util.factories.MemberFactory;
import util.factories.TaskFactory;
import util.log.DukeLogger;
import views.CLIView;

public class ProjectInputController implements IController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private CLIView consoleView;
    private MemberFactory memberFactory;

    /**
     * Constructor for ProjectInputController takes in a View model and a ProjectRepository.
     * ProjectInputController is responsible for handling user input when user chooses to manage a project.
     * @param consoleView The main UI of ArchDuke.
     * @param projectRepository The object holding all projects.
     */
    public ProjectInputController(CLIView consoleView, ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.consoleView = consoleView;
        this.memberFactory = new MemberFactory();
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    public void onCommandReceived(String input) {
        DukeLogger.logInfo(ProjectInputController.class, "Managing project: " + input);
        int projectNumber = Integer.parseInt(input);
        Project projectToManage = projectRepository.getItem(projectNumber);
        this.consoleView.consolePrint("Now managing: " + projectToManage.getDescription());
        boolean isManagingAProject = true;
        while (isManagingAProject) {
            isManagingAProject = manageProject(projectToManage);
        }
    }

    /**
     * Manages the project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable giving status of whether the exit command is entered.
     */
    public boolean manageProject(Project projectToManage) {
        boolean isManagingAProject = true;
        if (manageProjectInput.hasNextLine()) {
            String projectFullCommand = manageProjectInput.nextLine();
            DukeLogger.logInfo(ProjectInputController.class, "Managing:"
                    + projectToManage.getDescription() + ",input:'"
                    + projectFullCommand + "'");
            String[] projectCommand = projectFullCommand.split("-",1);
            switch (projectCommand[0].trim()) {
            case "exit":
                isManagingAProject = projectExit(projectToManage);
                break;
            case "add member":
                projectAddMember(projectToManage, projectFullCommand);
                break;
            case "edit member":
                projectEditMember(projectToManage, projectFullCommand);
                break;
            case "delete member":
                projectDeleteMember(projectToManage, projectFullCommand);
                break;
            case "view members":
                projectViewMembers(projectToManage);
                break;
            case "view credits":
                projectViewCredits();
                break;
            case "add task":
                projectAddTask(projectToManage, projectFullCommand);
                break;
            case "view tasks":
                projectViewTasks(projectToManage, projectFullCommand);
                break;
            case "view assigned tasks":
                projectViewAssigned(projectToManage, projectFullCommand);
                break;
            case "view task requirements i/": // need to refactor this command
                projectViewTaskRequirements(projectToManage, projectFullCommand);
                break;
            case "edit task requirements":
                projectEditTaskRequirements(projectToManage, projectFullCommand);
                break;
            case "edit task":
                projectEditTask(projectToManage, projectFullCommand);
                break;
            case "delete task":
                projectDeleteTask(projectToManage, projectFullCommand);
                break;
            case "assign task":
                projectAssignTask(projectToManage, projectFullCommand);
                break;
            case "bye":
                consoleView.end();
                break;
            default:
                consoleView.consolePrint("Invalid command. Try again!");
                break;
            }
        } else {
            consoleView.consolePrint("Please enter a command.");
        }
        return isManagingAProject;
    }

    private void projectViewAssigned(Project projectToManage, String projectFullCommand) {
        
    }


    /**
     * Adds a member to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectAddMember(Project projectToManage, String projectCommand) {
        String memberDetails = projectCommand.substring(11);
        int numberOfCurrentMembers = projectToManage.getNumOfMembers();
        memberDetails = memberDetails + " -x " + numberOfCurrentMembers;
        IMember newMember = memberFactory.create(memberDetails);
        if (newMember.getName() != null) {
            projectToManage.addMember((Member) newMember);
            consoleView.addMember(projectToManage, newMember.getDetails());
        } else {
            consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                    + "at least the name of the new member.");
        }
    }

    /**
     * Updates the details of a given member in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectEditMember(Project projectToManage, String projectCommand) {
        try {
            int memberIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
            if (projectToManage.getNumOfMembers() >= memberIndexNumber && memberIndexNumber > 0) {
                String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("-"));
                consoleView.editMember(projectToManage, memberIndexNumber, updatedMemberDetails);
            } else {
                consoleView.consolePrint("The member index entered is invalid.");
            }
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            consoleView.consolePrint("Please enter the updated member details format correctly.");
        }
    }

    /**
     * Deletes a member from the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectDeleteMember(Project projectToManage, String projectCommand) {
        int memberIndexNumber = Integer.parseInt(projectCommand.substring(14).split(" ")[0]);
        if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
            Member memberToRemove = projectToManage.getMembers().getMember(memberIndexNumber);
            projectToManage.removeMember(memberToRemove);
            consoleView.consolePrint("Removed member with the index number " + memberIndexNumber);
        } else {
            consoleView.consolePrint("The member index entered is invalid.");
        }
    }

    /**
     * Displays all the members in the current project.
     * @param projectToManage The project specified by the user.
     */
    public void projectViewMembers(Project projectToManage) {
        consoleView.viewAllMembers(projectToManage);
    }

    /**
     * Displays the members’ credits, their index number, name, and name of tasks completed.
     */
    public void projectViewCredits() {
        // TODO view all credits.
        consoleView.consolePrint("Not implemented yet");
    }

    /**
     * Adds a task to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectAddTask(Project projectToManage, String projectCommand) {
        try {
            TaskFactory taskFactory = new TaskFactory();
            ITask newTask = taskFactory.createTask(projectCommand.substring(9));
            if (newTask.getDetails() != null) {
                consoleView.addTask(projectToManage, (Task) newTask);
            } else {
                consoleView.consolePrint("Failed to create new task. Please ensure all "
                        + "necessary parameters are given");
            }
        } catch (NumberFormatException | ParseException e) {
            consoleView.consolePrint("Please enter your task format correctly.");
        }
    }

    /**
     * Updates the task details of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectEditTask(Project projectToManage, String projectCommand) {
        int taskIndexNumber = Integer.parseInt(projectCommand.substring(10).split(" ")[0]);
        String updatedTaskDetails = projectCommand.substring(projectCommand.indexOf("-"));

        if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
            consoleView.editTask(projectToManage, updatedTaskDetails, taskIndexNumber);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Deletes a task from the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectDeleteTask(Project projectToManage, String projectCommand) {
        int taskIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
        if (projectToManage.getNumOfTasks() >= taskIndexNumber) {
            consoleView.removeTask(projectToManage, taskIndexNumber);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Updates the task requirements of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectEditTaskRequirements(Project projectToManage, String projectCommand) {
        String[] updatedTaskRequirements = projectCommand.split(" [ir]m?/");
        int taskIndexNumber = Integer.parseInt(updatedTaskRequirements[1]);
        boolean haveRemove = false;
        if (projectCommand.contains(" rm/")) {
            haveRemove = true;
        }
        if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
            consoleView.editTaskRequirements(projectToManage, taskIndexNumber, updatedTaskRequirements,
                    haveRemove);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Displays the tasks in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectViewTaskRequirements(Project projectToManage, String projectCommand) {
        int taskIndex = Integer.parseInt(projectCommand.substring(25));
        if (projectToManage.getNumOfTasks() >= taskIndex && taskIndex > 0) {
            if (projectToManage.getTask(taskIndex).getNumOfTaskRequirements() == 0) {
                consoleView.consolePrint("This task has no specific requirements.");
            } else {
                consoleView.viewTaskRequirements(projectToManage, taskIndex);
            }
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Manages the assignment to and removal of tasks from members.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectAssignTask(Project projectToManage, String projectCommand) {
        AssignmentController assignmentController = new AssignmentController(projectToManage);
        assignmentController.assignAndUnassign(projectCommand.substring(12));
        consoleView.consolePrint(assignmentController.getErrorMessages().toArray(new String[0]));
        consoleView.consolePrint(assignmentController.getSuccessMessages().toArray(new String[0]));
    }

    /**
     * Displays the assigned tasks in the current project.
     * @param assignedTaskList The list containing the assignment of the tasks.
     */
    public void projectViewAssignedTasks(ArrayList<String> assignedTaskList) {
        consoleView.consolePrint(assignedTaskList.toArray(new String[0]));
    }

    /**
     * Displays all the tasks in the given project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    public void projectViewTasks(Project projectToManage, String projectCommand) {
        if (("view tasks").equals(projectCommand)) {
            consoleView.viewAllTasks(projectToManage);
        } else if (projectCommand.length() >= 11) {
            String sortCriteria = projectCommand.substring(11);
            consoleView.viewSortedTasks(projectToManage, sortCriteria);
        }
    }

    /**
     * Prints a list of members' individual list of tasks.
     * @param projectToManage the project being managed.
     * @param projectCommand The command by the user containing index numbers of the members to view.
     */
    public void projectViewMembersAssignments(Project projectToManage, String projectCommand) {
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validMembers = parserHelper.parseMembersIndexes(projectCommand,
            projectToManage.getNumOfMembers());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            consoleView.consolePrint(parserHelper.getErrorMessages().toArray(new String[0]));
        }
        consoleView.consolePrint(AssignmentViewHelper.getMemberOutput(validMembers,
            projectToManage).toArray(new String[0]));
    }

    /**
     * Prints a list of tasks and the members assigned to them.
     * @param projectToManage The project to manage.
     * @param projectCommand The user input.
     */
    private void projectViewTasksAssignments(Project projectToManage, String projectCommand) {
        ParserHelper parserHelper = new ParserHelper();
        ArrayList<Integer> validTasks = parserHelper.parseTasksIndexes(projectCommand,
            projectToManage.getNumOfTasks());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            consoleView.consolePrint(parserHelper.getErrorMessages().toArray(new String[0]));
        }
        consoleView.consolePrint(AssignmentViewHelper.getTaskOutput(validTasks,
            projectToManage).toArray(new String[0]));
    }


    /**
     * Exits the current project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable specifying the exit status.
     */
    public boolean projectExit(Project projectToManage) {
        boolean isManagingAProject;
        isManagingAProject = false;
        consoleView.exitProject(projectToManage.getDescription());
        return isManagingAProject;
    }

}
