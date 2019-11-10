package eggventory.ui;

import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Raghav-B
public class InputTextBoxTest {
    InputTextBox inputTextBox = new InputTextBox(new TextFlow());

    @Test
    public void testRemoveTextBackspace_RemoveFromEmptyInput() {
        assertDoesNotThrow(() -> inputTextBox.removeTextBackspace());
        assertEquals("", inputTextBox.getAllText());
    }

    @Test
    public void testRemoveTextDelete_RemoveFromEmptyInput() {
        assertDoesNotThrow(() -> inputTextBox.removeTextDelete());
        assertEquals("", inputTextBox.getAllText());
    }

    @Test
    public void testAppendText_Success() {
        String fieldText = "test text";
        inputTextBox.appendText(fieldText, 0);
        assertEquals(fieldText, inputTextBox.getAllText());

        String newFieldText = " appended";
        inputTextBox.appendText(newFieldText, 0);
        assertEquals(fieldText + newFieldText, inputTextBox.getAllText());
    }

    @Test
    public void testRemoveTextBackspace_RemoveFromEnd() {
        testAppendText_Success();
        String curText = inputTextBox.getAllText();

        inputTextBox.removeTextBackspace();
        curText = curText.substring(0, curText.length() - 1);
        assertEquals(curText, inputTextBox.getAllText());

        inputTextBox.removeTextBackspace();
        inputTextBox.removeTextBackspace();
        curText = curText.substring(0, curText.length() - 2);
        assertEquals(curText, inputTextBox.getAllText());
    }

    @Test
    public void testRemoveTextDelete_RemoveFromEnd() {
        testAppendText_Success();
        String curText = inputTextBox.getAllText();

        inputTextBox.removeTextDelete();
        assertEquals(curText, inputTextBox.getAllText());
    }

    @Test
    public void testMoveCaret_Left() {
        testAppendText_Success();
        String curText = inputTextBox.getAllText();

        // Move caret left 9 times
        for (int i = 0; i < 9; i++) {
            inputTextBox.moveCaret(-1);
        }

        assertEquals(curText, inputTextBox.getAllText());
    }

    @Test
    public void testMoveCaret_Right() {
        testMoveCaret_Left();
        String curText = inputTextBox.getAllText();

        // Move caret right 20 times
        for (int i = 0; i < 20; i++) {
            inputTextBox.moveCaret(1);
        }

        assertEquals(curText, inputTextBox.getAllText());
    }

    @Test
    public void testMoveCaret_Left_ThenBackspace() {
        testMoveCaret_Left();
        String curText = inputTextBox.getAllText();
        String leftText = curText.substring(0,curText.length() - 9);
        String rightText = curText.substring(curText.length() - 9);

        // Backspace 3 characters
        leftText = leftText.substring(0, leftText.length() - 3);
        for (int i = 0; i < 3; i++) {
            inputTextBox.removeTextBackspace();
        }

        assertEquals(leftText + rightText, inputTextBox.getAllText());
    }

    @Test
    public void testMoveCaret_Left_ThenDelete() {
        testMoveCaret_Left();
        String curText = inputTextBox.getAllText();
        String leftText = curText.substring(0,curText.length() - 9);
        String rightText = curText.substring(curText.length() - 9);

        // Deleting 3 characters
        rightText = rightText.substring(3);
        for (int i = 0; i < 3; i++) {
            inputTextBox.removeTextDelete();
        }

        assertEquals(leftText + rightText, inputTextBox.getAllText());
    }

    @Test
    public void testAcceptSearchText_Success() {
        inputTextBox.appendText("a", 0);
        inputTextBox.acceptSearchText();

        assertEquals("add stock", inputTextBox.getAllText());

        inputTextBox.appendText("t", 0);
        inputTextBox.acceptSearchText();

        assertEquals("add stocktype", inputTextBox.getAllText());
    }

    @Test
    public void testClearAllText() {
        testAppendText_Success();

        inputTextBox.clearAllText();

        assertEquals("", inputTextBox.getAllText());
    }
}
//@@author