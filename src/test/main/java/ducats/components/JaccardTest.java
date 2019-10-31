package main.java.ducats.components;

import junit.framework.TestCase;
import ducats.components.WordGetter;
public class JaccardTest extends TestCase {
    private int x = 1;
    private int y = 1;

    public void testAddition() {
        int z = x + y;
        WordGetter tester = new ducats.components.WordGetter();
        assertEquals("help", tester.closestWord("helo"));
    }

}