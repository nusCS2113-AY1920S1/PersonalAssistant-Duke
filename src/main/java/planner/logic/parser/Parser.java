package planner.logic.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.action.StoreTrueArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;
import planner.logic.command.AddCcaScheduleCommand;
import planner.logic.command.Arguments;
import planner.logic.command.CapCommand;
import planner.logic.command.ClearCommand;
import planner.logic.command.EndCommand;
import planner.logic.command.GradeCommand;
import planner.logic.command.ModuleCommand;
import planner.logic.command.ReminderCommand;
import planner.logic.command.RemoveCommand;
import planner.logic.command.SearchThenAddCommand;
import planner.logic.command.ShowCommand;
import planner.logic.command.SortCommand;
import planner.logic.command.UpdateModuleCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.parser.action.Join;
import planner.util.logger.PlannerLogger;

public class Parser {

    private ArgumentParser parser;
    private Subparsers subParserManager;
    private HashMap<String, Subparser> subParsers;
    private HashMap<String, Class<? extends ModuleCommand>> commandMapper;
    private ArgumentAction joinString;

    /**
     * Constructor for parser.
     */
    public Parser() {
        this.parser = ArgumentParsers.newFor("ModPlan")
                .build()
                .defaultHelp(true)
                .description("ModPlan Argument Parser")
                .version("1.0");
        this.init();
    }

    /**
     * Map CLI commands to respective ModuleCommand classes.
     */
    // Add new command types here
    private void mapBuiltinCommands() {
        mapCommand("add", SearchThenAddCommand.class);
        mapCommand("show", ShowCommand.class);
        mapCommand("bye", EndCommand.class);
        mapCommand("remove", RemoveCommand.class);
        mapCommand("scheduleCca", AddCcaScheduleCommand.class);
        mapCommand("clear", ClearCommand.class);
        mapCommand("sort", SortCommand.class);
        mapCommand("cap", CapCommand.class);
        mapCommand("grade", GradeCommand.class);
        mapCommand("update", UpdateModuleCommand.class);
        mapCommand("reminder", ReminderCommand.class);
        //mapCommand("passwd", SetPasswordCommand.class);
    }

    /**
     * Add arguments for respective sub-parsers.
     */
    // Add arguments for parsers here
    public void mapBuiltinParserArguments() {
        Subparsers addParsers = getSubParser("add")
                .help("Add a module or cca")
                .addSubparsers()
                .dest("toAdd")
                .help("add command options");
        Subparser addParser = addParsers.addParser("module")
                .help("Add a module");
        addParser.addArgument("moduleCode")
                .help("Codename of module to add");
        addParser.addArgument("--begin")
                //.required(true)
                .nargs("+")
                .action(this.joinString)
                .help("Begin time");
        addParser.addArgument("--end")
                //.required(true)
                .nargs("+")
                .action(this.joinString)
                .help("End time");
        addParser.addArgument("--dayOfWeek")
                //.required(true)
                .help("Day of week on which the module takes place");

        Subparser addCcaParser = addParsers.addParser("cca")
                .help("Add a cca");
        addCcaParser.addArgument("name")
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

        getSubParser("show")
                .help("Show infos about your timetable")
                .addArgument("toShow")
                .choices("module", "core", "ge", "ue", "cca")
                .help("What to show");

        getSubParser("bye")
                .help("Exit ModPlan");

        Subparser removeParser = getSubParser("remove")
                .help("Remove a module or cca");
        removeParser.addArgument("toRemove")
                .choices("module", "cca")
                .help("What to remove");
        removeParser.addArgument("index")
                .type(int.class)
                .help("Index to remove");

        Subparser scheduleCcaParser = getSubParser("scheduleCca")
                .help("Add schedule to a CCA");
        scheduleCcaParser.addArgument("index")
                .type(Integer.class)
                .help("Index of cca to schedule");
        scheduleCcaParser.addArgument("--begin")
                .required(true)
                .nargs("+")
                .action(this.joinString)
                .help("Begin time");
        scheduleCcaParser.addArgument("--end")
                .required(true)
                .nargs("+")
                .action(this.joinString)
                .help("End time");
        scheduleCcaParser.addArgument("--dayOfWeek")
                .required(true)
                .help("Day of week on which cca takes place");

        getSubParser("clear")
                .help("Clear your data as specified")
                .addArgument("toClear")
                .choices("module", "cca", "data", "password")
                .help("What to clear");

        Subparsers sortParsers = getSubParser("sort")
                .help("Sort your modules and/or ccas in the order you desire")
                .addSubparsers()
                .dest("toSort")
                .help("What to sort");

        Subparser module = sortParsers.addParser("module")
                .help("Sort your modules");
        module.addArgument("type")
                .choices("code", "grade", "level", "mc")
                .help("What to use for sorting");
        module.addArgument("--r")
                .action(new StoreTrueArgumentAction())
                .setDefault(false);

        Subparser cca = sortParsers.addParser("cca")
                .help("Sort your CCAs");
        cca.addArgument("--r")
                .action(new StoreTrueArgumentAction())
                .setDefault(false);

        Subparser time = sortParsers.addParser("time")
                .help("Sort your modules and ccas to days of the week");
        time.addArgument("DayOfTheWeek")
                .choices("monday","tuesday","wednesday","thursday","friday","saturday","sunday")
                .help("Day of the week");
        time.addArgument("--r")
                .action(new StoreTrueArgumentAction())
                .setDefault(false);

        getSubParser("cap")
                .help("Calculate your CAP from your input or list")
                .addArgument("toCap")
                .choices("overall", "list", "module")
                .help("What type of CAP to calculate");

        Subparser gradeParser = getSubParser("grade")
                .help("Enter your grades and let me calculate your GPA for you!");
        gradeParser.addArgument("moduleCode")
            .help("Codename of module to grade");
        gradeParser.addArgument("letterGrade")
            .help("Grade you achieved for this module");

        getSubParser("update")
                .help("Updates local module data file")
                .addArgument("moduleDataUpdate")
                .choices("module")
                .help("\"module\" is the only valid keyword!");

        getSubParser("reminder")
                .help("Setting reminders")
                .addArgument("toReminder")
                .choices("list", "one", "two", "three", "four", "stop")
                .help("When do you want to set the reminder again");

        //getSubParser("passwd")
        //        .help("Set or update your password")
        //        .addArgument("password")
        //        .help("New password");
    }

