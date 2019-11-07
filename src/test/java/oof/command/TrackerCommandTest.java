package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.StorageFileCorruptedException;
import oof.exception.command.CommandException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.storage.StorageManager;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author debbiextan

public class TrackerCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();

    /**
     * Test behavior when tracker instruction not given.
     */
    @Test
    public void execute_emptyDescription_exceptionThrown() {
        try {
            oof.executeCommand("tracker");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Please enter your instructions!", e.getMessage());
        }
    }

    /**
     * Test behavior when tracker instruction is invalid.
     */
    @Test
    public void execute_invalidDescription_exceptionThrown() {
        try {
            oof.executeCommand("tracker /test ");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid Command!", e.getMessage());
        }
    }

    /**
     * Test behavior when tracker view period is empty.
     */
    @Test
    public void execute_emptyPeriod_exceptionThrown() {
        try {
            oof.executeCommand("tracker /view");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid Commmand!", e.getMessage());
        }
    }

    /**
     * Test behavior when tracker view period is invalid.
     */
    @Test
    public void execute_stringPeriod_exceptionThrown() {
        try {
            oof.executeCommand("tracker /view abc");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid Period!", e.getMessage());
        }
    }

    /**
     * Test behavior when tracker view period is an integer.
     */
    @Test
    public void execute_integerPeriod_exceptionThrown() {
        try {
            oof.executeCommand("tracker /view 12");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid Period!", e.getMessage());
        }
    }


    /**
     * test behavior when an invalid task index is given.
     */
    @Test
    public void execute_invalidTaskIndex_exceptionThrown() {
        try {
            oof.executeCommand("tracker /start 12345 CS2113T");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Invalid Task Index!", e.getMessage());
        }
    }

    /**
     * Test behavior when tracker tries to stop without starting.
     *
     * @throws CommandException if Command is invalid.
     * @throws ParserException if Command cannot be parsed.
     */
    @Test
    public void execute_stopTimerWithoutStarting_exceptionThrown() throws CommandException, ParserException {
        try {
            oof.executeCommand("todo stop without start /on 05-11-2019");
            int recent = taskList.getSize();
            String command = "tracker /stop " + recent + " CS2113T";
            oof.executeCommand(command);
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Tracker for this Assignment has not started.", e.getMessage());
        }
        int index = taskList.getSize();
        oof.executeCommand("delete " + index);
    }

    /**
     * Test behavior when tracker tries to pause without starting.
     *
     * @throws CommandException if Command is invalid.
     * @throws ParserException if Command cannot be parsed.
     */
    @Test
    public void execute_pauseTimerWithoutStarting_exceptionThrown() throws CommandException, ParserException {
        try {
            oof.executeCommand("todo pause test /on 05-11-2019");
            int recent = taskList.getSize();
            String command = "tracker /pause " + recent + " CS2113T";
            oof.executeCommand(command);
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Tracker for this Assignment has not started.", e.getMessage());
        }
        int index = taskList.getSize();
        oof.executeCommand("delete " + index);
    }

    /**
     * Test behavior when tracker tries to start a task tracker that has already been stopped and completed.
     *
     * @throws CommandException if Command is invalid.
     * @throws ParserException if Command cannot be parsed.
     */
    @Test
    public void execute_startTimerThatHasStopped_exceptionThrown() throws CommandException, ParserException {
        try {
            oof.executeCommand("todo start stopped timer /on 06-11-2019");
            int recent = taskList.getSize();
            oof.executeCommand("done " + recent);
            String command = "tracker /start " + recent + " CS2113T";
            oof.executeCommand(command);
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Task has already been completed.", e.getMessage());
        }
        int index = taskList.getSize();
        oof.executeCommand("delete " + index);
    }

    /**
     * Test behavior when tracker tries to start/stop/pause a task description
     * that does not match the previous start entry.
     *
     * @throws CommandException if Command is invalid.
     * @throws ParserException if Command cannot be parsed.
     * @throws StorageFileCorruptedException if tracker.csv cannot be read.
     */
    @Test
    public void execute_taskDescriptionsNotMatched_exceptionThrown() throws CommandException,
            ParserException, StorageFileCorruptedException {
        try {
            oof.executeCommand("todo description /on 06-11-2019");
            oof.executeCommand("todo desc /on 07-11-2019");
            int index = taskList.getSize() - 1;
            String command = "tracker /start " + index + " CS2113T";
            oof.executeCommand(command);
            oof.executeCommand("delete " + index);

            command = "tracker /start " + index + " CS2113T";
            oof.executeCommand(command);
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("Task descriptions do not match!", e.getMessage());
        }
        int index = taskList.getSize();
        oof.executeCommand("delete " + index);
        ArrayList<Tracker> trackerList = oof.getStorageManager().readTrackerList();
        int trackerIndex = trackerList.size();
        oof.executeCommand("tracker /delete " + trackerIndex);
    }

    /**
     * Test behavior for starting a Tracker.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException if command cannot be parsed.
     * @throws StorageFileCorruptedException if tracker.csv cannot be processed.
     */
    @Test
    public void execute_correctStartTracker_startTracker() throws CommandException,
            ParserException, StorageFileCorruptedException {
        oof.executeCommand("todo start tracker /on 06-11-2019");
        int recent = taskList.getSize();
        String command = "tracker /start " + recent + " CS2113T";
        oof.executeCommand(command);

        StorageManager storageManager = oof.getStorageManager();
        ArrayList<Tracker> entries = storageManager.readTrackerList();
        int lastEntry = entries.size() - 1;
        Tracker tracker = entries.get(lastEntry);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        String lastUpdated = simpleDateFormat.format(tracker.getLastUpdated());
        assertEquals(date, lastUpdated);

        oof.executeCommand("tracker /stop " + recent + " CS2113T");
        oof.executeCommand("delete " + recent);
        ArrayList<Tracker> trackerList = oof.getStorageManager().readTrackerList();
        int trackerIndex = trackerList.size();
        oof.executeCommand("tracker /delete " + trackerIndex);
    }

    /**
     * Test behavior for pausing a Tracker.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException if command cannot be parsed.
     * @throws StorageFileCorruptedException if tracker.csv cannot be processed.
     */
    @Test
    public void execute_correctPauseTracker_pauseTracker() throws CommandException,
            ParserException, StorageFileCorruptedException {
        oof.executeCommand("todo pause tracker /on 06-11-2019");
        int recent = taskList.getSize();
        String command = "tracker /start " + recent + " CS2113T";
        oof.executeCommand(command);

        command = "tracker /pause " + recent + " CS2113T";
        oof.executeCommand(command);
        StorageManager storageManager = oof.getStorageManager();
        ArrayList<Tracker> entries = storageManager.readTrackerList();
        int lastEntry = entries.size() - 1;
        Tracker tracker = entries.get(lastEntry);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        assertEquals(date, simpleDateFormat.format(tracker.getLastUpdated()));
        assertNull(tracker.getStartDate());

        oof.executeCommand("delete " + recent);
        ArrayList<Tracker> trackerList = oof.getStorageManager().readTrackerList();
        int trackerIndex = trackerList.size();
        oof.executeCommand("tracker /delete " + trackerIndex);
    }

    /**
     * Test behavior for stopping a Tracker.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException if command cannot be parsed.
     * @throws StorageFileCorruptedException if tracker.csv cannot be processed.
     */
    @Test
    public void execute_correctStopTracker_stopTracker() throws CommandException,
            ParserException, StorageFileCorruptedException {
        oof.executeCommand("todo stop tracker /on 06-11-2019");
        int recent = taskList.getSize();
        String command = "tracker /start " + recent + " CS2113T";
        oof.executeCommand(command);

        command = "tracker /stop " + recent + " CS2113T";
        oof.executeCommand(command);
        StorageManager storageManager = oof.getStorageManager();
        ArrayList<Tracker> entries = storageManager.readTrackerList();
        int lastEntry = entries.size() - 1;
        Tracker tracker = entries.get(lastEntry);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        assertEquals(date, simpleDateFormat.format(tracker.getLastUpdated()));
        assertNull(tracker.getStartDate());

        int index = tracker.getTaskIndex();
        Task task = taskList.getTask(index);
        assertTrue(task.getStatus());

        oof.executeCommand("delete " + recent);
        ArrayList<Tracker> trackerList = oof.getStorageManager().readTrackerList();
        int trackerIndex = trackerList.size();
        oof.executeCommand("tracker /delete " + trackerIndex);
    }
}
