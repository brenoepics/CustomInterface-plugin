package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class YoutubeTVComposer extends OutgoingWebMessage {

    public YoutubeTVComposer(String videoId) {
        super("youtube_tv");
        this.data.add("videoId", new JsonPrimitive(videoId));
    }
}
