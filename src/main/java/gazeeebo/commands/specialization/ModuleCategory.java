package gazeeebo.commands.specialization;

public class ModuleCategory {
    public String code;
    public boolean breadth;
    public boolean depth;

    public ModuleCategory(String code) {
        this.code = code;
        this.breadth = false;
        this.depth = false;
    }
}
