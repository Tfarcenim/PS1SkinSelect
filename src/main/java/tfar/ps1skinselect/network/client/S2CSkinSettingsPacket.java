package tfar.ps1skinselect.network.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.ClothingColor;
import tfar.ps1skinselect.ClothingType;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.client.PS1SkinSelectClient;

import java.util.EnumMap;
import java.util.Map;

public record S2CSkinSettingsPacket(int entityId,BaseSkin skin, Map<ClothingType, ClothingColor> clothing) implements S2CModPacket {

    public S2CSkinSettingsPacket(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readEnum(BaseSkin.class),buf.readMap(value -> value.readEnum(ClothingType.class),buf1 -> buf1.readEnum(ClothingColor.class)));
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(entityId);
        to.writeEnum(skin);
        to.writeMap(clothing, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeEnum);
    }

    @Override
    public void handleClient() {
        PS1SkinSelectClient.handleSkin(this);
    }
}
