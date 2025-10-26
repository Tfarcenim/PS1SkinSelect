package tfar.ps1skinselect;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.client.S2CEventPacket;

public class SkinSelectCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(PS1SkinSelect.MOD_ID)
                        .executes(SkinSelectCommand::openGui)
                .then(Commands.literal("reset").requires(sourceStack -> sourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("seen")
                                .executes(SkinSelectCommand::resetSeen)
                        )
                )
        );
    }

    static int resetSeen(CommandContext<CommandSourceStack> context) {
        PS1SkinSelect.customSavedData.seen.clear();
        PS1SkinSelect.customSavedData.setDirty();
        context.getSource().sendSuccess(new TextComponent("Reset 'seen' data"),true);
        return 1;
    }

    static int openGui(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ForgePacketHandler.sendToClient(S2CEventPacket.OPEN_SKIN_SELECT,player);
        return 1;
    }
}
