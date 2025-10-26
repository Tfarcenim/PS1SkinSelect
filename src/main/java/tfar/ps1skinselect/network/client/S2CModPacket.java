package tfar.ps1skinselect.network.client;


import tfar.ps1skinselect.network.ModPacket;

public interface S2CModPacket extends ModPacket {
    void handleClient();
}
