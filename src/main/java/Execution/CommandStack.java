package Execution;

import commands.COMMANDKEYS;
import commands.CommandSuper;

import java.io.IOException;
import java.util.Stack;

public class CommandStack {
    private static Stack<CommandSuper> MyStack = new Stack<>();

    public static void pushCmd(CommandSuper cmd) throws IOException {
        if(cmd.getRoot() == COMMANDKEYS.yes){
            executeLastCommand();
        } else {
            MyStack.push(cmd);
            if (cmd.isExecute()) {
                cmd.executeCommands();
            }
        }

    }

    public static CommandSuper popCmd() {
        CommandSuper topCmd = MyStack.peek();
        MyStack.pop();
        return topCmd;
    }

    public static void topCmd() {
        MyStack.peek();
    }

    public static void clearStack() {
        MyStack.clear();
    }

    public static void executeLastCommand() throws IOException {
        System.out.println("Execute Last Command");
        CommandSuper cmd = MyStack.peek();
        if (!cmd.isExecute()) {
            cmd.executeCommands();
            cmd.setExecute(true);
        }
        //TODO Execute Last Command
    }
}
