package tfar.ps1skinselect.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.ps1skinselect.*;

import javax.annotation.Nullable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow protected abstract float getWhiteOverlayProgress(T pLivingEntity, float pPartialTicks);

    @Shadow @Nullable protected abstract RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing);

    @Shadow protected abstract boolean isBodyVisible(T pLivingEntity);

    @Shadow protected M model;

    @Shadow public abstract M getModel();

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSpectator()Z"))
    private void renderExtraModels(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pEntity instanceof AbstractClientPlayer abstractClientPlayer) {
            Minecraft minecraft = Minecraft.getInstance();
            for (ClothingType clothingType : ClothingType.values()) {

                boolean flag = this.isBodyVisible(pEntity);
                boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
                boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);

                RenderType rendertype = this.getClothingRenderType(abstractClientPlayer, flag, flag1, flag2,clothingType);
                if (rendertype != null) {
                    VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
                    int i = LivingEntityRenderer.getOverlayCoords(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
                    this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
                }
            }
        }
    }

    @Nullable
    @Unique
    private RenderType getClothingRenderType(AbstractClientPlayer pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing, ClothingType clothingType) {
        ResourceLocation resourcelocation = this.getClothingTextureLocation(pLivingEntity,clothingType);
        if (pTranslucent) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (pBodyVisible) {
            return this.model.renderType(resourcelocation);
        } else {
            return pGlowing ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected ResourceLocation getClothingTextureLocation(AbstractClientPlayer pLivingEntity,ClothingType type) {
        BaseSkin baseSkin = ((PlayerDuck)pLivingEntity).getBaseSkin();
        ClothingColor clothingColor = ((PlayerDuck)pLivingEntity).getClothing().get(type);
        ResourceLocation texture = PS1SkinSelect.id("textures/entity/player/parts/"+baseSkin.name()+"/"+type.name()+"/"+clothingColor.name()+".png");
        return texture;
    }
}
