package com.brenoepic.javascript.communication.outgoing.slotmachine;

import com.brenoepic.javascript.communication.outgoing.OutgoingWebMessage;
import com.google.gson.JsonPrimitive;

public class SlotMachineComposer extends OutgoingWebMessage {
    public SlotMachineComposer(int itemId, int credits) {
        super("slot_machine");
        this.data.add("itemId", new JsonPrimitive(itemId));
        this.data.add("credits", new JsonPrimitive(credits));
    }
}
