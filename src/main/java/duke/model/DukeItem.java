package duke.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The parent class for all {@code DukeItem}s, which are stored in {@code DukeList}s.
 */
abstract class DukeItem implements Serializable {
    /**
     * The string that separates different fields in the storage string.
     */
    protected static final String STORAGE_FIELD_DELIMITER = "\n";
    /**
     * The string that separates the names from the values in the storage string.
     */
    protected static final String STORAGE_NAME_SEPARATOR = ":";

    /**
     * The string that separates tags from each other in the storage string.
     */
    protected static final String STORAGE_TAG_SEPARATOR = " ";

    /**
     * The string that separates tags from each other in an input string.
     */
    protected static final String TAG_SEPARATOR = " ";
    /**
     * The item's tags.
     */
    protected final String tag;

    /**
     * A utility method used to extract fields from a storage string into a map, so that they can be
     * easily accessed by the subclasses in order to construct a new builder from the storage string.
     *
     * @param storageString the storage string representing a subclass.
     * @return a map of the storage string's fields.
     */
    protected static Map<String, String> storageStringToMap(String storageString) {
        return Stream.of(storageString.split(STORAGE_FIELD_DELIMITER))
            .map(s -> s.split(STORAGE_NAME_SEPARATOR, 2))
            .collect(Collectors.toMap(s -> s[0], s -> s.length > 1 ? s[1] : ""));
    }

    /**
     * Subclassing solution taken from https://stackoverflow.com/a/17165079
     * A builder class for {@code DukeItem}, making it easier to construct items with
     * optional fields.
     *
     * @param <T> the subclassed builder; see the sof link above.
     */
    abstract static class Builder<T extends Builder<T>> {
        private String tag = "";

        /**
         * Constructs an empty item with default values for all fields.
         */
        protected Builder() {

        }

        /**
         * Constructs an item from an existing item.
         *
         * @param i the existing item.
         */
        protected Builder(DukeItem i) {
            tag = i.tag;
        }

        /**
         * Constructs an item from its storage string. Used to load items from storage.
         *
         * @param storageString the item's storage string.
         */
        protected Builder(String storageString) {
            this(storageStringToMap(storageString));
        }

        /**
         * Constructs an item from its mapped storage string. Used internally to load items from storage.
         *
         * @param mappedStorageString a map of the item's storage string.
         */
        protected Builder(Map<String, String> mappedStorageString) {
            if (mappedStorageString.containsKey("tag")) {
                this.tag =  mappedStorageString.get("tag");
            }
        }

        public T setTag(String tag) {
            this.tag = tag;
            return getThis();
        }



        /**
         * Method used to limit the scope of suppression; returns {@code this}, type-cast to {@code T},
         * the subclassed builder.
         *
         * @return {@code this} type-casted to {@code T}
         */
        @SuppressWarnings("unchecked")
        private T getThis() {
            return (T) this;
        }
    }

    /**
     * Constructs an item from the item builder.
     *
     * @param builder the builder containing information for this object.
     */
    protected DukeItem(Builder<?> builder) {
        tag = builder.tag;
    }

    /**
     * Converts the item to a storage string to be saved, then loaded later.
     *
     * @return the item's storage string.
     */
    protected String toStorageString() {
        StringJoiner stringJoiner = new StringJoiner(STORAGE_FIELD_DELIMITER);
        stringJoiner.add("tag" + STORAGE_NAME_SEPARATOR + String.join(" ", tag));
        return stringJoiner.toString();
    }

    /**
     * Returns the set of tags of this item.
     *
     * @return the set of tags of this item.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Returns a single string containing all of the tags.
     *
     * @return single String of all the tags
     */
    public String getTagString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (tag.isEmpty()) {
            return "";
        } else {
            return tag;
        }
    }

}
