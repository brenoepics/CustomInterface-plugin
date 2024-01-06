package com.brenoepic.rarevalue;

import com.eu.habbo.habbohotel.catalog.CatalogItem;
import com.google.gson.JsonArray;
public record Rare(int id, String name, int itemId, int category, RareCost cost) {


    public JsonArray toJson() {
        JsonArray jsonElements = new JsonArray();
        jsonElements.add(id);
        jsonElements.add(name);
        jsonElements.add(itemId);
        jsonElements.add(cost.toJson());
        return jsonElements;
    }
}