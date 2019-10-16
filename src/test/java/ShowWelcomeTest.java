import gazeeebo.UI.Ui;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowWelcomeTest {
    @Test
    public void test() throws IOException {
        Ui ui = new Ui();
        String logo = " ___   ___  ___  ___  ___  ___  ___   ___ \n"
                + "|     |   |   / |    |    |    |   \\ |   |\n"
                + "|  __ |__ |  /  |___ |___ |___ |___| |   |\n"
                + "|___| |   | /__ |___ |___ |___ |___/ |___|";
        ByteArrayInputStream in = new ByteArrayInputStream("jjjjjj".getBytes());
        System.setIn(in);
        ByteArrayInputStream second = new ByteArrayInputStream("jjjry".getBytes());
        System.setIn(second);
        assertEquals("\nWelcome to Gazeebo"
                + "\n__________________________________________\n"
                + logo
                + "\n__________________________________________\n",ui.showWelcome());
    }
}
