package controllers;

import java.util.Scanner;
import models.data.IProject;
import models.member.Member;
import repositories.ProjectRepository;
import views.CLIView;

public class ProjectInputController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private CLIView consoleView;

    public ProjectInputController(CLIView consoleView, ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.consoleView = consoleView;
    }

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
                        Member newMember = MemberFactoryUtil.createMember(memberDetails);
                        consoleView.addMember(projectToManage, newMember);
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
