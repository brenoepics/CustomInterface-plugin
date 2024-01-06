package com.brenoepic.rarevalue;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public record RareCost(int credits, int pointsType, int points) {
    public JsonArray toJson() {
        JsonArray jsonElements = new JsonArray();
        jsonElements.add(new JsonPrimitive(credits));
        jsonElements.add(new JsonPrimitive(pointsType));
        jsonElements.add(new JsonPrimitive(points));
        return jsonElements;
    }
}
