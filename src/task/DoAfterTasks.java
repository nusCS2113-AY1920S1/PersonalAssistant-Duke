//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package task;

import java.io.Serializable;

public class DoAfterTasks extends task.Task implements Serializable {
    protected String after;

    public DoAfterTasks(String description, String after) {
        super(description);
        this.after = after;
    }

    public String GiveTask() {
        String var10000 = super.giveTask();
        return "[A]" + var10000 + "(after: " + after + ")";
    }
}
