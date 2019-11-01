package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.Event;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

//@@author yueyeah
public class EventCommandTest {
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListDup = new ArrayList<>();
    private TaskList taskListMain = new TaskList();
    private TaskList taskListDup = new TaskList();

    @BeforeEach
    void setUp() {
        this.taskListMain.setArrList(taskArrListMain);
        this.taskListDup.setArrList(taskArrListDup);
    }

    @Test
    void createAndAddEvent_validNonTrailingTask_success() {
        String testDescription = "CS2113T Exam";
        String testStartDateString = "23/11/2019";
        Date testStartDate = CompalUtils.stringToDate(testStartDateString);
        ArrayList<String> testStartDateList = new ArrayList<>();
        testStartDateList.add(testStartDateString);
        Task.Priority testPriority = Task.Priority.low;
        String testStartTime = "1300";
        String testEndTime = "1500";
        String testFinalDateString = "23/11/2019";
        int testInterval = 7;
        boolean testIsInOneDay = true;
        EventCommand testEventCommand = new EventCommand(testDescription, testStartDateList, testPriority,
                testStartTime, testEndTime, testFinalDateString, testInterval);
        String testString = testEventCommand.createAndAddEvent(testIsInOneDay, taskListMain, testStartDate);
        Event expectedEvent = createExpectedEvent(testIsInOneDay, testDescription, testStartDateString, testPriority,
                testStartTime, testEndTime);
        String expectedString = expectedEvent.toString();
        Assertions.assertEquals(expectedString, testString);
    }

    @Test
    void createAndAddEvent_validTrailingTask_success() {
        String testDescription = "CS2113T Study Session";
        String testStartDateString = "28/10/2019";
        Date testStartDate = CompalUtils.stringToDate(testStartDateString);
        ArrayList<String> testStartDateList = new ArrayList<>();
        testStartDateList.add(testStartDateString);
        Task.Priority testPriority = Task.Priority.low;
        String testStartTime = "2200";
        String testEndTime = "0200";
        String testFinalDateString = "28/10/2019";
        int testInterval = 7;
        boolean testIsInOneDay = false;
        EventCommand testEventCommand = new EventCommand(testDescription, testStartDateList, testPriority,
                testStartTime, testEndTime, testFinalDateString, testInterval);
        String testString = testEventCommand.createAndAddEvent(testIsInOneDay, taskListMain, testStartDate);
        Event expectedEvent = createExpectedEvent(testIsInOneDay, testDescription, testStartDateString, testPriority,
                testStartTime, testEndTime);
        String expectedString = expectedEvent.toString();
        Assertions.assertEquals(expectedString, testString);
    }

    @Test
    void commandExecute_validSingleTask_success() throws CommandException {
        String testDescription = "CS2113T Exam";
        String testStartDateString = "23/11/2019";
        Date testStartDate = CompalUtils.stringToDate(testStartDateString);
        ArrayList<String> testStartDateList = new ArrayList<>();
        testStartDateList.add(testStartDateString);
        Task.Priority testPriority = Task.Priority.low;
        String testStartTime = "1300";
        String testEndTime = "1500";
        String testFinalDateString = "23/11/2019";
        Date testFinalDate = CompalUtils.stringToDate(testFinalDateString);
        int testInterval = 7;
        boolean testIsInOneDay = true;
        EventCommand testEventCommand = new EventCommand(testDescription, testStartDateList, testPriority,
                testStartTime, testEndTime, testFinalDateString, testInterval);
        CommandResult testCommandResult = testEventCommand.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;

        String expectedString = "The following tasks were added: \n";
        while (!testStartDate.after(testFinalDate)) {
            testStartDateString = CompalUtils.dateToString(testStartDate);
            Event indivExpectedEvent = createExpectedEvent(testIsInOneDay, testDescription, testStartDateString,
                    testPriority, testStartTime, testEndTime);
            taskListDup.addTask(indivExpectedEvent);
            expectedString += indivExpectedEvent.toString();
            testStartDate = CompalUtils.incrementDateByDays(testStartDate, testInterval);
        }

        Assertions.assertEquals(expectedString, testString);
    }

    @Test
    void commandExecute_validRecurringTask_success() throws CommandException {
        String testDescription = "CS2113T Lecture";
        String testStartDateString = "10/10/2019";
        Date testStartDate = CompalUtils.stringToDate(testStartDateString);
        ArrayList<String> testStartDateList = new ArrayList<>();
        testStartDateList.add(testStartDateString);
        Task.Priority testPriority = Task.Priority.low;
        String testStartTime = "1700";
        String testEndTime = "1800";
        String testFinalDateString = "01/01/2020";
        Date testFinalDate = CompalUtils.stringToDate(testFinalDateString);
        int testInterval = 7;
        boolean testIsInOneDay = true;
        EventCommand testEventCommand = new EventCommand(testDescription, testStartDateList, testPriority,
                testStartTime, testEndTime, testFinalDateString, testInterval);
        CommandResult testCommandResult = testEventCommand.commandExecute(taskListMain);
        String testString = testCommandResult.feedbackToUser;

        String expectedString = "The following tasks were added: \n";
        while (!testStartDate.after(testFinalDate)) {
            testStartDateString = CompalUtils.dateToString(testStartDate);
            Event indivExpectedEvent = createExpectedEvent(testIsInOneDay, testDescription, testStartDateString,
                    testPriority, testStartTime, testEndTime);
            taskListDup.addTask(indivExpectedEvent);
            expectedString += indivExpectedEvent.toString();
            testStartDate = CompalUtils.incrementDateByDays(testStartDate, testInterval);
        }

        Assertions.assertEquals(expectedString, testString);
    }

    private Event createExpectedEvent(boolean isInOneDay, String testDescription, String testStartDateString,
                                      Task.Priority testPriority, String testStartTime, String testEndTime) {
        String testTrailingDateString;
        if (isInOneDay) {
            testTrailingDateString = testStartDateString;
        } else {
            Date testStartDate = CompalUtils.stringToDate(testStartDateString);
            Date testTrailingDate = CompalUtils.incrementDateByDays(testStartDate, 1);
            testTrailingDateString = CompalUtils.dateToString(testTrailingDate);
        }
        Event testEvent = new Event(testDescription, testPriority, testStartDateString, testTrailingDateString,
                testStartTime, testEndTime);
        return testEvent;
    }
}
