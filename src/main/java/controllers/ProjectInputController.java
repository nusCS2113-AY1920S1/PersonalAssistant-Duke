package controllers;

import java.util.Scanner;
import models.data.IProject;
import models.member.Member;
import views.CLIView;

public class ProjectInputController {
    private Scanner manageProjectInput;
    private boolean continueManaging;
    private IProject projectToManage;
    private CLIView consoleView;

    public ProjectInputController(IProject projectToManage, CLIView consoleView) {
        this.manageProjectInput = new Scanner(System.in);
        this.continueManaging = true;
        this.projectToManage = projectToManage;
        this.consoleView = consoleView;
    }

    public void manageProject() {
        this.consoleView.consolePrint("Now managing: " + this.projectToManage.getDescription());
        while (continueManaging) {
            if (manageProjectInput.hasNextLine()) {
                String projectCommand = manageProjectInput.nextLine();
                switch (projectCommand) {
                    case "exit":
                        continueManaging = false;
                        consoleView.exitProject(this.projectToManage.getDescription());
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
