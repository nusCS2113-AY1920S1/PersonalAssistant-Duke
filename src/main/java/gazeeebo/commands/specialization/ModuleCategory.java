//@@author e0323290

package gazeeebo.commands.specialization;

/**
 * Parts of a technical elective.
 */
public class ModuleCategory {
    /**
     * Module code for a technical elective.
     */
    public String code;
    /**
     * If breadth is true, the technical elective is a breadth elective.
     */
    public boolean isBreadth;
    /**
     * If depth is true, the technical elective is a depth elective.
     */
    public boolean isDepth;
    /**
     * Initializing the 3 segments of a technical elective.
     * @param code module code
     */
    public ModuleCategory(final String code) {
        this.code = code;
        this.isBreadth = false;
        this.isDepth = false;
    }
}
