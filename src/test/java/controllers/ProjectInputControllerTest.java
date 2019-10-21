package controllers;

import models.data.Project;
import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;
import views.CLIView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectInputControllerTest {
    private ProjectRepository projectRepository;
    private CLIView consoleView;
    private ProjectInputController projectInputController;
    private String simulatedUserinput;
    private String actualOutput;
    private String expectedOutput;


    ProjectInputControllerTest() {
        projectRepository = new ProjectRepository();
        consoleView = new CLIView();
        projectInputController = new ProjectInputController(consoleView,projectRepository);
    }

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testProjectAddMember() {
        Project project = new Project("Infinity_Gauntlet");
        String simulatedUserinput = "add member n/Jerry Zhang i/9123456 e/jerryzhang@gmail.com";
        projectInputController.projectAddMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry Zhang (Phone: 9123456 | Email: jerryzhang@gmail.com)";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testProjectEditMember() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserinput = "add member n/Jerry Zhang i/9123456 e/jerryzhang@gmail.com";
        projectInputController.projectAddMember(project,simulatedUserinput);

        simulatedUserinput = "edit member 1 n/Dillen i/9123456 e/dillen@gmail.com";
        projectInputController.projectEditMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Dillen (Phone: 9123456 | Email: dillen@gmail.com)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserinput = "edit member 1 n/Jerry";
        projectInputController.projectEditMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry (Phone: 9123456 | Email: dillen@gmail.com)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserinput = "edit member 1 i/911";
        projectInputController.projectEditMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry (Phone: 911 | Email: dillen@gmail.com)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserinput = "edit member 1 e/jerry@gmail.com";
        projectInputController.projectEditMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry (Phone: 911 | Email: jerry@gmail.com)";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testProjectDeleteMember() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserinput = "add member n/Jerry Zhang i/9123456 e/jerryzhang@gmail.com";
        projectInputController.projectAddMember(project,simulatedUserinput);

        simulatedUserinput = "delete member 1";
        projectInputController.projectDeleteMember(project,simulatedUserinput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testProjectViewCredits() {

    }

    @Test
    void testProjectAddTask() {
    }

    @Test
    void testProjectEditTask() {

    }

    @Test
    void testProjectDeleteTask() {

    }

    @Test
    void testProjectEditTaskRequirements() {

    }

    @Test
    void testProjectViewTaskRequirements() {

    }

    @Test
    void testProjectAssignTask() {

    }

    @Test
    void testProjectViewAssignedTasks() {

    }

    @Test
    void testProjectViewTasks() {

    }

    @Test
    void testProjectExit() {

    }

}