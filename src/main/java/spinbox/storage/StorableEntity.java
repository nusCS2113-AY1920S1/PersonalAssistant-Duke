package spinbox.storage;

public interface StorableEntity {
    String storeString();

    void fromStoredString(String fromStorage);
}
