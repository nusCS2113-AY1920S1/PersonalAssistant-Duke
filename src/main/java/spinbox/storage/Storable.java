package spinbox.storage;

public interface Storable {
    String storeString();

    void fromStoredString(String fromStorage);
}
