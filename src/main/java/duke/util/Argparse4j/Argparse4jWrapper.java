package duke.util.Argparse4j;

import duke.command.logic.AddCcaCommand;
import duke.command.logic.EndCommand;
import duke.command.logic.ModuleCommand;
import duke.command.logic.RemoveCcaCommand;
import duke.command.logic.RemoveModCommand;
import duke.command.logic.SearchThenAddCommand;
import duke.command.logic.ShowCcaCommand;
import duke.command.logic.ShowModuleCommand;
import duke.exceptions.ModException;
import duke.util.Argparse4j.customParserAction.Join;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

public class Argparse4jWrapper {

    private ArgumentParser parser;
    private Subparsers subParserManager;
    private HashMap<String, Subparser> subParsers;
    private HashMap<String, Class<? extends ModuleCommand>> commandMapper;
    private HashMap<String, List<String>> argumentMapper;
    private HashMap<Class, Class> typeMapper;
    private ArgumentAction joinString;

    /**
     * Constructor for parser.
     */
    public Argparse4jWrapper() {
        this.parser = ArgumentParsers.newFor("ModPlanner")
                .build()
                .defaultHelp(true)
                .description("ModPlanner Argument Parser")
                .version("1.0");
        this.init();
    }

    /**
     * Map CLI command to arguments used in respective ModuleCommand classes.
     */
    // Map arguments here -> this.mapArgument("commandName", "arg1", "arg2", "arg3", ...);
    public void mapBuiltinCommandsArguments() {
        this.mapArgument("add", "moduleCode");
        this.mapArgument("remove", "index");
        this.mapArgument("addCca", "name", "begin", "end", "dayOfWeek");
        this.mapArgument("removeCca", "index");
    }

    /**
     * Map CLI commands to respective ModuleCommand classes.
     */
    // Add new command types here
    public void mapBuiltinCommands() {
        this.mapCommand("add", SearchThenAddCommand.class);
        this.mapCommand("show", ShowModuleCommand.class);
        this.mapCommand("showCca", ShowCcaCommand.class);
        this.mapCommand("bye", EndCommand.class);
        this.mapCommand("remove", RemoveModCommand.class);
        this.mapCommand("addCca", AddCcaCommand.class);
        this.mapCommand("removeCca", RemoveCcaCommand.class);
    }

    /**
     * Add arguments for respective sub-parsers.
     */
    // Add arguments for parsers here
    public void mapBuiltinParserArguments() {
        Subparser addParser = this.getSubParser("add");
        addParser.addArgument("moduleCode")
                .required(true)
                .help("Codename of module to add");

        Subparser removeParser = this.getSubParser("remove");
        removeParser.addArgument("index")
                .type(Integer.class)
                .required(true)
                .help("Index of module to remove");

        Subparser addCcaParser = this.getSubParser("addCca");
        addCcaParser.addArgument("name")
                .required(true)
                .nargs("+")
                .action(this.joinString)
                .help("Name of cca");
        addCcaParser.addArgument("--begin")
                .required(true)
                .nargs("+")
                .action(this.joinString)
                .help("Begin time");
        addCcaParser.addArgument("--end")
                .required(true)
                .nargs("+")
                .action(this.joinString)
                .help("End time");
        addCcaParser.addArgument("--dayOfWeek")
                .required(true)
                .help("Day of week on which cca takes place");

        Subparser removeCcaParser = this.getSubParser("removeCca");
        removeCcaParser.addArgument("index")
                .type(Integer.class)
                .required(true)
                .help("Index of cca to remove");
    }

    // This point onwards is automatic
    public void initTypeMapper() {
        this.typeMapper.put(Integer.class, int.class);
    }

    public void initBuiltinActions() {
        this.joinString = new Join();
    }

    /**
     * Initialize sub-parsers and command mappers.
     */
    public void init() {
        this.subParserManager = this.parser.addSubparsers();
        this.subParsers = new HashMap<>();
        this.argumentMapper = new HashMap<>();
        this.commandMapper = new HashMap<>();
        this.typeMapper = new HashMap<>();
        this.initBuiltinActions();
        this.mapBuiltinCommands();
        this.mapBuiltinCommandsArguments();
        this.initBuiltinParsers();
        this.mapBuiltinParserArguments();
        this.initTypeMapper();
    }

    /**
     * Map CLI commands to sub-parsers.
     */
    public void initBuiltinParsers() {
        for (String command: this.commandMapper.keySet()) {
            this.addParser(command);
        }
    }

