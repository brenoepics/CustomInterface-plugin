package tech.brenoepic.javascript.override_packets.outgoing;

import tech.brenoepic.javascript.utils.JsonFactory;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;

public class JavascriptCallbackComposer extends MessageComposer {
    private final OutgoingWebMessage webMessage;

    public JavascriptCallbackComposer(OutgoingWebMessage message) {
        this.webMessage = message;
    }

    public static JavascriptCallbackComposer ofMessage(OutgoingWebMessage message) {
        return new JavascriptCallbackComposer(message);
    }
    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.NuxAlertComposer);
        //replace the / char so the string doesnt get cutoff by the swf
        String jsonMessage = JsonFactory.getInstance().toJson(webMessage).replace("/", "&#47;");
        this.response.appendString("habblet/open/" + jsonMessage);
        return this.response;
    }
}
