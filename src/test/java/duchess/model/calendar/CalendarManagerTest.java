package duchess.model.calendar;

import duchess.exceptions.DuchessException;
import duchess.model.task.Event;
import duchess.model.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalendarManagerTest {
    private List<CalendarEntry> duchessCalendar;

    @BeforeEach
    private void initialise() throws DuchessException {
        duchessCalendar = new ArrayList<>();
        LocalDate startOfAY20192020 = LocalDate.of(2019, Month.AUGUST, 12);
        LocalDate semOneEnd = LocalDate.of(2019, Month.DECEMBER, 8);
        for (LocalDate date = startOfAY20192020; date.compareTo(semOneEnd) <= 0; date = date.plusDays(1)) {
            List<Task> taskList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                LocalDateTime start = LocalDateTime.of(date, LocalTime.of(i, 12));
                LocalDateTime end = LocalDateTime.of(date, LocalTime.of(i, 14));
                Event event = new Event("Test", end, start);
                taskList.add(event);
            }
            CalendarEntry ce = new CalendarEntry(date, taskList);
            duchessCalendar.add(ce);
        }
        LocalDate semTwoStart = LocalDate.of(2020, Month.JANUARY, 13);
        LocalDate semTwoEnd = LocalDate.of(2020, Month.MAY, 10);
        for (LocalDate date = semTwoStart; date.compareTo(semTwoEnd) <= 0; date = date.plusDays(1)) {
            List<Task> taskList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                LocalDateTime start = LocalDateTime.of(date, LocalTime.of(i, 12));
                LocalDateTime end = LocalDateTime.of(date, LocalTime.of(i, 14));
                Event event = new Event("Test", end, start);
                taskList.add(event);
            }
            CalendarEntry ce = new CalendarEntry(date, taskList);
            duchessCalendar.add(ce);
        }
    }

    @Test
    public void addEntry_givenLocalDateAndTaskList_AddsToCalendar() throws DuchessException {
        LocalDate date = LocalDate.of(2019, Month.AUGUST, 18);
        CalendarEntry beforeCE = Objects.requireNonNull(duchessCalendar
                .stream()
                .filter(e -> e.getDate().equals(date))
                .findAny()
                .orElse(null));
        assertNotNull(beforeCE);
        List<Task> beforeTaskList = beforeCE.getDateTasks();
        int before = beforeTaskList.size();
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(12, 12));
        LocalDateTime end = LocalDateTime.of(date, LocalTime.of(12, 14));
        Event event = new Event("Test", end, start);
        CalendarManager.addEntry(duchessCalendar, event, start.toLocalDate());
        CalendarEntry afterCE = Objects.requireNonNull(duchessCalendar
                .stream()
                .filter(e -> e.getDate().equals(date))
                .findAny()
                .orElse(null));
        assertNotNull(afterCE);
        List<Task> afterTaskList = afterCE.getDateTasks();
        int after = afterTaskList.size();
        int difference = after - before;
        assertEquals(1, difference);
    }

    @AfterEach
    private void reset() {
        duchessCalendar.clear();
    }
}