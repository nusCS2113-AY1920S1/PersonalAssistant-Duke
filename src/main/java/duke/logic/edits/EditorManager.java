package duke.logic.edits;

import duke.model.ModifiableEditorList;
import duke.model.TaskList;
import duke.model.UnmodifiableEditorList;

public class EditorManager {
    private Editor editor;
    private ModifiableEditorList editorList;
    private boolean isActive;

    public EditorManager() {
        isActive = false;
    }

    public void set(TaskList tasks) {
        editorList = new ModifiableEditorList(tasks);
    }

    public void activate() {
        isActive = true;
    }

    private void deactivate() {
        isActive = false;
    }

    public UnmodifiableEditorList getUnmodifiableEditorList() {
        return editorList;
    }
}
