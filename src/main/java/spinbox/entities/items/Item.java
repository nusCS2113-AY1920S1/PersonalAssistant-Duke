package spinbox.entities.items;

import spinbox.exporter.Exportable;
import spinbox.storage.Storable;

public abstract class Item implements Exportable, Storable {
    private static final String STORE_DELIMITER = " | ";
    private static final String BRACKET_OPEN = "[";
    private static final String BRACKET_CLOSE = "] ";
    private static final String DONE = "DONE";
    private static final String NOT_DONE = "NOT DONE";

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

    protected String getStatusText() {
        return (this.getDone() ? DONE : NOT_DONE);
    }

    public String toString() {
        return BRACKET_OPEN + this.getStatusText() + BRACKET_CLOSE + this.getName();
    }

    @Override
    public String exportString() {
        return this.toString();
    }

    public String storeString() {
        return (this.getDone() ? 1 : 0) + STORE_DELIMITER + this.getName();
    }

    public void updateDone(Boolean done) {
        isDone = done;
    }

    public void markDone() {
        this.updateDone(true);
    }
}
