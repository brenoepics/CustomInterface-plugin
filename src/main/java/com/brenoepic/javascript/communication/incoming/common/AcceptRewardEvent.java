package com.brenoepic.javascript.communication.incoming.common;

import com.brenoepic.TimedRewards;
import com.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class AcceptRewardEvent extends IncomingWebMessage<AcceptRewardEvent.JSONAcceptRewardEvent> {

    public AcceptRewardEvent() {
        super(JSONAcceptRewardEvent.class);
    }

    @Override
    public void handle(GameClient client, JSONAcceptRewardEvent message) {
        if(client.getHabbo() == null) return;

        TimedRewards.getRewardsManager().claimReward(client.getHabbo(), message.accept);
    }

    public static class JSONAcceptRewardEvent {
        boolean accept;
    }
}

