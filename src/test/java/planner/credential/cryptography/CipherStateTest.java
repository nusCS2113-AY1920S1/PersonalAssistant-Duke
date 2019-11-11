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

    private void testNext() {
        assertSame(cipherState.next(false), cipherState.getMessage());
        assertSame(cipherState.next(true), cipherState.getKey());
    }

    @DisplayName("CipherState Initialization Test")
    @Test
    public void cipherStateInitializationShouldCorrectlyInitializeFields() {
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
    public void cipherStateToStringShouldBeCorrect() {
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

    @DisplayName("CipherState Functionality Test")
    @Test
    public void cipherStateNextShouldReturnCorrectField() {
        cipherState = new CipherState();
        testNext();

        String testMessageString = "testMessage";
        byte[] testMessage = testMessageString.getBytes();
        cipherState = new CipherState(testMessage);
        testNext();

        String testKeyString = "testKey";
        byte[] testKey = testKeyString.getBytes();
        cipherState = new CipherState(testMessage, testKey);
        testNext();
    }
}
