package tfar.ps1skinselect.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.network.client.S2CEventPacket;
import tfar.ps1skinselect.network.client.S2CSkinSettingsPacket;

public class PS1SkinSelectClient {



    public static void init() {

    }

    static void renderPlayer(RenderPlayerEvent.Pre event) {
        event.setCanceled(true);

    }

    public static void handleEvent(S2CEventPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        switch (packet) {
            case OPEN_SKIN_SELECT -> {
                minecraft.setScreen(new PS1SkinSelectScreen(new TextComponent("Skin Select")));
            }
        }
    }

    public static void handleSkin(S2CSkinSettingsPacket packet) {
        Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId());
        if (entity instanceof PlayerDuck player) {
            player.setBaseSkin(packet.skin());
            player.setClothing(packet.clothing());
        }
    }
}
