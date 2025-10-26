package tfar.ps1skinselect.network.server;

import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.network.ModPacket;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
