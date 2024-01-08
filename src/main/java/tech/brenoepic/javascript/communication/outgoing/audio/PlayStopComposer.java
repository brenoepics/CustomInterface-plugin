package tech.brenoepic.javascript.communication.outgoing.audio;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class PlayStopComposer extends OutgoingWebMessage {
    public PlayStopComposer(boolean isPlaying) {
        super("play_stop");
        this.data.add("playing", new JsonPrimitive(isPlaying));
    }
}
