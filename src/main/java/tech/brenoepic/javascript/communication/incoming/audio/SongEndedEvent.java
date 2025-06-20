package tech.brenoepic.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.audio.PlaySongComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class SongEndedEvent extends IncomingWebMessage<SongEndedEvent.JSONSongEndedEvent> {

    public SongEndedEvent() {
        super(JSONSongEndedEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONSongEndedEvent message) {
        Room room = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(room == null)
            return;

        if(room.hasRights(client.getHabbo())) {
            RoomPlaylist playlist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(room.getId());
            if(playlist.getCurrentIndex() == message.currentIndex) {
                playlist.nextSong();
                playlist.setPlaying(true);
                PlaySongComposer playSongComposer = new PlaySongComposer(playlist.getCurrentIndex());
                room.sendComposer(new JavascriptCallbackComposer(playSongComposer).compose());
                room.sendComposer(playlist.getNowPlayingBubbleAlert().compose());
            }
        }
    }

    static class JSONSongEndedEvent {
        public int currentIndex;
    }
}
