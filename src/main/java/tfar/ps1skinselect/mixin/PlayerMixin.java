package tfar.ps1skinselect.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.client.S2CSkinSettingsPacket;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {


    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    protected BaseSkin baseSkin = BaseSkin.STEVE;

    @Override
    public BaseSkin getBaseSkin() {
        return baseSkin;
    }

    @Override
    public void setBaseSkin(BaseSkin skin) {
        baseSkin = skin;
        if (!level.isClientSide) {
            ForgePacketHandler.sendToAll(new S2CSkinSettingsPacket(skin));
        }
    }


    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void read(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("base_skin")) {
            baseSkin = BaseSkin.valueOf(pCompound.getString("base_skin"));
        }
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void add(CompoundTag pCompound, CallbackInfo ci) {
        pCompound.putString("base_skin",baseSkin.name());
    }
}
