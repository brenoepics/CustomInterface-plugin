package com.brenoepic.javascript.communication;

import com.brenoepic.javascript.communication.incoming.audio.*;
import com.brenoepic.javascript.communication.incoming.common.AcceptRewardEvent;
import com.brenoepic.javascript.utils.JsonFactory;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import com.brenoepic.javascript.communication.incoming.common.MoveAvatarEvent;
import com.brenoepic.javascript.communication.incoming.common.RequestCreditsEvent;
import com.brenoepic.javascript.communication.incoming.common.RequestSpinSlotMachineEvent;
import gnu.trove.map.hash.THashMap;
import lombok.extern.slf4j.Slf4j;

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
    }

    public void registerMessage(String key, Class<? extends IncomingWebMessage> message) {
        this.incomingMessages.put(key, message);
    }

    public THashMap<String, Class<? extends IncomingWebMessage>> getIncomingMessages() {
        return this.incomingMessages;
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
