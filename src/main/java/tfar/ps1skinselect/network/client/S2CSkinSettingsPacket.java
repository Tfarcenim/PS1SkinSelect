package tfar.ps1skinselect.network.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.client.PS1SkinSelectClient;

public record S2CSkinSettingsPacket(BaseSkin skin) implements S2CModPacket {

    public S2CSkinSettingsPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(BaseSkin.class));
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(skin);
    }

    @Override
    public void handleClient() {
        PS1SkinSelectClient.handleSkin(this);
    }
}
