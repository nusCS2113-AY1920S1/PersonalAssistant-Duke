package spinbox.items;

public abstract class Item {
    private String name;
    private Boolean isDone;

    protected Item(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getDone() {
        return isDone;
    }

    protected String getStatusIcon() {
        return (this.getDone() ? "✓" : "✗");
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getName();
    }

    public String storeString() {
        return Integer.toString((this.getDone() ? 1 : 0)) + " | " + this.getName();
    }

    protected void setDone(Boolean done) {
        isDone = done;
    }

    public void markDone() {
        this.setDone(true);
    }
}
