package tfar.ps1skinselect.network.client;

import net.minecraft.network.FriendlyByteBuf;
import tfar.ps1skinselect.client.PS1SkinSelectClient;

public enum S2CEventPacket implements S2CModPacket {
    OPEN_SKIN_SELECT;

    static final S2CEventPacket[] VALUES = values();

    public static S2CEventPacket read(FriendlyByteBuf buf) {
        int ordinal = buf.readInt();
        return VALUES[ordinal];
    }

    @Override
    public void handleClient() {
        PS1SkinSelectClient.handleEvent(this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(ordinal());
    }
}
