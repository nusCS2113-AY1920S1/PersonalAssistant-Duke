package gazeeebo.commands.specialization;

public class ModuleCategories {
    public String code;
    public boolean isDone;
    public boolean breadth;
    public boolean depth;

    public ModuleCategories(String code) {
        this.code = code;
        this.isDone = false;
        this.breadth = false;
        this.depth = false;
    }
    public String getStatusIcon() {
        return (isDone ? "D" : "ND");
    }

}

