package tech.brenoepic.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.audio.PlaySongComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class PreviousSongEvent extends IncomingWebMessage<PreviousSongEvent.JSONPreviousSongEvent> {
    public PreviousSongEvent() {
        super(JSONPreviousSongEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONPreviousSongEvent message) {
        Room currentRoom = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(currentRoom == null)
            return;
        if(currentRoom.hasRights(client.getHabbo())) {
            RoomPlaylist playlist= JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(currentRoom.getId());
            playlist.prevSong();
            playlist.setPlaying(true);
            PlaySongComposer playSongComposer = new PlaySongComposer(playlist.getCurrentIndex());
            currentRoom.sendComposer(new JavascriptCallbackComposer(playSongComposer).compose());
            currentRoom.sendComposer(playlist.getNowPlayingBubbleAlert().compose());
        }
    }

    public static class JSONPreviousSongEvent {
        public int currentIndex;
    }
}
