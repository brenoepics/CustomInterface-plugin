package tech.brenoepic.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.audio.RemoveSongComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class RemoveSongEvent extends IncomingWebMessage<RemoveSongEvent.JSONRemoveSongEvent> {

    public RemoveSongEvent() {
        super(JSONRemoveSongEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONRemoveSongEvent message) {
        Room room = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(room == null)
            return;

        if(room.hasRights(client.getHabbo())) {
            RoomPlaylist roomPlaylist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(room.getId());
            roomPlaylist.removeSong(message.index);
            room.sendComposer(new JavascriptCallbackComposer(new RemoveSongComposer(message.index)).compose());
        }
    }

    public static class JSONRemoveSongEvent {
        public int index;
    }
}
