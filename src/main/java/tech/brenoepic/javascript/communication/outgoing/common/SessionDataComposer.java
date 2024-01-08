package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class SessionDataComposer extends OutgoingWebMessage {
    public SessionDataComposer(int id, String username, String look, int credits) {
        super("session_data");
        this.data.add("id", new JsonPrimitive(id));
        this.data.add("username", new JsonPrimitive(username));
        this.data.add("look", new JsonPrimitive(look));
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
