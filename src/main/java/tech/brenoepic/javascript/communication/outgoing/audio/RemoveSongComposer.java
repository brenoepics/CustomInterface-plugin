package tech.brenoepic.javascript.communication.outgoing.audio;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class RemoveSongComposer extends OutgoingWebMessage {

    public RemoveSongComposer(int index) {
        super("remove_song");
        this.data.add("index", new JsonPrimitive(index));
    }
}
