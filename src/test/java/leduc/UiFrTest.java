package leduc;

import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UiFrTest {
    private static Ui ui;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeAll
    public static void BeforeUiFrTest(){
        ui = new UiFr();

    }
}
