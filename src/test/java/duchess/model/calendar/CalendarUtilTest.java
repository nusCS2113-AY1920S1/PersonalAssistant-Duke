package duchess.model.calendar;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarUtilTest implements ArgumentConverter {

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
    void toString_validDateInput_givesCorrectContext(@ConvertWith(CalendarUtilTest.class) LocalDate localDate,
                                                     String expected) {
        String output = CalendarUtil.toString(localDate);
        assertEquals(output, expected);
    }
}
