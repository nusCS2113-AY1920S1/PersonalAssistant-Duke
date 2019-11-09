package dolla.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yetong1895
public class ShortcutListTest {
    private ArrayList<Record> shortcutListTest = new ArrayList<>();

    @Test
    public void addToShortcutListTest() {
        shortcutListTest.add(new Shortcut("income",255, "shortcut testing 1"));
        assertEquals(1,shortcutListTest.size());
        shortcutListTest.add(new Shortcut("expense",123, "shortcut testing 2"));
        assertEquals(2,shortcutListTest.size());
        shortcutListTest.add(new Shortcut("income",556, "shortcut testing 3"));
        assertEquals(3,shortcutListTest.size());
        shortcutListTest.add(new Shortcut("expense",12, "shortcut testing 4"));
        assertEquals(4,shortcutListTest.size());
    }

    @Test
    public void removeFromShortcutListTest() {
        addToShortcutListTest();
        shortcutListTest.remove(3);
        assertEquals(3,shortcutListTest.size());
        shortcutListTest.remove(2);
        assertEquals(2,shortcutListTest.size());
        shortcutListTest.remove(1);
        assertEquals(1,shortcutListTest.size());
        shortcutListTest.remove(0);
        assertEquals(0,shortcutListTest.size());
    }


}
