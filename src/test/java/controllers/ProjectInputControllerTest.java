package controllers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;
import models.project.Project;
import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;
import util.date.DateTimeHelper;

class ProjectInputControllerTest {
    private ProjectRepository projectRepository;
    private ProjectInputController projectInputController;
    private DateTimeHelper dateTimeHelper;
    private String simulatedUserInput;
    private String actualOutput;
    private String expectedOutput;
    private Date dueDate;

    ProjectInputControllerTest() {
        this.projectRepository = new ProjectRepository();
        this.projectInputController = new ProjectInputController(projectRepository);
        this.dateTimeHelper = new DateTimeHelper();
    }

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testProjectAddMember_valid() {
        Project project = new Project("Infinity_Gauntlet");
        String simulatedUserInput = "add member -n Jerry Zhang -i 9123456 -e jerryzhang@gmail.com";
        projectInputController.projectAddMember(project,simulatedUserInput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry Zhang (Phone: 9123456 | Email: jerryzhang@gmail.com | Role: member)";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testProjectAddMember_duplicateMembers() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Cynthia";
        projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals(1, project.getNumOfMembers());
        assertEquals("Cynthia", project.getMember(1).getName());
        simulatedUserInput = "add member -n Cynthia -p 98765432";
        String[] projectOutput = projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals(1, project.getNumOfMembers());
        assertEquals("The member you have tried to add already exists!", projectOutput[0]);
    }

    @Test
    void testProjectAddMember_noName() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member";
        String[] projectOutput = projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals("Add member command minimum usage must be \"add member -n NAME\"!",
            projectOutput[0]);
        simulatedUserInput = "add member -n";
        projectOutput = projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals("Name cannot be empty! Please follow the add command format in user guide!"
            + " \"add member -n NAME\" is the minimum requirement for add member command", projectOutput[0]);
    }


