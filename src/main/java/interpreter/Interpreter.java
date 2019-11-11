package interpreter;

import executor.accessors.AccessType;
import executor.command.CommandType;
import executor.Executor;
import utils.InfoCapsule;

public class Interpreter {
    private Executor executorLayer;

    public Interpreter(String taskPath, String walletPath) {
        this.executorLayer = new Executor(taskPath, walletPath);
    }

    /**
     * Interprets the userInput relative to the TaskList provided and executes the Command.
     * @param userInput The userInput taken from the User Interface
     * @return InfoCapsule containing the execution results of the Command
     */
    public InfoCapsule interpret(String userInput) {
        CommandType commandType = Parser.parseForCommandType(userInput);
        return this.executorLayer.runCommand(commandType, userInput);
    }

    public InfoCapsule requestSave() {
        return this.executorLayer.saveAllData();
    }

    public InfoCapsule requestTesterData() {
        return this.executorLayer.loadTestData();
    }

    public InfoCapsule request(AccessType accessType, String argsStr) {
        return this.executorLayer.access(accessType, argsStr);
    }
}
