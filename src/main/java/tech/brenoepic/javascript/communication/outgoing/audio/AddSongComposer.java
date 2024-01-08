package tech.brenoepic.javascript.communication.outgoing.audio;

import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AddSongComposer extends OutgoingWebMessage {
    public AddSongComposer(RoomPlaylist.YoutubeVideo video) {
        super("add_song");
        JsonObject song = new JsonObject();
        song.add("name", new JsonPrimitive(video.name));
        song.add("videoId", new JsonPrimitive(video.videoId));
        song.add("channel", new JsonPrimitive(video.channel));
        this.data.add("song", song);
    }
}
