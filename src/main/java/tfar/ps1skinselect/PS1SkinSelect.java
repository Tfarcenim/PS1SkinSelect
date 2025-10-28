package tfar.ps1skinselect;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import tfar.ps1skinselect.client.PS1SkinSelectClient;
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
        bus.addGenericListener(Block.class,this::registerBlocks);
        bus.addGenericListener(Item.class,this::registerItems);
        bus.addListener(ModDatagen::gather);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.addListener(this::commands);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::loggedIn);
        MinecraftForge.EVENT_BUS.addListener(this::clonePlayer);
        MinecraftForge.EVENT_BUS.addListener(this::tracking);
        if (FMLEnvironment.dist.isClient()) {
            PS1SkinSelectClient.init();
        }
    }

    void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ModInit.BLOCK.setRegistryName("wardrobe"));
    }
    void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ModInit.ITEM.setRegistryName("wardrobe"));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID,path);
    }

    void commands(RegisterCommandsEvent event) {
        //SkinSelectCommand.register(event.getDispatcher());
    }

    public static CustomSavedData customSavedData;

    void serverStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        ServerLevel overworld = server.overworld();
        customSavedData = overworld.getDataStorage().computeIfAbsent((p_184095_) -> CustomSavedData.loadStatic(overworld, p_184095_),
                () -> new CustomSavedData(), MOD_ID);
    }

    void tracking(PlayerEvent.StartTracking event) {
        Entity target = event.getTarget();
        Player trackingPlayer = event.getPlayer();
        if (target instanceof Player player) {
            ForgePacketHandler.sendToClient(new S2CSkinSettingsPacket(player.getId(),
                    ((PlayerDuck)player).getBaseSkin(),((PlayerDuck)player).getClothing()),(ServerPlayer) trackingPlayer);
        }
    }

    void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        ForgePacketHandler.sendToAll(new S2CSkinSettingsPacket(player.getId(),((PlayerDuck)player).getBaseSkin(),((PlayerDuck)player).getClothing()));
    }

    private void clonePlayer(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getPlayer();
        ((PlayerDuck)newPlayer).setClothing(((PlayerDuck)oldPlayer).getClothing());
        ((PlayerDuck)newPlayer).setBaseSkin(((PlayerDuck)oldPlayer).getBaseSkin());
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();
    }
}
