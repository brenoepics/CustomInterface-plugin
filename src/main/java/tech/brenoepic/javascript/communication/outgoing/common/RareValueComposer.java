package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import tech.brenoepic.rarevalue.Rare;
import tech.brenoepic.rarevalue.RareCategory;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.util.Collection;

public class RareValueComposer extends OutgoingWebMessage {
    public RareValueComposer(String frontPage, Collection<Rare> rares, Collection<RareCategory> categories, Collection<Integer> owned) {
        super("rare_values");

        this.data.add("rares", getRares(rares));
        this.data.add("categories", getCategories(categories));
        this.data.add("owned", getOwned(owned));
        this.data.add("frontpage", new JsonPrimitive(frontPage));
    }
    private JsonArray getRares(Collection<Rare> rares) {
        JsonArray jsonElements = new JsonArray();
        rares.forEach(rare -> jsonElements.add(rare.toJson()));
        return jsonElements;
    }
    private JsonArray getCategories(Collection<RareCategory> categories) {
        JsonArray jsonElements = new JsonArray();
        categories.forEach(category -> jsonElements.add(category.toJson()));
        return jsonElements;
    }
    private JsonArray getOwned(Collection<Integer> owned) {
        JsonArray jsonElements = new JsonArray();
        owned.forEach(jsonElements::add);
        return jsonElements;
    }
}
