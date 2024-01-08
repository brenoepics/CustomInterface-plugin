package tech.brenoepic.javascript;

import tech.brenoepic.javascript.audio.RoomAudioManager;
import tech.brenoepic.javascript.communication.CommunicationManager;
import lombok.Getter;

@Getter
public class JSPlugin {
    private static JSPlugin instance;

    private final RoomAudioManager roomAudioManager;
    private final CommunicationManager communicationManager;

    public JSPlugin() {
        this.roomAudioManager = new RoomAudioManager();
        this.communicationManager = new CommunicationManager();
    }

    public static void init() {
        JSPlugin.instance = new JSPlugin();
    }

    public static JSPlugin getInstance() {
        return JSPlugin.instance;
    }

    public void dispose() {
        getInstance().communicationManager.dispose();
        getInstance().roomAudioManager.dispose();
    }
}
