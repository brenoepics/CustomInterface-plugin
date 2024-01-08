package tech.brenoepic.javascript.communication.incoming.common;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.messages.outgoing.users.UserCreditsComposer;
import tech.brenoepic.javascript.communication.incoming.IncomingWebMessage;
import tech.brenoepic.javascript.communication.outgoing.common.UpdateCreditsComposer;
import tech.brenoepic.javascript.override_packets.outgoing.JavascriptCallbackComposer;

public class RequestCreditsEvent extends IncomingWebMessage<RequestCreditsEvent.JSONRequestCredits> {

    public RequestCreditsEvent() {
        super(JSONRequestCredits.class);
    }

    @Override
    public void handle(GameClient client, JSONRequestCredits message) {
        client.sendResponse(new UserCreditsComposer(client.getHabbo()));
        UpdateCreditsComposer creditsComposer = new UpdateCreditsComposer(client.getHabbo().getHabboInfo().getCredits());
        client.sendResponse(new JavascriptCallbackComposer(creditsComposer));
    }

    static class JSONRequestCredits {
        boolean idk;
    }
}
