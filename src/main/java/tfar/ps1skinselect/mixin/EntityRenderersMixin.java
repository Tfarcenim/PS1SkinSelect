package tfar.ps1skinselect.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import tfar.ps1skinselect.client.ClothingPlayerRenderer;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {

    @Shadow
    @Mutable
    @Final
    private static Map<String, EntityRendererProvider<AbstractClientPlayer>> PLAYER_PROVIDERS;


    static {
        PLAYER_PROVIDERS = ImmutableMap.of(
                "default", $$0 -> new ClothingPlayerRenderer($$0, false), "slim", $$0 -> new ClothingPlayerRenderer($$0, true)
        );
    }

    /*@Inject(method = "<clinit>",at = @At("RETURN"))
    private static void changeProviders(CallbackInfo ci) {

    }*/
}
