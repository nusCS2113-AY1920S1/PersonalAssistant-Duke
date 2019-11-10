package main.java.ducats.components;

import junit.framework.TestCase;
import ducats.components.WordGetter;
public class JaccardTestFail extends TestCase {
    private int x = 1;
    private int y = 1;

    public void testAddition() {
        int z = x + y;
        WordGetter tester = new ducats.components.WordGetter();
        assertEquals("sasaff", tester.closestWord("djjsajas"));
    }

}