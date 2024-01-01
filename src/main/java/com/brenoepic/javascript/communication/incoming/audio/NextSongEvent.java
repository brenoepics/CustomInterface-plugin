package com.brenoepic.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.brenoepic.javascript.JSPlugin;
import com.brenoepic.javascript.audio.RoomPlaylist;
import com.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import com.brenoepic.javascript.communication.outgoing.audio.PlaySongComposer;
import com.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class NextSongEvent extends IncomingWebMessage<NextSongEvent.JSONNextSongEvent> {

    public NextSongEvent() {
        super(JSONNextSongEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONNextSongEvent message) {
        Room currentRoom = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(currentRoom == null)
            return;
        if(currentRoom.hasRights(client.getHabbo())) {
            RoomPlaylist playlist= JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(currentRoom.getId());
            playlist.nextSong();
            playlist.setPlaying(true);
            PlaySongComposer playSongComposer = new PlaySongComposer(playlist.getCurrentIndex());
            currentRoom.sendComposer(new JavascriptCallbackComposer(playSongComposer).compose());
            currentRoom.sendComposer(playlist.getNowPlayingBubbleAlert().compose());
        }
    }

    public static class JSONNextSongEvent {
        public int currentIndex;
    }
}
