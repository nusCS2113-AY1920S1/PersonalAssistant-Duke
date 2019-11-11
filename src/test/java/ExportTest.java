import chronologer.command.Command;
import chronologer.command.ExportCommand;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
import chronologer.storage.CalendarOutput;
import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.MapTimeZoneCache;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;


class ExportTest {

    private static final String PROD_ID = "-//Chronologer//iCal4j Test 1.2//EN";
    private static String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/Christmas.ics";

    private Calendar initializeCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(PROD_ID));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
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
    void testError() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("export");
        });
    }

    @Test
    void testFileCreation() throws ChronologerException {

        java.util.Calendar testCalendar = java.util.Calendar.getInstance();
        testCalendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        testCalendar.set(java.util.Calendar.DAY_OF_MONTH, 25);

        VEvent christmas = new VEvent(new Date(testCalendar.getTime()), "Christmas");

        Calendar calendar = initializeCalendar();
        UidGenerator generator = new RandomUidGenerator();
        christmas.getProperties().add(generator.generateUid());
        calendar.getComponents().add(christmas);

        CalendarOutput.outputCalendar("Christmas", calendar);

        Path path = Paths.get(filePath);
        Assertions.assertTrue(Files.exists(path));

        /*File file = new File(filePath);
        file.delete();*/
    }

    @Test
    void testExportCommand() throws ChronologerException, IOException, ParserException, ParseException {
        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
        ArrayList<Task> list = new ArrayList<>();
        TaskList tasks = new TaskList(list);

        LocalDateTime startDate = LocalDateTime.now().plusDays(3);
        Deadline deadline = new Deadline("Test", startDate);
        deadline.setComment("Testing description");
        tasks.add(deadline);

        Command export = new ExportCommand("ExportTest", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
        export.execute(tasks, null, null);

        File calendarFile = new File(System.getProperty("user.dir") + "/src/ChronologerDatabase/ExportTest.ics");
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
    }

}