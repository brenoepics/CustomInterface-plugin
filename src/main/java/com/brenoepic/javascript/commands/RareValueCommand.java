package com.brenoepic.javascript.commands;

import com.brenoepic.TimedRewards;
import com.brenoepic.javascript.communication.outgoing.common.RareValueComposer;
import com.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class RareValueCommand extends Command {
    public RareValueCommand(String name, String[] keys) {
        super(name, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        RareValueComposer composer = new RareValueComposer(TimedRewards.getRareValueManager().getRares());
        gameClient.sendResponse(JavascriptCallbackComposer.ofMessage(composer));
        return true;
    }
}
