import duchess.model.AcademicContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcademicContextTest implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                    .withResolverStyle(ResolverStyle.STRICT);

            return LocalDateTime.parse((String) source, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert", e);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"29/12/2018 0000:AY2018/2019, Winter Break, 3 weeks left to the next semester",
            "10/12/2018 0000:AY2018/2019, Winter Break, 5 weeks left to the next semester",
            "17/12/2018 0000:AY2018/2019, Winter Break, 4 weeks left to the next semester",
            "24/12/2018 0000:AY2018/2019, Winter Break, 3 weeks left to the next semester",
            "01/01/2019 0000:AY2018/2019, Winter Break, 2 weeks left to the next semester",
            "07/01/2019 2359:AY2018/2019, Winter Break, last week of break before a new semester starts", //
            "14/01/2019 2359:AY2018/2019, Semester 2, W1",
            "05/08/2019 0000:AY2018/2019, Summer Break, last week of break before a new semester starts", //
            "12/08/2019 2359:AY2019/2020, Semester 1, W1",
            "18/08/2019 2359:AY2019/2020, Semester 1, W1",
            "19/08/2019 2359:AY2019/2020, Semester 1, W2",
            "25/08/2019 2359:AY2019/2020, Semester 1, W2",
            "06/10/2019 2359:AY2019/2020, Semester 1, W7",
            "07/10/2019 2359:AY2019/2020, Semester 1, W8",
            "13/10/2019 2359:AY2019/2020, Semester 1, W8",
            "29/09/2019 0000:AY2019/2020, Semester 1, Recess Week",
            "20/11/2019 0000:AY2019/2020, Semester 1, Reading Week",
            "05/12/2019 0000:AY2019/2020, Semester 1, Examinations",
            "25/02/2020 0000:AY2019/2020, Semester 2, Recess Week",
            "21/04/2020 0000:AY2019/2020, Semester 2, Reading Week",
            "05/05/2020 2359:AY2019/2020, Semester 2, Examinations",
            "21/09/2020 0000:AY2020/2021, Semester 1, Recess Week",
            "19/11/2020 0000:AY2020/2021, Semester 1, Reading Week",
            "03/12/2020 2359:AY2020/2021, Semester 1, Examinations",
            "25/02/2021 0000:AY2020/2021, Semester 2, Recess Week",
            "21/04/2021 0000:AY2020/2021, Semester 2, Reading Week",
            "05/05/2021 2359:AY2020/2021, Semester 2, Examinations"},
            delimiter = ':')
    void academicContext_processedCorrectly(@ConvertWith(AcademicContextTest.class) LocalDateTime localDateTime,
                                            String expected) {
        AcademicContext ac = new AcademicContext(localDateTime);
        String academicContext = ac.getAcademicContext();
        assertEquals(expected, academicContext);
    }
}
