private int undoRedoPointer = -1;
private Stack<> commandStack = new Stack<>();

private void insertCommand()
        {
        deleteElementsAfterPointer(undoRedoPointer);
        Command command =
        new InsertCharacterCommand();
        command.execute();
        commandStack.push(command);
        undoRedoPointer++;
        }

private void deleteElementsAfterPointer(int undoRedoPointer)
        {
        if(commandStack.size()<1)return;
        for(int i = commandStack.size()-1; i > undoRedoPointer; i--)
        {
        commandStack.remove(i);
        }
        }
private void undo()
        {
        Command command = commandStack.get(undoRedoPointer);
        command.unExecute();
        undoRedoPointer--;
        }

private void redo()
        {
        if(undoRedoPointer == commandStack.size() - 1)
        return;
        undoRedoPointer++;
        Command command = commandStack.get(undoRedoPointer);
        command.execute();
        }