    public void mapArgument(String command, String... arguments) {
        this.argumentMapper.put(command, Arrays.asList(arguments));
    }

    public void mapArgument(String command, List<String> arguments) {
        this.argumentMapper.put(command, arguments);
    }

    public void mapCommand(String command, Class<? extends ModuleCommand> type) {
        this.commandMapper.put(command, type);
    }

    /**
     * Add a custom sub-parser.
     * @param name name of sub-parser
     * @param addHelp add help or not
     * @param prefixChars prefix character to distinguish arguments
     * @return added sub-parser
     */
    public Subparser addParser(String name, boolean addHelp, String prefixChars) {
        this.subParsers.put(name,
                this.subParserManager
                .addParser(name, addHelp, prefixChars)
                .setDefault("subParserName", name));
        return this.getSubParser(name);
    }

    public Subparser addParser(String name, boolean addHelp) {
        return this.addParser(name, addHelp, "--");
    }

    public Subparser addParser(String name) {
        return this.addParser(name, true);
    }

    public ArgumentParser getParser() {
        return this.parser;
    }

    public Subparser getSubParser(String subParserName) {
        return this.subParsers.get(subParserName);
    }

    public Argument addArgument(String subParserName, String name) {
        return this.getSubParser(subParserName).addArgument(name);
    }

    /**
     * Parse input.
     * @param args input "words"
     * @return parsed Namespace if input is valid else null
     */
    public Namespace parse(String[] args) {
        try {
            return this.getParser().parseArgs(args);
        } catch (ArgumentParserException ex) {
            this.handleError(ex);
            return null;
        }
    }

    public Namespace parse(String userInput) {
        return this.parse(userInput.split(" +"));
    }

    /**
     * Parse input using a sub-parser.
     * @param args input "words"
     * @return parsed Namespace if input is valid else null
     */
    public Namespace parse(String subParserName, String[] args) {
        try {
            return this.getSubParser(subParserName).parseArgs(args);
        } catch (ArgumentParserException ex) {
            this.handleError(subParserName, ex);
            return null;
        }
    }

    public Namespace parse(String subParserName, String userInput) {
        return this.parse(subParserName, userInput.split(" +"));
    }

    private void handleError(ArgumentParserException ex) {
        this.parser.handleError(ex);
    }

    private void handleError(String subParserName, ArgumentParserException ex) {
        this.getSubParser(subParserName).handleError(ex);
    }

    /**
     * Remap some classes for compatibility.
     * @param classes parsed classes.
     */
    private void remapTypes(Class[] classes) {
        for (int i = 0; i < classes.length; ++i) {
            if (this.typeMapper.containsKey(classes[i])) {
                classes[i] = this.typeMapper.get(classes[i]);
            }
        }
    }

    /**
     * Invoke a module command
     * @param commandClass command class to invoke
     * @param argumentsClasses corresponding classes of arguments
     * @param objects corresponding arguments
     * @return
     */
    private ModuleCommand invokeCommand(Class<? extends ModuleCommand> commandClass,
                                        Class[] argumentsClasses,
                                        Object[] objects) throws ModException {
        try {
            return commandClass.getConstructor(argumentsClasses).newInstance(objects);
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof ModException) {
                throw (ModException) ex.getCause();
            }
            ex.printStackTrace();
            return null;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Parse input to ModuleCommand.
     * @param userInput input String
     * @return parsed ModuleCommand if input is valid else null
     */
    public ModuleCommand parseCommand(String userInput) throws ModException {
        Namespace parsedInput = this.parse(userInput);
        if (parsedInput != null) {
            String command = parsedInput.get("subParserName");
            Class<? extends ModuleCommand> commandClass = this.commandMapper.get(command);
            List<String> arguments = this.argumentMapper.get(command);
            Object[] objects = null;
            Class[] argumentsClasses = null;
            if (arguments != null) {
                objects = new Object[arguments.size()];
                argumentsClasses = new Class[arguments.size()];
                for (int i = 0; i < argumentsClasses.length; ++i) {
                    objects[i] = parsedInput.get(arguments.get(i));
                    argumentsClasses[i] = objects[i].getClass();
                }
                this.remapTypes(argumentsClasses);
            }
            return this.invokeCommand(commandClass, argumentsClasses, objects);
        } else {
            return null;
        }
    }
}
