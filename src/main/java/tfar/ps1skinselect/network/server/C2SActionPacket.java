package tfar.ps1skinselect.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public enum C2SActionPacket implements C2SModPacket{
    ;

    static final C2SActionPacket[] VALUES = values();

    public static C2SActionPacket read(FriendlyByteBuf buf) {
        int ordinal = buf.readInt();
        return VALUES[ordinal];
    }

    @Override
    public void handleServer(ServerPlayer player) {

    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(ordinal());
    }
}
