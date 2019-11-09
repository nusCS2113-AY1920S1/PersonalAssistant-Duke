package leduc;

import leduc.command.EventCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EventCommandTest {
    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    /**
     * Represents the before of eventCommandExecuteTest.
     */
    @BeforeAll
    public static void beforeEventCommandExecuteTest(){
        ui = new UiEn();
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/java/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/configTest.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/welcome.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        List<Task> tasksList = new ArrayList<>();
        tasks = new TaskList( tasksList);
        LocalDateTime d1 = null;
        LocalDateTime d2 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        try {
            d1 = LocalDateTime.parse("11/12/2019 20:30".trim(), formatter);
            d2 = LocalDateTime.parse("12/12/2019 20:40".trim(), formatter);
        } catch (Exception e) {
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        Date date1 = new Date(d1);
        Date date2 = new Date(d2);
        tasks.add(new EventsTask("testConflict", date1, date2));
    }

    @Test
    public void eventCommandExecuteTest() {
        EventCommand eventCommand1 = new EventCommand("event");
        try {
            eventCommand1.execute(tasks, ui, storage);
            fail("should throw exception when there is no description");
        } catch (DukeException e) {
            assertTrue(e.print().contains("description"));
        }

        EventCommand eventCommand2 = new EventCommand("event testNoDate");
        try {
            eventCommand2.execute(tasks, ui, storage);
            fail("should throw exception when there is no date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand3 = new EventCommand("event testNoDate /at");
        try {
            eventCommand3.execute(tasks, ui, storage);
            fail("should throw exception when there is no date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand4 = new EventCommand("event testNoDate /at 12/12/2019 20:30");
        try {
            eventCommand4.execute(tasks, ui, storage);
            fail("should throw exception when there is no period");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand5 = new EventCommand("event testNoDate /at 12/12/2019 20:30 12/12/2019 20:40");
        try {
            eventCommand5.execute(tasks, ui, storage);
            fail("should throw exception when there is a problem with the date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand6 = new EventCommand("event testNoDate /at - 12/12/2019 20:30");
        try {
            eventCommand6.execute(tasks, ui, storage);
            fail("should throw exception when there is no date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand7 = new EventCommand("event testNoDate /at 12/12/2019 20:30 - ");
        try {
            eventCommand7.execute(tasks, ui, storage);
            fail("should throw exception when there is no date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand8 = new EventCommand("event testNoDate /at a - b");
        try {
            eventCommand8.execute(tasks, ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (DukeException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand9 = new EventCommand("event testNoDate /at 12-12-2019 20:30 - 12-12-2019 20:40");
        try {
            eventCommand9.execute(tasks, ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (DukeException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand10 = new EventCommand("event testNoDate /at 12/12/2019 2030 - 12/12/2019 20:40");
        try {
            eventCommand10.execute(tasks, ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (DukeException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand11 = new EventCommand("event testConflictDate /at 11/12/2019 20:20 - 11/12/2019 21:00");
        try {
            eventCommand11.execute(tasks, ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand12 = new EventCommand("event testConflictDate /at 11/12/2019 20:20 - 12/12/2019 21:00");
        try {
            eventCommand12.execute(tasks, ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand13 = new EventCommand("event testConflictDate /at 12/12/2019 20:20 - 12/12/2019 21:00");
        try {
            eventCommand13.execute(tasks, ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand14 = new EventCommand("event testConflictDate /at 12/12/2019 20:20 - 12/12/2019 20:30");
        try {
            eventCommand14.execute(tasks, ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (DukeException e) {
            assertTrue(e.print().contains("conflict"));
        }


        EventCommand eventCommand15 = new EventCommand("event e1 /at 12/12/2000 22:22 - 12/12/2000 22:23 prio 6");
        try {
            eventCommand15.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(false);
        }
        assertTrue(tasks.size() == 2);
        assertTrue(tasks.get(1).getPriority() == 6);

        EventCommand eventCommand16 = new EventCommand("event e2 /at 12/12/2001 22:22 - 12/12/2001 22:23 prio 12");
        try {
            eventCommand16.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size() == 2);

        EventCommand eventCommand17 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio Qzeaze");
        try {
            eventCommand17.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size() == 2);

        EventCommand eventCommand18 = new EventCommand("event e3 /at 12/12/1996 22:22 - 12/12/1996 22:23 recu day 3");
        try {
            eventCommand18.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }
        assertTrue(tasks.size() == 6);

        EventCommand eventCommand19 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu");
        try {
            eventCommand19.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand20 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu week");
        try {
            eventCommand20.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand21 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu day");
        try {
            eventCommand21.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand22 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu month");
        try {
            eventCommand22.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand23 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu week a");
        try {
            eventCommand23.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand24 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu day a");
        try {
            eventCommand24.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand25 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu month a");
        try {
            eventCommand25.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand26 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 recu dsqdqsd 3");
        try {
            eventCommand26.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand27 = new EventCommand("event e3 /at 13/12/2002 22:22 - 14/12/2002 22:23 recu day 3");
        try {
            eventCommand27.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }

        EventCommand eventCommand28 = new EventCommand("event e3 /at 13/12/2002 22:22 - 21/12/2002 22:23 recu week 3");
        try {
            eventCommand28.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }

        EventCommand eventCommand29 = new EventCommand("event e3 /at 13/01/2002 22:22 - 21/02/2002 22:23 recu month 3");
        try {
            eventCommand29.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }

        EventCommand eventCommand30 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu");
        try {
            eventCommand30.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand31 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu week");
        try {
            eventCommand31.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand32 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu day");
        try {
            eventCommand32.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand33 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu month");
        try {
            eventCommand33.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand34 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu week a");
        try {
            eventCommand34.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand35 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu day a");
        try {
            eventCommand35.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand36 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu month a");
        try {
            eventCommand36.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand37 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio 6 recu dsqdqsd 3");
        try {
            eventCommand37.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceException);
        }

        EventCommand eventCommand38 = new EventCommand("event e3 /at 13/12/2002 22:22 - 14/12/2002 22:23 prio 6 recu day 3");
        try {
            eventCommand38.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }

        EventCommand eventCommand39 = new EventCommand("event e3 /at 13/12/2002 22:22 - 21/12/2002 22:23 prio 6 recu week 3");
        try {
            eventCommand39.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }

        EventCommand eventCommand40 = new EventCommand("event e3 /at 13/01/2002 22:22 - 21/02/2002 22:23 prio 6 recu month 3");
        try {
            eventCommand40.execute(tasks, ui, storage);
        } catch (DukeException e) {
            assertTrue(e instanceof RecurrenceDateException);
        }
    }

    /**
     * Represents the after of eventCommandExecuteTest.
     */
    @AfterAll
    public static void afterEventCommandExecuteTest(){
        tasks.getList().removeAll(tasks.getList());
        try {
            storage.save(tasks.getList());
        } catch (FileException f) {
            assertTrue(false);
        }
        assertTrue(tasks.size() == 0);
    }

}
