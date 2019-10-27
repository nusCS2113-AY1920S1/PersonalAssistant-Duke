package duke.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TreatmentAdaptor implements JsonSerializer<Treatment>, JsonDeserializer<Treatment> {
    @Override
    public Treatment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject result = json.getAsJsonObject();
        String type = result.get("type").getAsString();
        JsonElement element = result.get("properties");
        CustomClassLoader loader = new CustomClassLoader();
        Class<?> clz = loader.findClass(type);
        return context.deserialize(element, clz);
    }

    @Override
    public JsonElement serialize(Treatment src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
