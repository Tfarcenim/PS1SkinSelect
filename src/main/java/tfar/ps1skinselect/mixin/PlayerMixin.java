package tfar.ps1skinselect.mixin;

import com.google.common.collect.Maps;
import net.minecraft.Util;
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
import tfar.ps1skinselect.ClothingColor;
import tfar.ps1skinselect.ClothingType;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.client.S2CSkinSettingsPacket;

import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck {


    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    protected BaseSkin baseSkin = BaseSkin.steve;

    @Unique
    protected Map<ClothingType, ClothingColor> clothing = Util.make(Maps.newEnumMap(ClothingType.class),clothingTypeClothingColorEnumMap -> {
       for (ClothingType clothingType : ClothingType.values()) {
           clothingTypeClothingColorEnumMap.put(clothingType,ClothingColor.normal);
       }
    });

    @Override
    public BaseSkin getBaseSkin() {
        return baseSkin;
    }

    @Override
    public void setBaseSkin(BaseSkin skin) {
        baseSkin = skin;
        if (!level.isClientSide) {
            ForgePacketHandler.sendToAll(new S2CSkinSettingsPacket(getId(),skin,clothing));
        }
    }

    @Override
    public Map<ClothingType, ClothingColor> getClothing() {
        return clothing;
    }

    @Override
    public void setClothing(Map<ClothingType, ClothingColor> clothing) {
        this.clothing = clothing;
        if (!level.isClientSide) {
            ForgePacketHandler.sendToAll(new S2CSkinSettingsPacket(getId(),baseSkin,clothing));
        }
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void read(CompoundTag pCompound, CallbackInfo ci) {

        try {

            if (pCompound.contains("base_skin")) {
                baseSkin = BaseSkin.valueOf(pCompound.getString("base_skin"));
            }
            if (pCompound.contains("clothing")) {
                CompoundTag tag = pCompound.getCompound("clothing");
                for (ClothingType type : ClothingType.values()) {
                    clothing.put(type,ClothingColor.valueOf(tag.getString(type.name())));
                }
                }
        }catch (Exception e){}
    }

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void add(CompoundTag pCompound, CallbackInfo ci) {
        pCompound.putString("base_skin",baseSkin.name());
        CompoundTag clothingTag = new CompoundTag();
        for (ClothingType type : ClothingType.values()) {
            clothingTag.putString(type.name(),clothing.get(type).name());
        }
        pCompound.put("clothing",clothingTag);
    }
}
