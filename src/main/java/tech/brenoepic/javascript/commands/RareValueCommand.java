package tech.brenoepic.javascript.commands;

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
        reloadRareValue();
        if (Main.getRareValueManager() == null) {
            gameClient.getHabbo().whisper("Rare Value is not enabled.");
        }

        RareValueComposer composer = Main.getRareValueManager().getBuilder(gameClient.getHabbo()).build();
        gameClient.sendResponse(JavascriptCallbackComposer.ofMessage(composer));
        return true;
    }

    private void reloadRareValue() {
        if (Main.getRareValueManager() == null) {
            Main.setRareValueManager(new RareValueManager());
            return;
        }

        Main.getRareValueManager().reload();
    }
}
