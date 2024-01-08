package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class RewardAvailableComposer extends OutgoingWebMessage {
    public RewardAvailableComposer(int closesIn, String message) {
        super("reward_available");
        this.data.add("close", new JsonPrimitive(closesIn));
        this.data.add("message", new JsonPrimitive(message));
    }
}
