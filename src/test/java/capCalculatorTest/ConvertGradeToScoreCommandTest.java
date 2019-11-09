//@@author JasonLeeWeiHern
package capCalculatorTest;

import gazeeebo.commands.capCalculator.ConvertGradeToScoreCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvertGradeToScoreCommandTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void testConvertAPLUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(5.0, test.converter("A+"));
    }

    @Test
    void testConvertAtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(5.0, test.converter("A"));
    }

    @Test
    void testConvertAMINUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(4.5, test.converter("A-"));
    }

    @Test
    void testConvertBPLUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(4.0, test.converter("B+"));
    }

    @Test
    void testConvertBtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(3.5, test.converter("B"));
    }

    @Test
    void testConvertBMINUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(3.0, test.converter("B-"));
    }

    @Test
    void testConvertCPLUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(2.5, test.converter("C+"));
    }

    @Test
    void testConvertCtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(2.0, test.converter("C"));
    }

    @Test
    void testConvertDPLUStoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(1.5, test.converter("D+"));
    }

    @Test
    void testConvertDtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(1.0, test.converter("D"));
    }

    @Test
    void testConvertFtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(0.0, test.converter("F"));
    }

    @Test
    void testConvertOTHERtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(0.1, test.converter("CS"));
    }

}
