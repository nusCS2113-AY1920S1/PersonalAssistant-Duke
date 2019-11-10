package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DoneTaskCommand;
import duchess.model.calendar.CalendarEntry;
import duchess.model.task.Event;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoneTaskCommandTest {
    private final Storage storage = new Storage("data.json");
    private final Store store = new Store();
    private final Ui ui = new Ui();

    @BeforeEach
    private void initialise() throws DuchessException {
        LocalDateTime start = LocalDateTime.parse("2019-10-27T12:12");
        LocalDateTime end = LocalDateTime.parse("2019-10-27T12:14");
        Event event = new Event("description", end, start);
        List<Task> taskList = new ArrayList<>();
        taskList.add(event);
        List<CalendarEntry> ce = List.of(new CalendarEntry(LocalDate.parse("2019-10-27"), taskList));
        store.setDuchessCalendar(ce);
        store.setTaskList(taskList);
    }

    @Test
    public void execute_taskIsCalendarEntry_modifiesDuchessCalendar() throws DuchessException {
        DoneTaskCommand dc = new DoneTaskCommand(0);
        dc.execute(store, ui, storage);
        CalendarEntry ce = store.getDuchessCalendar().get(0);
        Task task = ce.getDateTasks().get(0);
        assertEquals("[E][/] description (at: 27/10/2019 1212 to 27/10/2019 1214)", task.toString());
    }
}
