package controllers;

import java.util.Scanner;
import models.data.IProject;
import util.repositories.ProjectRepository;
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
                switch (projectCommand) {
                case "exit":
                    continueManaging = false;
                    consoleView.exitProject(projectToManage.getDescription());
                    break;
                case "add member":
                    consoleView.consolePrint("Enter member details: n/NAME p/PHONE e/EMAIL");
                    String memberDetails = manageProjectInput.nextLine();
                    MemberFactoryUtil memberFactory = new MemberFactoryUtil();
                    if (memberFactory.memberIsCreated(memberDetails)) {
                        consoleView.addMember(projectToManage, memberFactory.getNewMember());
                    } else {
                        consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                            + "at least the name of the new member.");
                    }
                    break;
                case "view members":
                    consoleView.viewAllMembers(projectToManage);
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
