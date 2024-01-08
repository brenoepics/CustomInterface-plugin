package tech.brenoepic.javascript.communication.incoming.audio;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.audio.AddSongComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class AddSongEvent extends IncomingWebMessage<AddSongEvent.JSONAddSong> {

    public AddSongEvent() {
        super(JSONAddSong.class);
    }

    @Override
    public void handle(GameClient client, JSONAddSong message) {
        Room currentRoom = client.getHabbo().getHabboInfo().getCurrentRoom();
        if(currentRoom == null)
            return;
        if(currentRoom.hasRights(client.getHabbo())) {
            RoomPlaylist playlist= JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(currentRoom.getId());
            RoomPlaylist.YoutubeVideo song = new RoomPlaylist.YoutubeVideo(message.name, message.videoId, message.channel);
            playlist.addSong(song);
            AddSongComposer addSongComposer = new AddSongComposer(song);
            currentRoom.sendComposer(new JavascriptCallbackComposer(addSongComposer).compose());
        }
    }

    public static class JSONAddSong {
        public String name;
        public String videoId;
        public String channel;
    }
}
