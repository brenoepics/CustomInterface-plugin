package com.brenoepic.javascript.communication.outgoing.common;

import com.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.brenoepic.rarevalue.Rare;
import com.google.gson.JsonArray;

import java.util.Collection;

public class RareValueComposer extends OutgoingWebMessage {
    public RareValueComposer(Collection<Rare> rares) {
        super("rare_values");

        JsonArray jsonElements = new JsonArray();
        rares.forEach(rare -> jsonElements.add(rare.toJson()));
        this.data.add("rares", jsonElements);
    }
}
