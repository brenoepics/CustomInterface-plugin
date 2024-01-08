package tech.brenoepic.javascript.communication.outgoing.audio;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class PlaySongComposer extends OutgoingWebMessage {
    public PlaySongComposer(int index) {
        super("play_song");
        this.data.add("index", new JsonPrimitive(index));
    }
}