    @Test
    void testProjectEditMember_valid() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Jerry Zhang -i 9123456 -e jerryzhang@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);

        simulatedUserInput = "edit member 1 -n Dillen -i 9123456 -e dillen@gmail.com";
        projectInputController.projectEditMember(project, simulatedUserInput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Dillen (Phone: 9123456 | Email: dillen@gmail.com | Role: member)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "edit member 1 -n Jerry";
        projectInputController.projectEditMember(project, simulatedUserInput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry (Phone: 9123456 | Email: dillen@gmail.com | Role: member)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "edit member 1 -i 911";
        projectInputController.projectEditMember(project, simulatedUserInput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Jerry (Phone: 911 | Email: dillen@gmail.com | Role: member)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "edit member 1 -e jerry@gmail.com -n Thanos Endgame";
        projectInputController.projectEditMember(project, simulatedUserInput);
        actualOutput = "";
        for (String message : project.getMembers().getAllMemberDetails().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Thanos Endgame (Phone: 911 | Email: jerry@gmail.com | Role: member)";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testProjectDeleteMember_valid() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Jerry Zhang -i 9123456 -e jerryzhang@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals(1, project.getNumOfMembers());
        simulatedUserInput = "delete member 1";
        projectInputController.projectDeleteMember(project, simulatedUserInput);
        assertEquals(0, project.getNumOfMembers());

        //delete multiple
        simulatedUserInput = "add member -n Cynthia";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "add member -n Sean";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "add member -n Abhishek";
        projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals(3, project.getNumOfMembers());
        simulatedUserInput = "delete member 1 3";
        projectInputController.projectDeleteMember(project, simulatedUserInput);
        assertEquals(1, project.getNumOfMembers());
        assertEquals("Sean", project.getMember(1).getName());
    }

    @Test
    void testProjectDeleteMember_invalid() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Jerry Zhang -i 9123456 -e jerryzhang@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);
        assertEquals(1, project.getNumOfMembers());
        simulatedUserInput = "delete member";
        String[] output = projectInputController.projectDeleteMember(project, simulatedUserInput);
        assertEquals("Can't delete members: No member index numbers detected!", output[0]);
        simulatedUserInput = "delete member abc";
        output = projectInputController.projectDeleteMember(project, simulatedUserInput);
        assertEquals("Could not recognise member abc, please ensure it is an integer.", output[0]);
        assertEquals("No valid member indexes. Cannot delete members.", output[1]);
    }

    @Test
    void testProjectViewCredits() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Dillen -i 9999 -e dillen@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);

        simulatedUserInput = "add member -n Jerry - i 9999 -e jerryn@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);

        simulatedUserInput = "add member -n Sean";
        projectInputController.projectAddMember(project, simulatedUserInput);

        simulatedUserInput = "add task -t task1 -p 1 -c 10 -s doing";
        projectInputController.projectAddTask(project, simulatedUserInput);

        simulatedUserInput = "add task -t task2 -p 5 -c 10 -s done";
        projectInputController.projectAddTask(project, simulatedUserInput);

        actualOutput = "";
        for (String message : project.getCredits().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Dillen: 0 credits   Progress: .................... (0%)"
                + "2. Jerry: 0 credits   Progress: .................... (0%)"
                + "3. Sean: 0 credits   Progress: .................... (0%)";
        assertEquals(expectedOutput, actualOutput);

        simulatedUserInput = "assign task -i 1 -to 1 2";
        projectInputController.projectAssignTask(project, simulatedUserInput);

        simulatedUserInput = "assign task -i 2 -to 1 3";
        projectInputController.projectAssignTask(project, simulatedUserInput);

        actualOutput = "";
        for (String message : project.getCredits().toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "1. Dillen: 3 credits   Progress: ##########.......... (50%)"
                + "2. Jerry: 0 credits   Progress: .................... (0%)"
                + "3. Sean: 3 credits   Progress: #################### (100%)";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testProjectAddTask_valid() {
        try {
            Project project = new Project("Infinity_Gauntlet");
            dueDate = dateTimeHelper.formatDate("21/09/2019");
            simulatedUserInput = "add task -t Documentation for product -p 2 -d 21/09/2019 -c 40 -s todo "
                    + "-r do something -r do another thing";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "add task -t Documentation for product 1 -p 2 -c 40 "
                    + "-s done -r do something -r do another thing";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "add task  -p 2 -t Documentation for product 2 -c 40 -r do something "
                    + "-r do another thing";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "add task -t Documentation for product 3 -p 2 -c 40";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "add task -s doing -t Documentation for CS2113 -c 40 -d 21/09/2019 -p 2";
            projectInputController.projectAddTask(project, simulatedUserInput);

            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. Documentation for product | Priority: 2 | Due: 21 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 40 | State: TODO"
                    + "2. Documentation for product 1 | Priority: 2 | Due: -- | Credit: 40 | State: DONE"
                    + "3. Documentation for product 2 | Priority: 2 | Due: -- | Credit: 40 | State: OPEN"
                    + "4. Documentation for product 3 | Priority: 2 | Due: -- | Credit: 40 | State: OPEN"
                    + "5. Documentation for CS2113 | Priority: 2 | Due: 21 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 40 | State: DOING";

            assertEquals(expectedOutput, actualOutput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectEditTask() {
        try {
            Project project = new Project("Infinity_Gauntlet");
            dueDate = dateTimeHelper.formatDate("22/09/2019");
            simulatedUserInput = "add task -t Documentation for product -p 2 -d 21/09/2019 -c 40 -s todo "
                    + "-r do something -r do another thing";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "edit task 1 -t No documentation -p 5 -d 22/09/2019 -c 50 -s done "
                        + "-r do nothing -r do another thing";
            projectInputController.projectEditTask(project,simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. No documentation | Priority: 5 | Due: 22 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 50 | State: DONE";
            assertEquals(expectedOutput,actualOutput);

            simulatedUserInput = "edit task 1 -p 5 -t Infinity War -d 22/09/2019 -c 40 -s todo";
            projectInputController.projectEditTask(project,simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }

            expectedOutput = "1. Infinity War | Priority: 5 | Due: 22 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 40 | State: TODO";
            assertEquals(expectedOutput,actualOutput);

            simulatedUserInput = "edit task 1 -t Infinity War -p 1 -c 30";
            projectInputController.projectEditTask(project,simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. Infinity War | Priority: 1 | Due: 22 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 30 | State: TODO";
            assertEquals(expectedOutput,actualOutput);

            simulatedUserInput = "edit task 1 -c 20 -p 2";
            projectInputController.projectEditTask(project,simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. Infinity War | Priority: 2 | Due: 22 Sep 2019"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 20 | State: TODO";
            assertEquals(expectedOutput,actualOutput);

            simulatedUserInput = "edit task 1 -c 70 -s doing -p 6 -d 12/12/2020 -t End Game";
            //6 is an invalid value for task priority, so priority remains as 2 after this command
            dueDate = dateTimeHelper.formatDate("12/12/2020");
            projectInputController.projectEditTask(project,simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. End Game | Priority: 2 | Due: 12 Dec 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate)
                    + " | Credit: 70 | State: DOING";
            assertEquals(expectedOutput,actualOutput);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectViewTask() {
        try {
            Project project = new Project("Infinity_Gauntlet");
            simulatedUserInput = "add task -t task1 -p 5 -c 10 -s todo -d 12/12/2021";
            projectInputController.projectAddTask(project, simulatedUserInput);
            simulatedUserInput = "add task -t task2 -p 5 -c 100 -s doing";
            projectInputController.projectAddTask(project, simulatedUserInput);
            simulatedUserInput = "add task -t task3 -p 1 -c 50 -s done -d 1/1/2020";
            projectInputController.projectAddTask(project, simulatedUserInput);
            simulatedUserInput = "add member -n Dillen -i 9999 -e dillen@gmail.com";
            projectInputController.projectAddMember(project, simulatedUserInput);
            simulatedUserInput = "add member -n Jerry - i 9999 -e jerryn@gmail.com";
            projectInputController.projectAddMember(project, simulatedUserInput);
            simulatedUserInput = "assign task -i 1 -to 1 2";
            projectInputController.projectAssignTask(project, simulatedUserInput);
            simulatedUserInput = "assign task -i 2 -to 1";
            projectInputController.projectAssignTask(project, simulatedUserInput);

            Date dueDate1 = dateTimeHelper.formatDate("12/12/2021");
            Date dueDate2 = dateTimeHelper.formatDate("1/1/2020");
            actualOutput = "";
            for (String message : project.getTasks().getAllTaskDetails(
                    project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task3 | Priority: 1 | Due: 01 Jan 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 50 | State: DONE"
                    + "2. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO"
                    + "3. task2 | Priority: 5 | Due: -- | Credit: 100 | State: DOING";
            assertEquals(expectedOutput, actualOutput);

            actualOutput = "";
            for (String message : project.getTasks().getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(), "/PRIORITY", project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task3 | Priority: 1 | Due: 01 Jan 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 50 | State: DONE"
                    + "2. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO"
                    + "3. task2 | Priority: 5 | Due: -- | Credit: 100 | State: DOING";
            assertEquals(expectedOutput, actualOutput);

            actualOutput = "";
            for (String message : project.getTasks().getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(), "/NAME", project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO"
                    + "2. task2 | Priority: 5 | Due: -- | Credit: 100 | State: DOING"
                    + "3. task3 | Priority: 1 | Due: 01 Jan 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 50 | State: DONE";
            assertEquals(expectedOutput, actualOutput);

            actualOutput = "";
            for (String message : project.getTasks().getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(), "/DATE", project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task3 | Priority: 1 | Due: 01 Jan 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 50 | State: DONE"
                    + "2. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO";
            assertEquals(expectedOutput, actualOutput);

            actualOutput = "";
            for (String message : project.getTasks().getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(), "/CREDIT", project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task2 | Priority: 5 | Due: -- | Credit: 100 | State: DOING"
                    + "2. task3 | Priority: 1 | Due: 01 Jan 2020"
                    + dateTimeHelper.getDifferenceDays(dueDate2)
                    + " | Credit: 50 | State: DONE"
                    + "3. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO";
            assertEquals(expectedOutput, actualOutput);

            actualOutput = "";
            for (String message : project.getTasks().getAllSortedTaskDetails(
                    project.getTasksAndAssignedMembers(), "/WHO-Dillen", project).toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "1. task1 | Priority: 5 | Due: 12 Dec 2021"
                    + dateTimeHelper.getDifferenceDays(dueDate1)
                    + " | Credit: 10 | State: TODO"
                    + "2. task2 | Priority: 5 | Due: -- | Credit: 100 | State: DOING";
            assertEquals(expectedOutput, actualOutput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectDeleteTask() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add task -t Documentation for product -p 2 d/21/09/2019 -c 40 s/todo "
                + "r/do something -r do another thing";
        projectInputController.projectAddTask(project, simulatedUserInput);
        simulatedUserInput = "delete task 1";
        projectInputController.projectDeleteTask(project, simulatedUserInput);
        actualOutput = "";
        for (String message : project.getTasks().getAllTaskDetails(
                project.getTasksAndAssignedMembers(), project).toArray(new String[0])) {
            actualOutput += message;
        }
        expectedOutput = "";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testProjectEditTaskRequirements() {
        try {
            Project project = new Project("Infinity_Gauntlet");
            DateTimeHelper dateTimeHelper = new DateTimeHelper();
            dueDate = dateTimeHelper.formatDate("21/09/2019");
            simulatedUserInput = "add task -t Documentation for product -p 2 -d 21/09/2019 -c 40 -s todo "
                    + "-r do something -r do another thing";
            projectInputController.projectAddTask(project, simulatedUserInput);

            simulatedUserInput = "edit task requirements 1 -r do nothing";
            projectInputController.projectEditTaskRequirements(project, simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTask(1).getTaskRequirements().toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "Requirements for the task 'Documentation for product':"
                    + "1. do something2. do another thing3. do nothing";
            assertEquals(expectedOutput, actualOutput);

            simulatedUserInput = "edit task requirements 1 -rm 1 2 -r do everything";
            projectInputController.projectEditTaskRequirements(project, simulatedUserInput);
            actualOutput = "";
            for (String message : project.getTask(1).getTaskRequirements().toArray(new String[0])) {
                actualOutput += message;
            }
            expectedOutput = "Requirements for the task 'Documentation for product':"
                    + "1. do nothing2. do everything";
            assertEquals(expectedOutput, actualOutput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectAssignTask_valid() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add task -t Documentation for product -p 2 -d 21/09/2019 -c 40 -s todo "
                + "-r do something -r do another thing";
        projectInputController.projectAddTask(project, simulatedUserInput);
        simulatedUserInput = "add member -n Jerry Zhang -i 9123456 -e jerryzhang@gmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "add member -n Dillen -i 911 -e dillen@hotmail.com";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "assign task -i 1 -to 1 2";
        String[] actualOutput = projectInputController.projectAssignTask(project, simulatedUserInput);
        String[] expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Results from task assignments:                                        |",
            "+----------------------------------------------------------------------+",
            "| +------------------------------------------------------------------+ |",
            "| |For task 1 (Documentation for product):                           | |",
            "| +------------------------------------------------------------------+ |",
            "| |Assigned to member 1 (Jerry Zhang).                               | |",
            "| |Assigned to member 2 (Dillen).                                    | |",
            "| +------------------------------------------------------------------+ |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, actualOutput);
        simulatedUserInput = "assign task -i 1 -rm 1";
        actualOutput = projectInputController.projectAssignTask(project, simulatedUserInput);
        expectedOutput = new String[] {
            "+----------------------------------------------------------------------+",
            "|Results from task assignments:                                        |",
            "+----------------------------------------------------------------------+",
            "| +------------------------------------------------------------------+ |",
            "| |For task 1 (Documentation for product):                           | |",
            "| +------------------------------------------------------------------+ |",
            "| |Unassigned task from member 1 (Jerry Zhang).                      | |",
            "| +------------------------------------------------------------------+ |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    //@@author sinteary
    @Test
    void testProjectViewAssignments_invalidInputs() {
        Project project = new Project("New project");
        simulatedUserInput = "view assignments";
        String[] output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("Please input the parameters to view assignments:", output[0]);
        simulatedUserInput = "view assignments -";
        output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("Please input the parameters to view assignments:", output[0]);
        simulatedUserInput = "view assignments atm";
        output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("Please input the parameters to view assignments:",
            output[0]);
        //no members
        simulatedUserInput = "view assignments -m all";
        output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("No members in project yet.", output[0]);
        //no tasks
        simulatedUserInput = "view assignments -t all";
        output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("No tasks in project yet.", output[0]);
        simulatedUserInput = "view assignments -a 1";
        output = projectInputController.projectViewAssignments(project, simulatedUserInput);
        assertEquals("Could not understand your command! Please use:", output[0]);
    }


    @Test
    void assignRole_correctInputs() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add member -n Tony Stark -e richguy@gmail.com -r RichGuy";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "add member -n Doctor Strange -i 9123456 -e strange@gmail.com -r SmartGuy";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "add member -n Hulk -i 911 -r StrongGuy";
        projectInputController.projectAddMember(project, simulatedUserInput);
        simulatedUserInput = "role 1 -n IronMan";
        projectInputController.projectRoleMembers(project, simulatedUserInput);
        actualOutput = project.getMembers().getMember(1).getRole();
        expectedOutput = "IronMan";
        assertEquals(expectedOutput, actualOutput);
        assertEquals("SmartGuy", project.getMembers().getMember(2).getRole());
        assertEquals("StrongGuy", project.getMembers().getMember(3).getRole());
    }

    //@@author Dkenobi
    @Test
    void testAddReminder_correctInput() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        actualOutput = project.getReminderList().get(0).getReminderName();
        expectedOutput = "Make new suit for the team";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testAddReminder_invalidInput() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder Make new suit for the team may be try using pink 10/10/2019";
        String [] invalid = projectInputController.projectAddReminder(project,simulatedUserInput);
        String [] expectedOutputArray = new String[] {"Failed to create new task. "
                + "Please ensure all necessary parameters are given"};
        assertArrayEquals(expectedOutputArray, invalid);
    }

    @Test
    void testViewReminder() throws ParseException {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        String[] actualOutputArray = projectInputController.projectViewReminder(project);
        String[] expectedOutputArray = new String[] {
            "+----------------------------------------------------------------------+",
            "|Reminder of Infinity_Gauntlet:                                        |",
            "+----------------------------------------------------------------------+",
            "|1. [X] Make new suit for the team                                     |",
            "|   - Remarks: may be try using pink                                   |",
            "|   - 10 Oct 2019"
                    + dateTimeHelper.getDifferenceDays(dateTimeHelper.formatDate("10/10/2019"))
                    + "                                   |",
            "+----------------------------------------------------------------------+"};
        assertArrayEquals(expectedOutputArray,actualOutputArray);
    }

    @Test
    void testDeleteReminder() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "add reminder -n System integration with system team "
                + "-r Need to bring along thumbdrive -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "delete reminder 1";
        projectInputController.projectDeleteReminder(project,simulatedUserInput);
        int reminderListSize = project.getReminderListSize();
        assertEquals(1,reminderListSize);
    }

    @Test
    void testDeleteReminder_invalidIndex() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "delete reminder 100";
        String[] invalidArray = projectInputController.projectDeleteReminder(project,simulatedUserInput);
        String [] expectedArray = {"No reminder index number found in the list! "
                + "Please enter the correct reminder index number."};
        assertArrayEquals(expectedArray,invalidArray);
    }

    @Test
    void testEditReminder() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "edit reminder 1 -n new reminder name";
        projectInputController.projectEditReminder(project,simulatedUserInput);
        actualOutput = project.getReminderList().get(0).getReminderName();
        expectedOutput = "new reminder name";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testEditReminder_invalidIndex() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "edit reminder 100 -n new reminder name";
        String[] invalidArray = projectInputController.projectEditReminder(project,simulatedUserInput);
        String [] expectedArray = {"No reminder index number found in the list! "
                + "Please enter the correct reminder index number."};
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testSetReminderStatus() {
        Project project = new Project("Infinity_Gauntlet");
        simulatedUserInput = "add reminder -n Make new suit for the team -r may be try using pink -d 10/10/2019";
        projectInputController.projectAddReminder(project,simulatedUserInput);
        simulatedUserInput = "mark reminder 1";
        projectInputController.projectSetReminderStatus(project,simulatedUserInput);
        Boolean reminderStatus = project.getReminder(1).getIsDone();
        assertEquals(true,reminderStatus);
    }
}