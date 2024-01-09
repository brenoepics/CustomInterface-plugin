package tech.brenoepic.javascript.communication.incoming.common;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import tech.brenoepic.Main;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.common.RareValueComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class RequestRareValueEvent extends IncomingWebMessage<RequestRareValueEvent.JSONRequestRareValueEvent> {

    public RequestRareValueEvent() {
        super(RequestRareValueEvent.JSONRequestRareValueEvent.class);
    }

    @Override
    public void handle(GameClient client, RequestRareValueEvent.JSONRequestRareValueEvent message) {
        if(client.getHabbo() == null || Main.getRareValueManager() == null) return;

        RareValueComposer composer = Main.getRareValueManager().getBuilder(client.getHabbo()).build();
        client.sendResponse(JavascriptCallbackComposer.ofMessage(composer));
    }

    public static class JSONRequestRareValueEvent {
        String author;
    }
}