package tech.brenoepic.javascript.communication.outgoing.slotmachine;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class SpinResultComposer extends OutgoingWebMessage {
    public SpinResultComposer(int result1, int result2, int result3, boolean won, int payout) {
        super("slot_result");
        this.data.add("result1", new JsonPrimitive(result1));
        this.data.add("result2", new JsonPrimitive(result2));
        this.data.add("result3", new JsonPrimitive(result3));
        this.data.add("won", new JsonPrimitive(won));
        this.data.add("payout", new JsonPrimitive(payout));
    }
}
