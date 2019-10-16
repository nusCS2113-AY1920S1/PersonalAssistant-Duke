package spinbox.entities.items;

public abstract class Item {
    private static final String STORE_DELIMITER = " | ";
    private static final String BRACKET_OPEN = "[";
    private static final String BRACKET_CLOSE = "] ";
    private static final String DONE = "✓";
    private static final String NOT_DONE = "✗";

    private String name;
    private Boolean isDone;

    protected Item(String name) {
        this.name = name;
        this.isDone = false;
    }

    protected Item() {

    }

    public String getName() {
        return this.name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public Boolean getDone() {
        return isDone;
    }

    protected String getStatusIcon() {
        return (this.getDone() ? DONE : NOT_DONE);
    }

    public String toString() {
        return BRACKET_OPEN + this.getStatusIcon() + BRACKET_CLOSE + this.getName();
    }

    public String storeString() {
        return (this.getDone() ? 1 : 0) + STORE_DELIMITER + this.getName();
    }

    protected void setDone(Boolean done) {
        isDone = done;
    }

    public void markDone() {
        this.setDone(true);
    }
}
