package duchess.model.calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarUtilTest implements ArgumentConverter {
    private List<LocalDate> invalidDates;
    private List<LocalDate> semOne;
    private List<LocalDate> semTwo;
    private List<LocalDate> weekDates;
    private List<Integer> expectedCalculation;

    @BeforeEach
    private void initialise() {
        invalidDates = new ArrayList<>();
        semOne = new ArrayList<>();
        semTwo = new ArrayList<>();
        weekDates = new ArrayList<>();
        expectedCalculation = new ArrayList<>();
        LocalDate startOfAY20192020 = LocalDate.of(2019, Month.AUGUST, 12);
        LocalDate semOneEnd = LocalDate.of(2019, Month.DECEMBER, 8);
        for (LocalDate date = LocalDate.of(2018, Month.JANUARY, 1)
             ; date.isBefore(startOfAY20192020)
                ; date = date.plusDays(1)) {
            invalidDates.add(date);
        }
        for (LocalDate date = startOfAY20192020; date.compareTo(semOneEnd) <= 0; date = date.plusDays(1)) {
            semOne.add(date);
        }
        LocalDate semTwoStart = LocalDate.of(2020, Month.JANUARY, 13);
        LocalDate semTwoEnd = LocalDate.of(2020, Month.MAY, 10);
        for (LocalDate date = semOneEnd.plusDays(1); date.isBefore(semTwoStart); date = date.plusDays(1)) {
            invalidDates.add(date);
        }
        for (LocalDate date = semTwoStart; date.compareTo(semTwoEnd) <= 0; date = date.plusDays(1)) {
            semTwo.add(date);
        }
        for (int i = 1; i < 6; i++) {
            invalidDates.add(semTwoEnd.plusDays(i));
        }
        int count = 1;
        for (LocalDate date = startOfAY20192020; date.compareTo(semOneEnd) <= 0; date = date.plusWeeks(1)) {
            weekDates.add(date);
            weekDates.add(date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
            expectedCalculation.add(count);
            count++;
        }
        count = 1;
        for (LocalDate date = semTwoStart; date.compareTo(semTwoEnd) <= 0; date = date.plusWeeks(1)) {
            weekDates.add(date);
            weekDates.add(date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
            expectedCalculation.add(count);
            count++;
        }
    }

    @Test
    public void processDate_datesOutsideOfCurrentFormalAcademicYear_returnsMinusOne() {
        for (LocalDate date : invalidDates) {
            int result = CalendarUtil.processDate(date);
            assertEquals(result, -1);
        }
    }

    @Test
    public void processDate_datesWithinSemesterOneOfCurrentFormalAcademicYear_returnsPositiveOne() {
        for (LocalDate date : semOne) {
            int result = CalendarUtil.processDate(date);
            assertEquals(result, 1);
        }
    }

    @Test
    public void processDate_datesWithinSemesterTwoOfCurrentFormalAcademicYear_returnsPositiveTwo() {
        for (LocalDate date : semTwo) {
            int result = CalendarUtil.processDate(date);
            assertEquals(result, 2);
        }
    }

    @Test
    public void getWeekAsInt_datesWithinFormalSemestersInCurrentAcademicYear_returnsCorrectWeekNumber() {
        int counter = 0;
        for (int expected : expectedCalculation) {
            int result = CalendarUtil.getWeekAsInt(weekDates.get(counter));
            assertEquals(result, expected);
            counter++;
            result = CalendarUtil.getWeekAsInt(weekDates.get(counter));
            assertEquals(result, expected);
            counter++;
        }
    }

    @AfterEach
    private void clearLists() {
        invalidDates.clear();
        semOne.clear();
        semTwo.clear();
        weekDates.clear();
        expectedCalculation.clear();
    }

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse((String) source, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert", e);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "12/08/2019:AY2019/2020, Semester 1, W1",
            "18/08/2019:AY2019/2020, Semester 1, W1",
            "19/08/2019:AY2019/2020, Semester 1, W2",
            "25/08/2019:AY2019/2020, Semester 1, W2",
            "06/10/2019:AY2019/2020, Semester 1, W7",
            "07/10/2019:AY2019/2020, Semester 1, W8",
            "13/10/2019:AY2019/2020, Semester 1, W8",
            "29/09/2019:AY2019/2020, Semester 1, Recess Week",
            "20/11/2019:AY2019/2020, Semester 1, Reading Week",
            "05/12/2019:AY2019/2020, Semester 1, Examinations",
            "25/02/2020:AY2019/2020, Semester 2, Recess Week",
            "21/04/2020:AY2019/2020, Semester 2, Reading Week",
            "05/05/2020:AY2019/2020, Semester 2, Examinations",},
            delimiter = ':')
    public void toString_validDateInput_givesCorrectContext(@ConvertWith(CalendarUtilTest.class) LocalDate localDate,
                                                            String expected) {
        String output = CalendarUtil.toString(localDate);
        assertEquals(output, expected);
    }
}