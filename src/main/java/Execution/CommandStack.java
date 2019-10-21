package Execution;

import commands.COMMANDKEYS;
import commands.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;

public class CommandStack {
    private static ArrayList<CommandSuper> myStack = new ArrayList<>();

    public static ArrayList<CommandSuper> getMyStack() {
        return myStack;
    }

    public static void pushCmd(CommandSuper cmd) throws IOException {
        if (cmd.getRoot() == COMMANDKEYS.yes) {
            executeLastCommand();
        } else {
            myStack.add(cmd);
            if (cmd.isExecute()) {
                cmd.executeCommands();
            }
        }

    }

    public static void executePrevious () {
        for (int i = 0; i < myStack.size(); i += 1) {
            System.out.println("this is " + myStack.get(i));
        }
        CommandSuper cmd = myStack.get(myStack.size() - 4);
        try {
            cmd.executeCommands();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeLast () {
        CommandSuper cmd = myStack.get(myStack.size() - 3);
        try {
            cmd.executeCommands();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CommandSuper popCmd() {
        CommandSuper topCmd = myStack.get(myStack.size() - 1);
        myStack.remove(myStack.size() - 1);
        return topCmd;
    }

    public static void topCmd() {
        myStack.get(myStack.size() - 1);
    }

    public static void clearStack() {
        myStack.clear();
    }

    public static void executeLastCommand() throws IOException {
        System.out.println("Execute Last Command");
        CommandSuper cmd = myStack.get(myStack.size() - 1);
        if (!cmd.isExecute()) {
            cmd.executeCommands();
            cmd.setExecute(true);
        }
        //TODO Execute Last Command
    }
}
