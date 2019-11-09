package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.commands.ViewCommand;

import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserViewCommandIntegrationTest {
    @Test
    void testParser_viewCommand_toMainPages() throws SpinBoxException {
        ArrayDeque<String> pageTrace = new ArrayDeque<>();

        ModuleContainer testContainer = new ModuleContainer();
        Ui ui = new Ui(true);

        pageTrace.add("main");

        ArrayDeque<String> checkTrace = new ArrayDeque<>();

        String toCalendar = "view / calendar";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(toCalendar);
        command.execute(testContainer, pageTrace, ui, false);
        checkTrace.add("calendar");

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        String toModules = "view / modules";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModules);
        command.execute(testContainer, pageTrace, ui, false);
        checkTrace.clear();
        checkTrace.add("modules");

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        String toMain = "view / main";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toMain);
        command.execute(testContainer, pageTrace, ui, false);
        checkTrace.clear();
        checkTrace.add("main");

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));
    }

    @Test
    void testParser_viewCommand_toModule() throws SpinBoxException {
        ArrayDeque<String> pageTrace = new ArrayDeque<>();

        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);
        Ui ui = new Ui(true);

        pageTrace.add("main");

        ArrayDeque<String> checkTrace = new ArrayDeque<>();

        String toModule = "view / TESTMOD";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);
        checkTrace.addFirst("modules");
        checkTrace.addFirst("TESTMOD");
        checkTrace.addFirst("tasks");

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("calendar");

        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("modules");

        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.add("TESTMOD");

        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    void testParser_viewCommand_toModuleTasks() throws SpinBoxException {
        ArrayDeque<String> pageTrace = new ArrayDeque<>();

        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("TESTMOD", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        pageTrace.add("main");

        ArrayDeque<String> checkTrace = new ArrayDeque<>();
        checkTrace.addFirst("modules");
        checkTrace.addFirst("TESTMOD");
        checkTrace.addFirst("tasks");

        Ui ui = new Ui(true);

        String toModulesThenTasks = "view / modules TESTMOD tasks";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(toModulesThenTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("main");

        String toModuleTasks = "view / TESTMOD tasks";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModuleTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("calendar");

        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModuleTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("modules");

        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModuleTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.addFirst("modules");
        pageTrace.addFirst("TESTMOD");

        String toTasks = "view / tasks";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toTasks);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    void testParser_viewCommand_toModuleCaseInsensitive() throws SpinBoxException {
        ArrayDeque<String> pageTrace = new ArrayDeque<>();

        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("TESTMOD", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        pageTrace.add("main");

        ArrayDeque<String> checkTrace = new ArrayDeque<>();
        checkTrace.addFirst("modules");
        checkTrace.addFirst("TESTMOD");
        checkTrace.addFirst("tasks");

        Ui ui = new Ui(true);

        String toModule = "view / testmod";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));

        pageTrace.clear();
        pageTrace.add("main");

        toModule = "view / TESTMOD";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(toModule);
        command.execute(testContainer, pageTrace, ui, false);

        assertTrue(Arrays.equals(checkTrace.toArray(), pageTrace.toArray()));
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }
}
