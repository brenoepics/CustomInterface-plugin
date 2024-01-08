package tech.brenoepic.rarevalue;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public record RareCost(int credits, int pointsType, int points) {
    public JsonObject toJson() {
        JsonObject jsonElements = new JsonObject();
        jsonElements.add("credits", new JsonPrimitive(credits));
        jsonElements.add("pointsType", new JsonPrimitive(pointsType));
        jsonElements.add("points", new JsonPrimitive(points));
        return jsonElements;
    }
}
