package tech.brenoepic.javascript.communication;

import lombok.Getter;
import tech.brenoepic.javascript.communication.incoming.audio.*;
import tech.brenoepic.javascript.communication.incoming.common.*;
import tech.brenoepic.javascript.utils.JsonFactory;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import gnu.trove.map.hash.THashMap;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CommunicationManager {
    private final THashMap<String, Class<? extends IncomingWebMessage>> incomingMessages;

    public CommunicationManager() {
        this.incomingMessages = new THashMap<>();
        initializeMessages();
    }

    public void initializeMessages() {
        this.registerMessage("move_avatar", MoveAvatarEvent.class);
        this.registerMessage("request_credits", RequestCreditsEvent.class);
        this.registerMessage("spin_slot_machine", RequestSpinSlotMachineEvent.class);
        this.registerMessage("add_song", AddSongEvent.class);
        this.registerMessage("next_song", NextSongEvent.class);
        this.registerMessage("prev_song", PreviousSongEvent.class);
        this.registerMessage("play_stop", PlayStopEvent.class);
        this.registerMessage("remove_song", RemoveSongEvent.class);
        this.registerMessage("song_ended", SongEndedEvent.class);
        this.registerMessage("accept_reward", AcceptRewardEvent.class);
        this.registerMessage("request_rare_values", RequestRareValueEvent.class);
    }

    public void registerMessage(String key, Class<? extends IncomingWebMessage> message) {
        this.incomingMessages.put(key, message);
    }

    public void onMessage(String jsonPayload, GameClient sender) {
        try {
            IncomingWebMessage.JSONIncomingEvent heading = JsonFactory.getInstance().fromJson(jsonPayload, IncomingWebMessage.JSONIncomingEvent.class);
            Class<? extends IncomingWebMessage> message = this.getIncomingMessages().get(heading.header);
            IncomingWebMessage webEvent = message.getDeclaredConstructor().newInstance();
            webEvent.handle(sender, JsonFactory.getInstance().fromJson(heading.data.toString(), webEvent.type));
        } catch(Exception e) {
            log.warn("unknown message: " + jsonPayload);
        }
    }

    public void dispose() {
        incomingMessages.clear();
    }
}
