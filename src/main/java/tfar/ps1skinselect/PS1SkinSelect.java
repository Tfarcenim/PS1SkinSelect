package tfar.ps1skinselect;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.PacketHandler;
import tfar.ps1skinselect.network.client.S2CEventPacket;
import tfar.ps1skinselect.network.client.S2CSkinSettingsPacket;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PS1SkinSelect.MOD_ID)
public class PS1SkinSelect {
    public static final String MOD_ID = "ps1skinselect";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public PS1SkinSelect() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.addListener(this::commands);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::loggedIn);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID,path);
    }

    void commands(RegisterCommandsEvent event) {
        SkinSelectCommand.register(event.getDispatcher());
    }

    public static CustomSavedData customSavedData;

    void serverStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        ServerLevel overworld = server.overworld();
        customSavedData = overworld.getDataStorage().computeIfAbsent((p_184095_) -> CustomSavedData.loadStatic(overworld, p_184095_),
                () -> new CustomSavedData(), MOD_ID);
    }

    void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        if (true||!customSavedData.seen.contains(player.getUUID())) {
            ForgePacketHandler.sendToClient(S2CEventPacket.OPEN_SKIN_SELECT,player);
            customSavedData.seen.add(player.getUUID());
            customSavedData.setDirty();
        }
        player.getServer().getPlayerList().getPlayers().forEach(player1 ->
                ForgePacketHandler.sendToClient(new S2CSkinSettingsPacket(((PlayerDuck)player).getBaseSkin()),
                player1));
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();
    }
}
