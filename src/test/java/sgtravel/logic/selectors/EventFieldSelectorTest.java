package sgtravel.logic.selectors;

import javafx.scene.input.KeyCode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventFieldSelectorTest {

    @Test
    void getIndex() {
        EventFieldSelector selector = new EventFieldSelector();
        assertEquals(selector.getIndex(), 0);
    }

    @Test
    void feedKeyCode() {
        EventFieldSelector selector = new EventFieldSelector();
        selector.feedKeyCode(KeyCode.UP);
        assertEquals(selector.getIndex(), 2);
        selector.feedKeyCode(KeyCode.DOWN);
        assertEquals(selector.getIndex(), 0);
        selector.feedKeyCode(KeyCode.DOWN);
        assertEquals(selector.getIndex(), 1);
        selector.feedKeyCode(KeyCode.UP);
        assertEquals(selector.getIndex(), 0);
    }
}
