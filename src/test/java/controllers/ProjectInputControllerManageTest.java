package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectInputControllerManageTest {
    private ProjectInputController projectInputController;
    private ProjectRepository projectRepository;
    private String simulatedUserInput;
    private String[] simulatedOutput;
    private String[] expectedOutput;

    ProjectInputControllerManageTest() {
        this.projectRepository = new ProjectRepository();
        this.projectInputController = new ProjectInputController(this.projectRepository);
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void onCommandReceived_noProjectIndex_executionFail() {
        simulatedUserInput = "test";
        simulatedOutput = projectInputController.onCommandReceived(simulatedUserInput);
        expectedOutput = new String[] {"Input is not a number! Please input a proper project index!"};
        assertArrayEquals(expectedOutput, simulatedOutput);
    }

    @BeforeEach
    void initializeProjectInputController() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        projectRepository.addToRepo("create Avengers Testing");
        simulatedUserInput = "1";
        simulatedOutput = projectInputController.onCommandReceived(simulatedUserInput);
        expectedOutput = new String[] {"Please enter a new command:"};
        assertArrayEquals(expectedOutput, simulatedOutput);
    }

    @Test
    void onCommandReceived_exitsProject_executionSuccess() {
        simulatedUserInput = "bye";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Bye. Hope to see you again soon!"};
        assertArrayEquals(expectedOutput, simulatedOutput);
    }

    @Test
    void manageProject_memberMethods_executionSuccess() {
        simulatedUserInput = "add member -n Thor";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Added new member to: Avengers Testing",
                                       "Member details 1. Thor (Phone: -- | Email: -- | Role: member)"};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "edit member 1 -n Marvel -e captain.marvel@gmail.com -i 91234567";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Updated member details with the index number 1"};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "add member -n Dr Strange";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        simulatedUserInput = "delete member 2";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Removed member 2: 2. Dr Strange (Phone: -- | Email: -- | Role: member)",
                                       "Take note that the member indexes might have changed after deleting!"};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "role 1 -n Captain";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Successfully changed the role of Marvel to Captain."};
        assertArrayEquals(expectedOutput, simulatedOutput);
    }

    @Test
    void manageProject_taskMethods_executionSuccess() {
        simulatedUserInput = "add task -t Kill Thanos -c 100 -p 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Added new task to the list."};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "edit task 1 -t Kill Ironman -c 1 -p 5 -d 20/11/2019 -s done";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Success!",
                                       "The name of this task has been changed to 'Kill Ironman'!",
                                       "The priority for this task has been set to 5!",
                                       "The new due date for this task has been set to 20 Nov 2019!",
                                       "The credit for this task has been set to 1!",
                                       "The state of this task has been set to DONE!"};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "delete task 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"Removed task 1: Kill Ironman",
                                       "Take note that index numbers of other tasks may have changed after deleting!"};
        assertArrayEquals(expectedOutput, simulatedOutput);
    }

    @Test
    void manageProject_viewTasksMethods_executionSuccess() {
        simulatedUserInput = "view tasks";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"+----------------------------------------------------------------------+",
                                       "|Tasks of Avengers Testing:                                            |",
                                       "+----------------------------------------------------------------------+",
                                       "| - There are currently no tasks! -                                    |",
                                       "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "add task -t Kill Thanos -c 100 -p 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        simulatedUserInput = "view tasks";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"+----------------------------------------------------------------------+",
                                       "|Tasks of Avengers Testing:                                            |",
                                       "+----------------------------------------------------------------------+",
                                       "|1. Kill Thanos                                                        |",
                                       "|   - Priority: 1                                                      |",
                                       "|   - Due: --                                                          |",
                                       "|   - Credit: 100                                                      |",
                                       "|   - State: OPEN                                                      |",
                                       "+----------------------------------------------------------------------+",
        };
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "view task requirements 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {"This task has no specific requirements."};
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "edit task requirements 1 -r Sacrifice Ironman";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {
            "Success!",
            "'Sacrifice Ironman' has been successfully added as a new requirement of this task!",
            "",
            "There were no errors!"
        };
        assertArrayEquals(expectedOutput, simulatedOutput);

        simulatedUserInput = "add task -t ATest -c 100 -p 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        simulatedUserInput = "add task -t BTest -c 100 -p 1";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        simulatedUserInput = "view tasks /NAME";
        simulatedOutput = projectInputController.manageProject(simulatedUserInput);
        expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Tasks of Avengers Testing:                                            |",
            "+----------------------------------------------------------------------+",
            "|1. ATest                                                              |",
            "|   - Priority: 1                                                      |",
            "|   - Due: --                                                          |",
            "|   - Credit: 100                                                      |",
            "|   - State: OPEN                                                      |",
            "|                                                                      |",
            "|2. BTest                                                              |",
            "|   - Priority: 1                                                      |",
            "|   - Due: --                                                          |",
            "|   - Credit: 100                                                      |",
            "|   - State: OPEN                                                      |",
            "|                                                                      |",
            "|3. Kill Thanos                                                        |",
            "|   - Priority: 1                                                      |",
            "|   - Due: --                                                          |",
            "|   - Credit: 100                                                      |",
            "|   - State: OPEN                                                      |",
            "+----------------------------------------------------------------------+"
        };
        assertArrayEquals(expectedOutput, simulatedOutput);
    }
}
