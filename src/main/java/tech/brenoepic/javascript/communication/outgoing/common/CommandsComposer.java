package tech.brenoepic.javascript.communication.outgoing.common;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.google.gson.JsonArray;

import java.util.List;

public class CommandsComposer extends OutgoingWebMessage {
    public CommandsComposer(List<Command> commands) {
        super("commands");
        JsonArray jsonElements = new JsonArray();
        for (Command c : commands) {
            jsonElements.add(Emulator.getTexts().getValue("commands.description." + c.permission, "commands.description." + c.permission));
        }
        this.data.add("commands", jsonElements);
    }
}
