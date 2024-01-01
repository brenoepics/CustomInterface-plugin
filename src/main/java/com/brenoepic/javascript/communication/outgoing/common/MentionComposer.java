package com.brenoepic.javascript.communication.outgoing.common;

import com.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class MentionComposer extends OutgoingWebMessage {
    public MentionComposer(String sender, String message) {
        super("mention");
        this.data.add("sender", new JsonPrimitive(sender));
        this.data.add("message", new JsonPrimitive(message));
    }
}
