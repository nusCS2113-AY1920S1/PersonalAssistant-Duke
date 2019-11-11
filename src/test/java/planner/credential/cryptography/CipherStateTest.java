//@@author LongLeCE

package planner.credential.cryptography;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CipherStateTest {

    private CipherState cipherState;

    private void testNextMethod(byte[] testMessage, byte[] testKey) {
        assertSame(cipherState.next(false), testMessage);
        assertSame(cipherState.next(true), testKey);
    }

    @DisplayName("CipherState Initialization Test")
    @Test
    public void constructorsShouldCorrectlyInitializeFields() {
        cipherState = new CipherState();
        assertNull(cipherState.getMessage());
        assertNull(cipherState.getKey());

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        assertArrayEquals(testMessage, cipherState.getMessage());
        assertNull(cipherState.getKey());

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        assertArrayEquals(testMessage, cipherState.getMessage());
        assertArrayEquals(testKey, cipherState.getKey());
    }

    @DisplayName("CipherState String Representation Test")
    @Test
    public void toStringShouldBeCorrect() {
        HashMap<String, String> stateMap = new HashMap<>();

        cipherState = new CipherState();
        stateMap.put("message", "");
        stateMap.put("privateKey", "");
        assertEquals(cipherState.toString(), stateMap.toString());

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        stateMap.put("message", testMessageString);
        stateMap.put("privateKey", "");
        assertEquals(cipherState.toString(), stateMap.toString());

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        stateMap.put("message", testMessageString);
        stateMap.put("privateKey", testKeyString);
        assertEquals(cipherState.toString(), stateMap.toString());
    }

    @DisplayName("CipherState next Test")
    @Test
    public void nextShouldReturnCorrectField() {
        cipherState = new CipherState();
        testNextMethod(null, null);

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        testNextMethod(testMessage, null);

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        testNextMethod(testMessage, testKey);
    }

    @DisplayName("CipherState getMessage Test")
    @Test
    public void getMessageShouldReturnCorrectMessage() {
        cipherState = new CipherState();
        assertNull(cipherState.getMessage());

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        assertSame(testMessage, cipherState.getMessage());

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        assertSame(testMessage, cipherState.getMessage());
    }

    @DisplayName("CipherState getKey Test")
    @Test
    public void getKeyShouldReturnCorrectKey() {
        cipherState = new CipherState();
        assertNull(cipherState.getKey());

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        assertNull(cipherState.getKey());

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        assertSame(testMessage, cipherState.getMessage());
        assertSame(testKey, cipherState.getKey());
    }
}
