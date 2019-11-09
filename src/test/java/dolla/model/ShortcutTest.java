package dolla.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortcutTest {
    private Shortcut createShortcut() {
        return new Shortcut("income",223,"shortcut testing");
    }

    @Test
    public void amountToMoney() {
        Shortcut shortcutTest = createShortcut();
        assertEquals("$223.0", shortcutTest.amountToMoney());
    }

    @Test
    public void getLogText() {
        Shortcut shortcutTest = createShortcut();
        assertEquals("[shortcut] [income] [$223.0] [shortcut testing]",
                shortcutTest.getRecordDetail());
    }

    @Test
    public void formatSave() {
        Shortcut shortcutTest = createShortcut();
        assertEquals("shortcut | income | 223.0 | shortcut testing", shortcutTest.formatSave());
    }

    @Test
    public void getDescription() {
        Shortcut shortcutTest = createShortcut();
        assertEquals("shortcut testing", shortcutTest.getDescription());
    }

    @Test
    public void getUserInput() {
        Shortcut shortcutTest = createShortcut();
        assertEquals("income 223.0 shortcut testing", shortcutTest.getUserInput());
    }
}
