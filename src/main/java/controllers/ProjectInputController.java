package controllers;

import java.text.ParseException;
import java.util.Scanner;
import models.data.IProject;
import repositories.ProjectRepository;
import util.factories.MemberFactoryUtil;
import util.factories.TaskFactory;
import views.CLIView;

public class ProjectInputController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private CLIView consoleView;

    /**
     * Class responsible for handling user input when user chooses to manage a project.
     * @param consoleView The main UI of ArchDuke.
     * @param projectRepository The list of all projects.
     */
    public ProjectInputController(CLIView consoleView, ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.consoleView = consoleView;
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    public void manageProject(String input) {
        int projectNumber = Integer.parseInt(input);
        IProject projectToManage = projectRepository.getProject(projectNumber);
        this.consoleView.consolePrint("Now managing: " + projectToManage.getDescription());
        boolean continueManaging = true;
        while (continueManaging) {
            if (manageProjectInput.hasNextLine()) {
                String projectCommand = manageProjectInput.nextLine();
                if (projectCommand.length() == 4 && ("exit").equals(projectCommand.substring(0, 4))) {
                    continueManaging = false;
                    consoleView.exitProject(projectToManage.getDescription());
                } else if (projectCommand.length() >= 11 && ("add member ").equals(projectCommand.substring(0, 11))) {
                    String memberDetails = projectCommand.substring(11);
                    MemberFactoryUtil memberFactory = new MemberFactoryUtil();
                    if (memberFactory.memberIsCreated(memberDetails, projectToManage.getNumOfMembers())) {
                        consoleView.addMember(projectToManage, memberFactory.getNewMember());
                    } else {
                        consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                                + "at least the name of the new member.");
                    }
                } else if (projectCommand.length() >= 11 && ("edit member ").equals(projectCommand.substring(0, 12))) {
                    int memberIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
                    if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
                        String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("n/"));
                        consoleView.editMember(projectToManage, memberIndexNumber, updatedMemberDetails);
                    } else {
                        consoleView.consolePrint("The member index entered is invalid.");
                    }
                } else if (projectCommand.length() >= 13 && ("delete member ").equals(projectCommand.substring(0,14))) {
                    int memberIndexNumber = Integer.parseInt(projectCommand.substring(14).split(" ")[0]);
                    if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
                        consoleView.removeMember(projectToManage, memberIndexNumber);
                    } else {
                        consoleView.consolePrint("The member index entered is invalid.");
                    }
                } else if (projectCommand.length() == 12 && ("view members").equals(projectCommand)) {
                    consoleView.viewAllMembers(projectToManage);
                } else if (projectCommand.length() == 8 && ("add task").equals(projectCommand)) {
                    try {
                        consoleView.consolePrint("Enter your task: t/TaskName p/TaskPriorityValue"
                                                + " [d/TaskDueDate] c/TaskCredit [s/TaskState]");
                        String taskDetails = manageProjectInput.nextLine();
                        TaskFactory taskFactory = new TaskFactory();
                        consoleView.addTask(projectToManage, taskFactory.createTask(taskDetails));
                    } catch (NumberFormatException | ParseException e) {
                        consoleView.consolePrint("Please enter your task format correctly");
                    }
                } else if (projectCommand.length() == 10 && ("view tasks").equals(projectCommand)) {
                    consoleView.viewAllTasks(projectToManage);
                } else if (projectCommand.length() == 10 && ("edit task ").equals(projectCommand)) {
                    String temp2 = "";
                    System.out.println(temp2);
                    /*
                        Empty method
                    */
                } else if (projectCommand.length() == 12 && ("delete task ").equals(projectCommand)) {
                    String temp3 = "";
                    System.out.println(temp3);
                    /*
                        Empty method
                    */
                } else if (projectCommand.length() >= 12 && ("assign task ").equals(projectCommand.substring(0,12))) {
                    AssignmentController.manageAssignment(projectToManage,
                        projectCommand.substring(12).split(" "), consoleView);
                } else {
                    consoleView.consolePrint("Invalid command. Try again!");
                }
            } else {
                consoleView.consolePrint("Please enter a command.");
            }
        }

    }

}
