package leduc;

import leduc.exception.*;
import leduc.task.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiEnTest {
    private static Ui ui;
    private static TaskList tasks;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void BeforeUiEnExecuteTest(){
        ui = new UiEn();

        tasks = new TaskList(new ArrayList<>());

        assertTrue(tasks.size()==0);
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void UiEnExecuteTest(){
        Task task = new TodoTask("UiEnTest");
        HomeworkTask homeTask = null;
        EventsTask eventTask = null;
        try {
            homeTask = new HomeworkTask("UiEnTest", new Date("21/11/2019 20:20"));
        } catch (NonExistentDateException e) {
            e.printStackTrace();
        }
        try {
            eventTask = new EventsTask("UiEnTest", new Date("21/11/2019 20:20"), new Date("22/11/2019 20:20"));
        } catch (NonExistentDateException e) {
            e.printStackTrace();
        }
        ArrayList<Task> emptyList = new ArrayList<>();
        tasks.add(task);
        ui.showBye();
        assertTrue(outContent.toString().contains("Hope to see you again soon!"));
        ui.showDelete(task, 0);
        assertTrue(outContent.toString().contains("Noted. I've removed this task"));
        ui.showDone(task);
        assertTrue(outContent.toString().contains("I've marked this task as done"));
        ui.showTask(task, 1);
        assertTrue(outContent.toString().contains("Got it. I've added this task"));
        ui.showFindMatching("UiEnTest");
        assertTrue(outContent.toString().contains("Here are the most relevant tasks in your list"));
        ui.showFindNotMatching();
        assertTrue(outContent.toString().contains("There is no matching tasks in your list"));
        ui.showList(tasks);
        assertTrue(outContent.toString().contains("Here are the tasks in your list"));
        ui.showNoTask();
        assertTrue(outContent.toString().contains("There is any task yet"));
        ui.showPostpone(homeTask);
        assertTrue(outContent.toString().contains("Noted. I've postponed this task"));
        ui.showPrioritize(task);
        assertTrue(outContent.toString().contains("Got it. I've set the priority of this task"));
        ui.showReschedule(eventTask);
        assertTrue(outContent.toString().contains("Noted. I've rescheduled this task"));
        ui.showNewWelcome("UiTestFr");
        assertTrue(outContent.toString().contains("The welcome message is edited"));
        ui.showSnooze(homeTask);
        assertTrue(outContent.toString().contains("Noted. I've snoozed this task"));
        ui.showSort();
        assertTrue(outContent.toString().contains("This is the new task list order:"));
        ui.showGeneralStats(1,1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Here are some general statistics about your task list"));
        ui.showPriorityStats(1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Here are some priority statistics about your task list"));
        ui.showCompletionStats(1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Here are some completion statistics about your task list"));
        ui.showUnFinishedTasks(tasks.getList());
        assertTrue(outContent.toString().contains("Here are the unfinished tasks in your list"));
        ui.showUnFinishedTasks(emptyList);
        assertTrue(outContent.toString().contains("There are no unfinished tasks in your list"));
        ui.showHelp();
        assertTrue(outContent.toString().contains("All command will be display as "));
        ui.showLanguage("English");
        assertTrue(outContent.toString().contains("English"));
        ui.showEditChooseTask();
        assertTrue(outContent.toString().contains("Please choose the task to edit from the list by its index"));
        ui.showEdit2Choice();
        assertTrue(outContent.toString().contains("Please choose what you want to edit (1 or 2)"));
        ui.showEditWhat("UiEnTest");
        assertTrue(outContent.toString().contains("Please enter the new UiEnTest"));
        ui.showEdit(task);
        assertTrue(outContent.toString().contains("The task is edited:"));
        ui.showAskShortcut("UiEnTest");
        assertTrue(outContent.toString().contains("Please enter a shortcut for UiEnTest"));
        ui.showAskAllShortcut("UiEnTest", "UiEnTest");
        assertTrue(outContent.toString().contains("The precedent shortcut for UiEnTest is UiEnTest please enter new shortcut"));
        ui.showOneShortcutSet("UiEnTest");
        assertTrue(outContent.toString().contains("The shortcut for UiEnTest"));
        ui.showAllShortcutSet();
        assertTrue(outContent.toString().contains("All shortcut has been set"));
        ui.showEnterDayShow();
        assertTrue(outContent.toString().contains("You have enter the show day mode."));
        ui.showEnterDayOfWeekShow();
        assertTrue(outContent.toString().contains("You have enter the show day of week mode"));
        ui.showEnterMonthShow();
        assertTrue(outContent.toString().contains("You have enter the show month mode."));
        ui.showEnterYearShow();
        assertTrue(outContent.toString().contains("You have enter the show year mode"));
        ui.showNotCompleteList(tasks.getList(), tasks);
        assertTrue(outContent.toString().contains("Here are the tasks in your list"));
        ui.terminateShortcut();
        assertTrue(outContent.toString().contains("The shortcut edit mode is being terminated earlier"));
        ui.showErrorLanguage();
        assertTrue(outContent.toString().contains("The language that you have chosen is currently not available"));
        ui.showError(new DateComparisonEventException());
        assertTrue(outContent.toString().contains("The second date should not be before the first one."));
        ui.showError(new DuplicationShortcutException());
        assertTrue(outContent.toString().contains("The shortcut already exists"));
        ui.showError(new EmptyArgumentException());
        assertTrue(outContent.toString().contains("There should have an argument"));
        ui.showError(new EmptyEventDateException());
        assertTrue(outContent.toString().contains("Please enter a period for the event task"));
        ui.showError(new EmptyEventException());
        assertTrue(outContent.toString().contains("The description of a event task cannot be empty"));
        ui.showError(new EmptyHomeworkDateException());
        assertTrue(outContent.toString().contains("Please enter a deadline for the task"));
        ui.showError(new EmptyHomeworkException());
        assertTrue(outContent.toString().contains("The description of a homework task cannot be empty"));
        ui.showError(new EmptyTodoException());
        assertTrue(outContent.toString().contains("The description of a todo cannot be empty"));
        ui.showError(new EventTypeException());
        assertTrue(outContent.toString().contains("The task should be a event task"));
        ui.showError(new FileException());
        assertTrue(outContent.toString().contains("File doesn't exist or cannot be created or cannot be opened"));
        ui.showError(new HomeworkTypeException());
        assertTrue(outContent.toString().contains("The task should be a homework task"));
        ui.showError(new MeaninglessException());
        assertTrue(outContent.toString().contains("I'm sorry, but I don't know what that means"));
        ui.showError(new NonExistentDateException());
        assertTrue(outContent.toString().contains("The date doesn't exist"));
        ui.showError(new NonExistentTaskException());
        assertTrue(outContent.toString().contains("The task doesn't exist"));
        ui.showError(new PostponeHomeworkException());
        assertTrue(outContent.toString().contains("The new homework should not be before the old one"));
        ui.showError(new PrioritizeFormatException());
        assertTrue(outContent.toString().contains("Please respect the prioritize command format"));
        ui.showError(new PrioritizeLimitException());
        assertTrue(outContent.toString().contains("The priority of a task should be an int greater than or equal to  0"));
        ui.showError(new WrongParameterException());
        assertTrue(outContent.toString().contains("The parameters are wrong"));
        ui.showError(new EventDateException());
        assertTrue(outContent.toString().contains("The start date should be before the end date for an event task"));
        ui.showError(new EditFormatException());
        assertTrue(outContent.toString().contains("Please respect the edit command format"));
        ui.showError(new UserAnswerException());
        assertTrue(outContent.toString().contains("Please answer correctly the question"));
        ui.showError(new InvalidFlagException());
        assertTrue(outContent.toString().contains("Invalid Flag"));
        ui.showError(new RecurrenceException());
        assertTrue(outContent.toString().contains("Please respect the recurrence format"));
        ui.showError(new RecurrenceDateException());
        assertTrue(outContent.toString().contains("You are trying to make the event recurrent but there will"));
        ui.showError(new ConflictDateException(emptyList));
        assertTrue(outContent.toString().contains("Here are the tasks in your list"));
    }


    @AfterAll
    public static void AfterUiEnExecuteTest() {
        tasks.getList().removeAll(tasks.getList());
        System.setOut(originalOut);
    }

}
