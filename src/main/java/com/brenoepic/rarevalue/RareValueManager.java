package com.brenoepic.rarevalue;

import java.util.Collection;
import java.util.HashMap;

public class RareValueManager {
    private final HashMap<Integer, Rare> rares = new HashMap<>();

    public RareValueManager() {
        RareCost cost = new RareCost(100, 5, 10);
        rares.put(1, new Rare(1, "Rare test tes tes teeeeeetttssts", 100, 1, cost));
        rares.put(2, new Rare(2, "Epicccccccccc", 200, 1, cost));
        rares.put(3, new Rare(3, "Legendary", 300, 1, cost));
        for (int i = 4; i < 100; i++) {
            rares.put(i, new Rare(i, "Rare " + i, 100, 1, cost));
        }
    }

    public Collection<Rare> getRares() {
        return rares.values();
    }
}
