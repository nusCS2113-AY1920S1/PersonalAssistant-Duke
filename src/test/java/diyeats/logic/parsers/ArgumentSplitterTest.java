package diyeats.logic.parsers;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgumentSplitterTest {

    private final String emptyTestStr = "";
    private final String invalidTestStr = "/date /calorie";
    private final String normalTestStr = "/date 12/12/2012 /calorie 200 /name cheese hamburger /protein 20";

    private HashMap<String,String> argMap;
    private HashMap<String,String> expectedArgMap = new HashMap<>();

    // test empty input string
    @Test
    public void splitForwardSlashArgumentEmptyTest() {
        argMap = ArgumentSplitter.splitForwardSlashArguments(emptyTestStr);
        assertTrue(argMap.isEmpty());
    }

    // test invalid input string
    @Test
    public void splitForwardSlashArgumentMissingTest() {
        argMap = ArgumentSplitter.splitForwardSlashArguments(invalidTestStr);
        assertTrue(argMap.isEmpty());
    }

    // test valid input string and ensure hashmap returned is exactly the same
    @Test
    public void splitForwardSlashArgumentNormalTest() {
        argMap = ArgumentSplitter.splitForwardSlashArguments(normalTestStr);
        expectedArgMap.put("date", "12/12/2012");
        expectedArgMap.put("calorie", "200");
        expectedArgMap.put("name", "cheese hamburger");
        expectedArgMap.put("protein", "20");
        assertEquals(argMap, expectedArgMap);
    }
}
