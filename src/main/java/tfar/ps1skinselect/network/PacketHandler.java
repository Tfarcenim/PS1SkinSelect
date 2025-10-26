package tfar.ps1skinselect.network;

import tfar.ps1skinselect.network.client.S2CEventPacket;
import tfar.ps1skinselect.network.server.C2SActionPacket;
import tfar.ps1skinselect.network.server.C2SSkinSettingsPacket;

public class PacketHandler {

    public static void registerPackets() {
        ForgePacketHandler.registerClientPacket(S2CEventPacket.class,S2CEventPacket::read);
        ForgePacketHandler.registerServerPacket(C2SActionPacket.class,C2SActionPacket::read);
        ForgePacketHandler.registerServerPacket(C2SSkinSettingsPacket.class, C2SSkinSettingsPacket::new);
    }

}
