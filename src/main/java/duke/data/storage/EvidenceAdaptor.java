package duke.data.storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import duke.data.Evidence;

import java.lang.reflect.Type;

public class EvidenceAdaptor implements JsonSerializer<Evidence>, JsonDeserializer<Evidence> {
    @Override
    public Evidence deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject result = json.getAsJsonObject();
        String type = result.get("type").getAsString();
        JsonElement element = result.get("properties");
        try {
            return context.deserialize(element, Class.forName("duke.data." + type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Evidence src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
