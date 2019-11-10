package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddEventCommand;
import duchess.model.calendar.CalendarEntry;
import duchess.model.task.Event;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddEventCommandTest {
    private final Storage storage = new Storage("data.json");
    private final Store store = new Store();
    private final Ui ui = new Ui();
    private List<Event> testCases;
    private List<LocalDate> validDates;

    @BeforeEach
    private void initialiseTestCases() {
        testCases = new ArrayList<>();
        validDates = new ArrayList<>();
        testCases.add(new Event("2019-08-11T12:12", "2019-08-11T12:14")); // invalid
        testCases.add(new Event("2019-08-12T12:12", "2019-08-12T12:14"));
        validDates.add(LocalDate.parse("2019-08-12"));
        testCases.add(new Event("2019-12-08T12:12", "2019-12-08T12:14"));
        validDates.add(LocalDate.parse("2019-12-08"));
        testCases.add(new Event("2019-12-09T12:12", "2019-12-09T12:14")); // invalid
        testCases.add(new Event("2020-01-12T12:12", "2020-01-12T12:14")); // invalid
        testCases.add(new Event("2020-01-13T12:12", "2020-01-13T12:14"));
        validDates.add(LocalDate.parse("2020-01-13"));
        testCases.add(new Event("2020-05-09T12:12", "2020-05-09T12:14"));
        validDates.add(LocalDate.parse("2020-05-09"));
        testCases.add(new Event("2020-05-10T12:12", "2020-05-10T12:14"));
        validDates.add(LocalDate.parse("2020-05-10"));
        testCases.add(new Event("2020-05-11T12:12", "2020-05-11T12:14")); // invalid
    }

    @Test
    public void execute_whenEventIsWithinFormalAcademicYear_eventAddsToDuchessCalendar()
            throws DuchessException {
        for (Event event : testCases) {
            LocalDateTime start = LocalDateTime.parse(event.getStart());
            LocalDateTime end = LocalDateTime.parse(event.getEnd());
            AddEventCommand validCommand = new AddEventCommand("testing", end, start);
            validCommand.execute(store, ui, storage);
        }
        int counter = 0;
        for (CalendarEntry ce : store.getDuchessCalendar()) {
            assertEquals(ce.getDate(), validDates.get(counter));
            counter++;
        }
        assertEquals(5, store.getDuchessCalendar().size());
    }

    @AfterEach
    private void clearTestCases() {
        testCases.clear();
        validDates.clear();
    }
}
