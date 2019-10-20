package duke.logic.edits;

import duke.logic.commands.Command;
import duke.logic.commands.PromptCommand;
import duke.model.ModifiableEditorList;
import duke.model.TaskList;
import duke.model.UnmodifiableEditorList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditorManager {
    private Editor editor;
    private ModifiableEditorList editorList;
    private boolean isActive;
    private int currentIndex;

    public EditorManager() {
        isActive = false;
        currentIndex = 0;
    }

    public Command execute(String userInput) {
        //editor.execute(currentIndex, )
        return new PromptCommand("PLACEHOLDER");
    }

    public void set(TaskList tasks) {
        editorList = new ModifiableEditorList(tasks);
    }

    public void activate() {
        isActive = true;
        if (editorList.isEmpty()) {
            deactivate();
        }
        editor = new Editor(editorList);
    }

    private void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public UnmodifiableEditorList getUnmodifiableEditorList() {
        return editorList;
    }

    public void updateIndex(KeyEvent e) {
        if (e.getCode().equals(KeyCode.UP)) {
            currentIndex = editor.getNearestUp(currentIndex);
        } else if (e.getCode().equals(KeyCode.DOWN)) {
            currentIndex = editor.getNearestDown(currentIndex);
        } else if (e.getCode().equals(KeyCode.RIGHT)) {
            currentIndex = editor.getNearestRight(currentIndex);
        } else if (e.getCode().equals(KeyCode.LEFT)) {
            currentIndex = editor.getNearestLeft(currentIndex);
        }
        //editorList.get(currentIndex).setColor();
    }
}
