package ducats.components;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ducats.components.WordGetter;

public class JaccardTestFail  {

    @Test
    public void testAddition() {

        WordGetter tester = new ducats.components.WordGetter();
        assertEquals("sasaff", tester.closestWord("djjsajas"));
    }

}
