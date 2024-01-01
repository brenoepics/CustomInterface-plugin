package com.brenoepic.javascript.communication.outgoing.audio;

import com.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;
public class DisposePlaylistComposer extends OutgoingWebMessage {
    public DisposePlaylistComposer() {
        super("dispose_playlist");
    }
}
