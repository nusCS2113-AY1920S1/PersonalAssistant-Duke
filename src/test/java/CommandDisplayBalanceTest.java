import executor.command.Command;
import executor.command.CommandDisplayBalance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Wallet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandDisplayBalanceTest {

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private PrintStream originalOut;
    private PrintStream originalErr;

    private void resetTextTracker() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    private void endTextTracker() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void executeTest() {
        resetTextTracker();
        assertEquals("", outContent.toString().trim());

        resetTextTracker();
        Wallet wallet = new Wallet();
        Command c = new CommandDisplayBalance("");
        c.execute(wallet);
        assertEquals("Your Balance: $0.00", outContent.toString().trim());

        resetTextTracker();
        wallet.setBalance(500.0);
        c.execute(wallet);
        assertEquals("Your Balance: $500.00", outContent.toString().trim());

        endTextTracker();
    }
}
