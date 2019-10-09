package controllers;

import models.data.IProject;
import repositories.ProjectRepository;
import views.CLIView;

import java.text.ParseException;
import java.util.Scanner;

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
                switch (projectCommand) {
                case "exit":
                    continueManaging = false;
                    consoleView.exitProject(projectToManage.getDescription());
                    break;
                case "add member":
                    consoleView.consolePrint("Enter member details: n/NAME p/PHONE e/EMAIL");
                    String memberDetails = manageProjectInput.nextLine();
                    MemberFactoryUtil memberFactory = new MemberFactoryUtil();
                    if (memberFactory.memberIsCreated(memberDetails, projectToManage.getNumOfMembers())) {
                        consoleView.addMember(projectToManage, memberFactory.getNewMember());
                    } else {
                        consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                            + "at least the name of the new member.");
                    }
                    break;
                case "edit member":
                    consoleView.consolePrint("Enter member index to be edited");
                    int memberIndexNumber = Integer.parseInt(manageProjectInput.nextLine());
                    if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
                        consoleView.consolePrint("Enter the updated member details: n/NAME p/PHONE e/EMAIL");
                        String updatedMemberDetails = manageProjectInput.nextLine();
                        consoleView.editMember(projectToManage,memberIndexNumber,updatedMemberDetails);
                    } else {
                        consoleView.consolePrint("The member index entered is invalid.");
                    }
                    break;
                case "view members":
                    consoleView.viewAllMembers(projectToManage);
                    break;
                case "add task":
                    try {
                        consoleView.consolePrint("Enter your task: TaskName TaskPriorityValue");
                        String taskDetails = manageProjectInput.nextLine();
                        TaskFactory taskFactory = new TaskFactory();
                        consoleView.addTask(projectToManage, taskFactory.createTask(taskDetails));
                    } catch (NumberFormatException | ParseException e) {
                        consoleView.consolePrint("Please enter your task format correctly");
                    }
                    break;
                case "view tasks":
                    consoleView.viewAllTasks(projectToManage);
                    break;
                case "edit task":
                    break;
                case "delete task":
                    break;
                default:
                    consoleView.consolePrint("Invalid command. Try again!");
                    break;
                }
            } else {
                consoleView.consolePrint("Please enter a command.");
            }
        }

    }

}
