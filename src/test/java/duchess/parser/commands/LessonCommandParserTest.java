package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddLessonCommand;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LessonCommandParserTest {
    @Test
    void parse() throws DuchessException {
        Map<String, String> parameters1 = Util
                .parameterize("lesson /code CS1231 /type Lecture /time 27/09/2019 1100 "
                        + "/to 27/09/2019 1300");
        assertTrue(
                LessonCommandParser.parse(parameters1) instanceof AddLessonCommand
        );

        Map<String, String> parameters2 = Util
                .parameterize("lesson /type lab /code CG2271 /time 28/09/2019 0800 /to "
                        + "28/09/2019 1000");
        assertTrue(
                LessonCommandParser.parse(parameters2) instanceof AddLessonCommand
        );

        Map<String, String> parameters4 = Util.parameterize("lesson /add something");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters4));

        Map<String, String> parameters5 = Util.parameterize("lesson something");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters5));

        Map<String, String> parameters6 = Util.parameterize("lesson /code something");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters6));

        Map<String, String> parameters7 = Util.parameterize("lesson /code something /type ");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters7));

        Map<String, String> parameters8 = Util.parameterize("lesson /code /type /time");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters8));

        Map<String, String> parameters9 = Util.parameterize("lesson /code /type /time /to");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters9));

        Map<String, String> parameters10 = Util.parameterize("lesson /code /type /time /to /by");
        assertThrows(DuchessException.class, () -> LessonCommandParser.parse(parameters10));

    }
}
