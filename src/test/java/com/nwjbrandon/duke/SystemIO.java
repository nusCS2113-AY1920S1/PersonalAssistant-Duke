package com.nwjbrandon.duke;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

// https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
public class SystemIO implements AfterEachCallback, BeforeEachCallback {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    public void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    public String getOutput() {
        return testOut.toString();
    }

    @Override
    public void  afterEach(ExtensionContext context)  {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

}
