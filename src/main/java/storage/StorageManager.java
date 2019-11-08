package storage;

public class StorageManager {
    private Storage inventoryStorage;
    private Storage userStorage;
    private Storage bookingStorage;
    private Storage roomStorage;

    /**
     * Mangaes the different storages.
     * @param userStorage user storage
     * @param bookingStorage booking storage
     * @param roomStorage room storage
     * @param inventoryStorage inventory stroage
     */
    public StorageManager(Storage userStorage, Storage bookingStorage, Storage roomStorage, Storage inventoryStorage) {
        this.inventoryStorage = inventoryStorage;
        this.bookingStorage = bookingStorage;
        this.roomStorage = roomStorage;
        this.userStorage = userStorage;
    }

    public Storage getInventoryStorage() {
        return inventoryStorage;
    }

    public Storage getUserStorage() {
        return userStorage;
    }

    public Storage getBookingStorage() {
        return bookingStorage;
    }

    public Storage getRoomStorage() {
        return roomStorage;
    }
}
