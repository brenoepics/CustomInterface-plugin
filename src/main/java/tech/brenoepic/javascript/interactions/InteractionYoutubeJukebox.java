package tech.brenoepic.javascript.interactions;

import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import tech.brenoepic.javascript.JSPlugin;
import tech.brenoepic.javascript.audio.RoomPlaylist;
import tech.brenoepic.javascript.communication.outgoing.audio.JukeboxComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionYoutubeJukebox extends InteractionDefault {
    public InteractionYoutubeJukebox(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionYoutubeJukebox(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {

    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        super.onClick(client, room, objects);
        if(room.hasRights(client.getHabbo())) {
            RoomPlaylist roomPlaylist = JSPlugin.getInstance().getRoomAudioManager().getPlaylistForRoom(room.getId());
            JukeboxComposer webComposer = new JukeboxComposer(roomPlaylist);
            client.sendResponse(new JavascriptCallbackComposer(webComposer));
        }
    }
}
