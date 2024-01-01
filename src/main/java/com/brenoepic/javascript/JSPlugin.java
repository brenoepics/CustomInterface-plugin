package com.brenoepic.javascript;

import com.brenoepic.javascript.audio.RoomAudioManager;
import com.brenoepic.javascript.communication.CommunicationManager;
import lombok.Getter;

@Getter
public class JSPlugin {
    private static JSPlugin INSTANCE;

    private final RoomAudioManager roomAudioManager;
    private final CommunicationManager communicationManager;

    public JSPlugin() {
        this.roomAudioManager = new RoomAudioManager();
        this.communicationManager = new CommunicationManager();
    }

    public static void init() {
        JSPlugin.INSTANCE = new JSPlugin();
    }

    public static JSPlugin getInstance() {
        return JSPlugin.INSTANCE;
    }

    public void dispose() {
        getInstance().communicationManager.dispose();
        getInstance().roomAudioManager.dispose();
    }
}
