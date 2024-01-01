package com.brenoepic.javascript.communication.outgoing.audio;

import com.brenoepic.javascript.audio.RoomPlaylist;
import com.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PlaylistComposer extends OutgoingWebMessage {
    public PlaylistComposer(RoomPlaylist playlist) {
        super("playlist");
        JsonArray playlistJson = new JsonArray();
        for (RoomPlaylist.YoutubeVideo video:playlist.getPlaylist()) {
            JsonObject song = new JsonObject();
            song.add("name", new JsonPrimitive(video.name));
            song.add("videoId", new JsonPrimitive(video.videoId));
            song.add("channel", new JsonPrimitive(video.channel));
            playlistJson.add(song);
        }
        this.data.add("playlist", playlistJson);
    }
}
