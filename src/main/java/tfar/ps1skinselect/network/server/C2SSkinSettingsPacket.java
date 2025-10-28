package tfar.ps1skinselect.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.ClothingColor;
import tfar.ps1skinselect.ClothingType;
import tfar.ps1skinselect.PlayerDuck;

public record C2SSkinSettingsPacket(BaseSkin skin, java.util.Map<ClothingType, ClothingColor> map) implements C2SModPacket {

    public C2SSkinSettingsPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(BaseSkin.class),buf.readMap(value -> value.readEnum(ClothingType.class), buf1 -> buf1.readEnum(ClothingColor.class)));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        ((PlayerDuck)player).setBaseSkin(skin);
        ((PlayerDuck)player).setClothing(map);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(skin);
        to.writeMap(map, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeEnum);
    }
}
