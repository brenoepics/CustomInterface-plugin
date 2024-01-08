package tech.brenoepic.rarevalue;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public record RareCategory(int id, String name) {
    public JsonObject toJson() {
        JsonObject jsonElements = new JsonObject();
        jsonElements.add("id", new JsonPrimitive(id));
        jsonElements.add("name", new JsonPrimitive(name));
        return jsonElements;
    }
}
