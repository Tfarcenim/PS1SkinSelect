package tfar.ps1skinselect.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import tfar.ps1skinselect.ClothingType;

public class ClothingPlayerRenderer extends PlayerRenderer {
    public ClothingPlayerRenderer(EntityRendererProvider.Context pContext, boolean pUseSlimModel) {
        super(pContext, pUseSlimModel);
    }

    ClothingType pass;

    @Override
    public void render(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pass = null;
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }
}
