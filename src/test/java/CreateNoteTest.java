import javacake.JavaCake;
import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import org.junit.jupiter.api.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateNoteTest {

    private Ui ui;
    private StorageManager sm;
    private Logic logic = Logic.getInstance();
    private JavaCake javaCake;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void processCreateNoteCommandTest() {

        assertThrows(NullPointerException.class, () -> {
            System.out.println(javaCake.getResponse("createnote ../hi"));
        });

    }

}
