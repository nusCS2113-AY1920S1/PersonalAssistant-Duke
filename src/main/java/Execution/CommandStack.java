package Execution;

import commands.COMMANDKEYS;
import commands.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;

public class CommandStack {
    private static ArrayList<CommandSuper> myStack = new ArrayList<>();
    private static int counter = 0;
    private static long lastexec = System.currentTimeMillis();

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

    public static String nextCommand() {

        if (myStack.size() == 0) {
            return null;
        }

        if (System.currentTimeMillis() - lastexec > 3000) {
            counter = 0;
        } else {
            counter += 1;
            counter %= myStack.size();
        }
        lastexec = System.currentTimeMillis();


        return myStack.get(myStack.size() - 1 - counter).toString();
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