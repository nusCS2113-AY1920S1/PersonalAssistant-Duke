import chronologer.command.Command;
import chronologer.command.ExportCommand;
import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.util.MapTimeZoneCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;

class ExportTest {

    private static ArrayList<Task> list;
    private static TaskList tasks;
    private static File file;
    private static File calendarFile;
    private static Storage storage;

    /**
     * Setups the necessary base to carry out the test operations.
     */
    @BeforeAll
    static void setup() {
        list = new ArrayList<>();
        tasks = new TaskList(list);
        file = new File(System.getProperty("user.dir") + "/src/test/Test");

        storage = new Storage(file);
        LocalDateTime startDate = LocalDateTime.now().plusDays(3);
        Deadline deadline = new Deadline("Test", startDate);
        deadline.setComment("Testing description");
        tasks.add(deadline);
    }

    private java.util.Calendar convertToCalendar(LocalDateTime startDate) {
        java.util.Calendar utilCalendar = new GregorianCalendar();
        utilCalendar.set(java.util.Calendar.YEAR, startDate.getYear());
        utilCalendar.set(java.util.Calendar.MONTH, startDate.getMonthValue() - 1);
        utilCalendar.set(java.util.Calendar.DAY_OF_MONTH, startDate.getDayOfMonth());
        utilCalendar.set(java.util.Calendar.HOUR_OF_DAY, startDate.getHour());
        utilCalendar.set(java.util.Calendar.MINUTE, startDate.getMinute());
        utilCalendar.set(java.util.Calendar.SECOND, 0);
        return utilCalendar;
    }

    @Test
    void testExport() throws ChronologerException, IOException, ParserException, ParseException {
        Command export = new ExportCommand("ExportTest", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
        export.execute(tasks, storage);

        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
        calendarFile = new File(System.getProperty("user.dir") + "/src/ChronologerDatabase/ExportTest.ics");
        FileInputStream inputStream = new FileInputStream(calendarFile);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(inputStream);

        Component component = calendar.getComponents().get(0);
        Description deadlineDescription = component.getProperty(Property.DESCRIPTION);
        Assertions.assertEquals(deadlineDescription.getValue(), "Testing description");

        Summary deadlineSummary = component.getProperty(Property.SUMMARY);
        Assertions.assertEquals(deadlineSummary.getValue(), "Test");

        DtEnd deadlineDate = component.getProperty(Property.DTEND);
        java.util.Calendar deadlineCalendar = convertToCalendar(LocalDateTime.now().plusDays(3));
        DateTime testDeadlineDate = new DateTime(deadlineCalendar.getTime());
        DateTime deadlineConverted = new DateTime(String.valueOf(deadlineDate.getValue()));
        Assertions.assertEquals(deadlineConverted, testDeadlineDate);

        inputStream.close();
        calendarFile.delete();
        file.delete();
    }

}
