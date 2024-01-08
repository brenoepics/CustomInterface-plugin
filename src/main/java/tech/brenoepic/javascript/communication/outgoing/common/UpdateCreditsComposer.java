package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class UpdateCreditsComposer extends OutgoingWebMessage {
    public UpdateCreditsComposer(int credits) {
        super("update_credits");
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
