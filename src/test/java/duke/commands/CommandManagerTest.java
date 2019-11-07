//@@lmtaek

package duke.commands;

import duke.commands.assignedtask.AssignPeriodTaskCommand;
import duke.commands.assignedtask.DeleteAssignedTaskCommand;
import duke.commands.assignedtask.FindAssignedTaskCommand;
import duke.commands.functional.DukeCommand;
import duke.commands.functional.ExitCommand;
import duke.commands.functional.HelpCommand;
import duke.commands.functional.UndoCommand;
import duke.commands.patient.AddPatientCommand;
import duke.commands.patient.DeletePatientCommand;
import duke.commands.patient.FindPatientCommand;
import duke.commands.patient.ListPatientsCommand;
import duke.commands.patient.UpdatePatientCommand;
import duke.commands.task.AddTaskCommand;
import duke.commands.task.DeleteTaskCommand;
import duke.commands.task.FindTaskCommand;
import duke.commands.task.ListTasksCommand;
import duke.commands.task.UpdateTaskCommand;
import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandManagerTest {
    /**
     * Test the return command type of Parser.parse(userInput)
     *
     * @throws DukeException referencing a Duke specified exception with error log
     */
    @Test
    public void commandExitTest() throws DukeException {
        Command result = CommandManager.manageCommand("bye");
        assertTrue(result instanceof ExitCommand, "The command type should be ExitCommand");
    }

    @Test
    void commandAddPatientTest() throws DukeException {
        Command result = CommandManager.manageCommand("add patient :name :nric :room :remark");
        assertTrue(result instanceof AddPatientCommand, "The command type should "
                + "be AddPatientCommand");
    }

    @Test
    void commandAddTaskTest() throws DukeException {
        Command result = CommandManager.manageCommand("add task :description");
        assertTrue(result instanceof AddTaskCommand, "The command type should "
                + "be AddTaskCommand");
    }


    @Test
    void commandAssignPeriodTest() throws DukeException {
        Command result = CommandManager.manageCommand("assign period task "
                + ":#1 :#2 :07/11/2019 1200 :08/11/2019 1200");
        assertTrue(result instanceof AssignPeriodTaskCommand, "The command type should "
                + "be AssignPeriodTaskCommand");
    }

    @Test
    void commandListPatientsTest() throws DukeException {
        Command result = CommandManager.manageCommand("list patients");
        assertTrue(result instanceof ListPatientsCommand, "The command type should "
                + "be ListPatientsCommand");
    }

    @Test
    void commandListTasksTest() throws DukeException {
        Command result = CommandManager.manageCommand("list tasks");
        assertTrue(result instanceof ListTasksCommand, "The command type should "
                + "be ListTasksCommand");
    }

    @Test
    void commandDeleteAssignedTaskTest() throws DukeException {
        Command result = CommandManager.manageCommand("delete assigned task :#3");
        assertTrue(result instanceof DeleteAssignedTaskCommand, "The command type should "
                + "be DeleteAssignedTaskCommand");
    }

    @Test
    void commandDeletePatientTest() throws DukeException {
        Command result = CommandManager.manageCommand("delete patient :#1");
        assertTrue(result instanceof DeletePatientCommand, "The command type should "
                + "be DeletePatientCommand");
    }

    @Test
    void commandDeleteTaskTest() throws DukeException {
        Command result = CommandManager.manageCommand("delete task :#2");
        assertTrue(result instanceof DeleteTaskCommand, "The command type should "
                + "be DeleteTaskCommand");
    }

    @Test
    void commandFindPatientTest() throws DukeException {
        Command result = CommandManager.manageCommand("find patient :Sarah");
        assertTrue(result instanceof FindPatientCommand, "The command type should "
                + "be FindPatientCommand");
    }

    @Test
    void commandFindTaskTest() throws DukeException {
        Command result = CommandManager.manageCommand("find task :Take medicine");
        assertTrue(result instanceof FindTaskCommand, "The command type should "
                + "be FindTaskCommand");
    }

    @Test
    void commandFindAssignedTasksTest() throws DukeException {
        Command result = CommandManager.manageCommand("find assigned tasks :#2");
        assertTrue(result instanceof FindAssignedTaskCommand, "The command type should "
                + "be FindAssignedTasksCommand");
    }

    @Test
    void commandUpdatePatientTest() throws DukeException {
        Command result = CommandManager.manageCommand("update patient :room :12A");
        assertTrue(result instanceof UpdatePatientCommand, "The command type should "
                + "be UpdatePatientCommand");
    }

    @Test
    void commandUpdateTaskTest() throws DukeException {
        Command result = CommandManager.manageCommand("update task :description :new info");
        assertTrue(result instanceof UpdateTaskCommand, "The command type should "
                + "be UpdateTaskCommand");
    }

    /*
    @Test
    void commandShowUpcomingTasksTest() throws DukeException {
        Command result = CommandManager.manageCommand("show upcoming tasks");
        assertTrue(result instanceof UpcomingTasksCommand, "The command type should "
                + "be UpcomingTasksCommand");
    }
    */

    @Test
    void commandDukeTest() throws DukeException {
        Command result = CommandManager.manageCommand("duke");
        assertTrue(result instanceof DukeCommand, "The command type should "
                + "be DukeCommand");
    }

    @Test
    void commandUndoTest() throws DukeException {
        Command result = CommandManager.manageCommand("undo");
        assertTrue(result instanceof UndoCommand, "The command type should "
                + "be UndoCommand");
    }

    @Test
    void commandHelpTest() throws DukeException {
        Command result = CommandManager.manageCommand("help");
        assertTrue(result instanceof HelpCommand, "The command type should "
                + "be HelpCommand");
    }


}
