package spinbox.commands;

import spinbox.Ui;
import spinbox.containers.ModuleContainer;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class PopulateCommand extends Command {
    private static final String POPULATED = HORIZONTAL_LINE + "\n"
            + "Sample data successfully populated. Hope you enjoy testing out SpinBox!!\n"
            + HORIZONTAL_LINE;
    private static final String NOT_POPULATED = HORIZONTAL_LINE + "\n"
            + "Sample data not populated. Please delete your current SpinBoxData folder and restart the application "
            + "if you wish to use sample data instead.\n"
            + HORIZONTAL_LINE;

    private static final String[] EMPTY_CONTEXT = {};
    private static final String[] CG2271_CONTEXT = {"modules", "CG2271"};
    private static final String[] CS3216_CONTEXT = {"modules", "CS3216"};
    private static final String[] ST2334_CONTEXT = {"modules", "ST2334"};
    private static final String[] CS2101_CONTEXT = {"modules", "CS2101"};
    private static final String[] CS2113T_CONTEXT = {"modules", "CS2113T"};

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode)
            throws SpinBoxException {

        if (moduleContainer.getModules().isEmpty()) {
            Queue<Command> commandsToExecute = new LinkedList<>();

            commandsToExecute.add(new AddCommand(EMPTY_CONTEXT, "module CS2113T Software Engineering & OOP"));
            commandsToExecute.add(new AddCommand(CS2113T_CONTEXT, "todo prepare for PE"));
            commandsToExecute.add(new AddCommand(CS2113T_CONTEXT, "note Complete the grades section"));
            commandsToExecute.add(new AddCommand(CS2113T_CONTEXT, "grade exam weightage: 40%"));
            commandsToExecute.add(new AddCommand(CS2113T_CONTEXT, "grade project weightage: 50%"));


            commandsToExecute.add(new AddCommand(EMPTY_CONTEXT, "module CS2101 Communication for "
                    + "Computing Professionals"));
            commandsToExecute.add(new AddCommand(CS2101_CONTEXT, "file conflict-resolution.pptx"));
            commandsToExecute.add(new AddCommand(CS2101_CONTEXT, "file team-meeting.docx"));
            commandsToExecute.add(new AddCommand(CS2101_CONTEXT, "file feedback.xlsx"));
            commandsToExecute.add(new UpdateCommand(CS2101_CONTEXT, "file 1 done"));


            commandsToExecute.add(new AddCommand(EMPTY_CONTEXT, "module CG2271 Real-Time Operating Systems"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "deadline Accumulate 300 impress points "
                    + "by: next friday"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "todo Start attending tutorials"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "lecture 2271 at: next tuesday 2pm to next "
                    + "tuesday 4pm"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "grade midterms weightage: 20%"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "file slides"));
            commandsToExecute.add(new AddCommand(CG2271_CONTEXT, "note I love in-class quizzes"));
            commandsToExecute.add(new UpdateCommand(CG2271_CONTEXT, "task 2 done"));


            commandsToExecute.add(new AddCommand(EMPTY_CONTEXT, "module ST2334 Probability & Statistics"));
            commandsToExecute.add(new AddCommand(ST2334_CONTEXT, "lab 2334 at: next wednesday 2pm to next "
                    + "wednesday 8pm"));
            commandsToExecute.add(new AddCommand(ST2334_CONTEXT, "tutorial 2334 at: next monday 10am to next "
                    + "monday 1pm"));


            commandsToExecute.add(new AddCommand(EMPTY_CONTEXT, "module CS3216 Software Product Engineering"
                    + " for Digital Markets"));
            commandsToExecute.add(new AddCommand(CS3216_CONTEXT, "lecture 3216 at: next monday 6:30pm to next "
                    + "monday 8:30pm"));
            commandsToExecute.add(new AddCommand(CS3216_CONTEXT, "note weird flex but ok"));

            while (!commandsToExecute.isEmpty()) {
                Command command = commandsToExecute.remove();
                command.execute(moduleContainer, pageTrace, ui, guiMode);
            }
            return POPULATED;
        } else {
            return NOT_POPULATED;
        }
    }
}
