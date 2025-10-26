package tfar.ps1skinselect.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.network.client.S2CEventPacket;
import tfar.ps1skinselect.network.client.S2CSkinSettingsPacket;

public class PS1SkinSelectClient {


    public static void handleEvent(S2CEventPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        switch (packet) {
            case OPEN_SKIN_SELECT -> {
                minecraft.setScreen(new PS1SkinSelectScreen(new TextComponent("Skin Select")));
            }
        }
    }

    public static void handleSkin(S2CSkinSettingsPacket packet) {
        ((PlayerDuck)Minecraft.getInstance().player).setBaseSkin(packet.skin());
    }
}
