package tech.brenoepic.commands;

import tech.brenoepic.Main;
import tech.brenoepic.javascript.communication.outgoing.common.RareValueComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;
import tech.brenoepic.rarevalue.RareValueManager;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class RareValueCommand extends Command {
    public RareValueCommand(String name, String[] keys) {
        super(name, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (Main.getRareValueManager() == null) {
            gameClient.getHabbo().whisper("Rare Value is not enabled.");
            return true;
        }

        RareValueComposer composer = Main.getRareValueManager().getBuilder(gameClient.getHabbo()).build();
        gameClient.sendResponse(JavascriptCallbackComposer.ofMessage(composer));
        return true;
    }
}
