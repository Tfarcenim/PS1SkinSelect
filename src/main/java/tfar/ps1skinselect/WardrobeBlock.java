package tfar.ps1skinselect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.client.S2CEventPacket;

public class WardrobeBlock extends Block {


    public WardrobeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && (pPlayer.getServer() instanceof DedicatedServer|| !FMLEnvironment.production)) {
            ForgePacketHandler.sendToClient(S2CEventPacket.OPEN_SKIN_SELECT,(ServerPlayer) pPlayer);
        }
        return InteractionResult.sidedSuccess(!pLevel.isClientSide);
    }
}
