package tfar.ps1skinselect.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends PlayerMixin {
    private static final UUID STEVE_UUID = new UUID(Long.MAX_VALUE, 0L);
    private static final UUID ALEX_UUID = new UUID(Long.MAX_VALUE, 1L);

    protected AbstractClientPlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Inject(
            at = {@At("HEAD")},
            method = {"getSkinTextureLocation"},
            cancellable = true
    )
    private void setSkinToSteve(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(DefaultPlayerSkin.getDefaultSkin(switch (baseSkin) {
            case steve -> STEVE_UUID;
            case alex -> ALEX_UUID;
        }));
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getModelName"},
            cancellable = true
    )
    private void setModelToSteve(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(DefaultPlayerSkin.getSkinModelName(switch (baseSkin) {
            case steve -> STEVE_UUID;
            case alex -> ALEX_UUID;
        }));
    }
}
