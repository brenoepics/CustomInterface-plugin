package tech.brenoepic.javascript.communication.outgoing.audio;

import tech.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;

public class DisposePlaylistComposer extends OutgoingWebMessage {
    public DisposePlaylistComposer() {
        super("dispose_playlist");
    }
}
