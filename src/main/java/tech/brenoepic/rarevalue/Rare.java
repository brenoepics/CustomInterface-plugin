package tech.brenoepic.rarevalue;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Rare {
    private final int id;
    private final String name;
    private final String image;
    private final int itemId;
    private final int category;
    private final RareCost cost;

    public Rare(int id, int itemId, int category, RareCost cost) throws NullPointerException {
        this.id = id;
        this.itemId = itemId;
        this.category = category;
        this.cost = cost;
        Item item = loadItem();
        if(item == null) {
            throw new NullPointerException("Item {} not found".replace("{}", String.valueOf(itemId)));
        }
        name = item.getFullName();
        image = item.getName().replace(" ", "_").replace("*", "_");
    }

    public JsonObject toJson() {
        JsonObject jsonElements = new JsonObject();
        jsonElements.add("id", new JsonPrimitive(id));
        jsonElements.add("name", new JsonPrimitive(name));
        jsonElements.add("image", new JsonPrimitive(image));
        jsonElements.add("category", new JsonPrimitive(category));
        jsonElements.add("cost", cost.toJson());
        return jsonElements;
    }

    private Item loadItem() throws NullPointerException {
        return Emulator.getGameEnvironment().getItemManager().getItem(itemId);
    }
}