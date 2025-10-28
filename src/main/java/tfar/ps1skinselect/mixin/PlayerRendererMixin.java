package tfar.ps1skinselect.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.ps1skinselect.ClothingType;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRendererMixin{

    @Shadow protected abstract void setModelProperties(AbstractClientPlayer pClientPlayer);

    @Inject(method = "renderHand",at = @At("RETURN"))
    private void onHandRender(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear, CallbackInfo ci) {
        for (ClothingType clothingType : ClothingType.values()) {
            pRendererArm.render(pMatrixStack, pBuffer.getBuffer(RenderType.entitySolid(getClothingTextureLocation(pPlayer,clothingType))), pCombinedLight, OverlayTexture.NO_OVERLAY);
            pRendererArmwear.render(pMatrixStack, pBuffer.getBuffer(RenderType.entityTranslucent(getClothingTextureLocation(pPlayer,clothingType))), pCombinedLight, OverlayTexture.NO_OVERLAY);
        }
    }


    /**
     * @author
     * @reason
     */
   /* @Overwrite
    private void renderHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear) {
        PlayerModel<AbstractClientPlayer> playermodel = (PlayerModel<AbstractClientPlayer>) this.getModel();
        this.setModelProperties(pPlayer);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        pRendererArm.xRot = 0.0F;
        pRendererArm.render(pMatrixStack, pBuffer.getBuffer(RenderType.entitySolid(pPlayer.getSkinTextureLocation())), pCombinedLight, OverlayTexture.NO_OVERLAY);
        pRendererArmwear.xRot = 0.0F;
        pRendererArmwear.render(pMatrixStack, pBuffer.getBuffer(RenderType.entityTranslucent(pPlayer.getSkinTextureLocation())), pCombinedLight, OverlayTexture.NO_OVERLAY);
    }*/
}
