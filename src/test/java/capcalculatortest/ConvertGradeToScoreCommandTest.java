//@@author JasonLeeWeiHern

package capcalculatortest;

import gazeeebo.commands.capcalculator.ConvertGradeToScoreCommand;

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
    void testConvertAplustoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(5.0, test.converter("A+"));
    }

    @Test
    void testConvertAtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(5.0, test.converter("A"));
    }

    @Test
    void testConvertAminustoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(4.5, test.converter("A-"));
    }

    @Test
    void testConvertBplustoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(4.0, test.converter("B+"));
    }

    @Test
    void testConvertBtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(3.5, test.converter("B"));
    }

    @Test
    void testConvertBminustoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(3.0, test.converter("B-"));
    }

    @Test
    void testConvertCplustoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(2.5, test.converter("C+"));
    }

    @Test
    void testConvertCtoScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(2.0, test.converter("C"));
    }

    @Test
    void testConvertDplustoScoreCommand() {
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
    void testConvertToOtherScoreCommand() {
        ConvertGradeToScoreCommand test = new ConvertGradeToScoreCommand();
        assertEquals(0.1, test.converter("CS"));
    }

}