    private void initBuiltinActions() {
        this.joinString = new Join();
    }

    /**
     * Initialize sub-parsers and command mappers.
     */
    private void init() {
        this.subParserManager = this.parser.addSubparsers();
        this.subParsers = new HashMap<>();
        this.commandMapper = new HashMap<>();
        this.initBuiltinActions();
        this.mapBuiltinCommands();
        this.initBuiltinParsers();
        this.mapBuiltinParserArguments();
    }

    /**
     * Map CLI commands to sub-parsers.
     */
    private void initBuiltinParsers() {
        for (String command: this.commandMapper.keySet()) {
            this.addParser(command);
        }
    }

    private void mapCommand(String command, Class<? extends ModuleCommand> type) {
        this.commandMapper.put(command, type);
    }

    /**
     * Add a custom sub-parser.
     * @param name name of sub-parser
     * @param addHelp add help or not
     * @param prefixChars prefix character to distinguish arguments
     * @return added sub-parser
     */
    private Subparser addParser(String name, boolean addHelp, String prefixChars) {
        this.subParsers.put(name,
                this.subParserManager
                .addParser(name, addHelp, prefixChars)
                .setDefault("command", name));
        return this.getSubParser(name);
    }

    private Subparser addParser(String name, boolean addHelp) {
        return this.addParser(name, addHelp, "--");
    }

    private Subparser addParser(String name) {
        return this.addParser(name, true);
    }

    private ArgumentParser getParser() {
        return this.parser;
    }

    private Subparser getSubParser(String subParserName) {
        return this.subParsers.get(subParserName);
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
            PlannerLogger.log(ex);
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
            PlannerLogger.log(ex);
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
     * Invoke a module command.
     * @param commandClass command class to invoke
     * @param argumentsClasses corresponding classes of arguments
     * @param objects corresponding arguments
     * @return ModuleCommand if input is valid else null
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
            PlannerLogger.log(ex);
            return null;
        } catch (Throwable ex) {
            ex.printStackTrace();
            PlannerLogger.log(ex);
            return null;
        }
    }

    /**
     * Parse input to ModuleCommand.
     * @param userInput input String
     * @return parsed ModuleCommand if input is valid else null
     */
    public ModuleCommand parseCommand(String userInput) throws ModException {
        if (userInput == null) {
            return null;
        }
        Namespace parsedInput = this.parse(userInput);
        if (parsedInput != null) {
            String command = parsedInput.get("command");
            Class<? extends ModuleCommand> commandClass = this.commandMapper.get(command);
            if (parsedInput.getAttrs().size() > 1) {
                return this.invokeCommand(commandClass,
                                          new Class[]{Arguments.class},
                                          new Arguments[]{new Arguments(parsedInput.getAttrs())});
            } else {
                return this.invokeCommand(commandClass, null, null);
            }
        } else {
            return null;
        }
    }
}
