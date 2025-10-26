package tfar.ps1skinselect.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.PlayerDuck;

public record C2SSkinSettingsPacket(BaseSkin skin) implements C2SModPacket {

    public C2SSkinSettingsPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(BaseSkin.class));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        ((PlayerDuck)player).setBaseSkin(skin);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(skin);
    }
}
