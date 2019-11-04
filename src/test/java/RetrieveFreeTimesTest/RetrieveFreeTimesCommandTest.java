package RetrieveFreeTimesTest;

import Commands.Command;
import Commons.LookupTable;
import Commons.Ui;
import DukeExceptions.DukeInvalidFormatException;
import Parser.RetrieveFreeTimesParse;
import StubClasses.StorageStub;
import Tasks.TaskList;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author hwbjerry
/**
 * This class tests RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesCommandTest {

    private static String userInputWithValidOption;
    private static Integer userInputSelectedOption;

    private static ArrayList<Pair<String, String>> retrievedFreeTimesList;

    private LookupTable lookupTable = new LookupTable();
    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private Ui ui = new Ui();

    @BeforeAll
    public static void setAllVariables() {
        userInputWithValidOption = "retrieve/ft 3";
        userInputSelectedOption = 3;
        retrievedFreeTimesList = new ArrayList<>();
    }

    @Test
    public void retrieveFreeTimesCommandWithEmptyList() {
        String expected = ui.showSelectionOptionEmptyList();
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithValidOption).parse();
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Before
    public void setRetrievedFreeTimesList() {
        String firstPairKey = "Thu 07/11/2019 02:30 PM until 07:30 PM";
        String secondPairKey = "Tue 26/11/2019 10:30 AM until 03:30 PM";
        String thirdPairKey = "Wed 06/11/2019 02:30 PM until 07:30 PM";
        String fourthPairKey = "Wed 06/11/2019 03:30 PM until 08:30 PM";
        String fifthPairKey = "Wed 06/11/2019 04:30 PM until 09:30 PM";

        String firstPairValue = "/at 07/11/2019 /from 1430 /to 1930";
        String secondPairValue = "/at 26/11/2019 /from 1030 /to 1530";
        String thirdPairValue = "/at 06/11/2019 /from 1430 /to 1930";
        String fourthPairValue = "/at 06/11/2019 /from 1530 /to 2030";
        String fifthPairValue = "/at 06/11/2019 /from 1630 /to 2130";

        retrievedFreeTimesList.add(new Pair<>(firstPairKey, firstPairValue));
        retrievedFreeTimesList.add(new Pair<>(secondPairKey, secondPairValue));
        retrievedFreeTimesList.add(new Pair<>(thirdPairKey, thirdPairValue));
        retrievedFreeTimesList.add(new Pair<>(fourthPairKey, fourthPairValue));
        retrievedFreeTimesList.add(new Pair<>(fifthPairKey, fifthPairValue));
    }

    @Test
    public void retrieveFreeTimesCommandWithPopulatedList() {
        String expected = ui.showSelectionOption(userInputSelectedOption, retrievedFreeTimesList.get(userInputSelectedOption-1).getKey());
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithValidOption).parse();
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @After
    public void clearParameter(){
        retrievedFreeTimesList.clear();
    }

}
