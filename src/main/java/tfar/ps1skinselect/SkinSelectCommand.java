package tfar.ps1skinselect;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.client.S2CEventPacket;

public class SkinSelectCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(PS1SkinSelect.MOD_ID)
                        .executes(SkinSelectCommand::openGui)
        );
    }

    static int openGui(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ForgePacketHandler.sendToClient(S2CEventPacket.OPEN_SKIN_SELECT,player);
        return 1;
    }
}
