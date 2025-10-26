package tfar.ps1skinselect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CustomSavedData extends SavedData {

    Set<UUID> seen = new HashSet<>();

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listTag = new ListTag();
        seen.forEach(uuid -> listTag.add(StringTag.valueOf(uuid.toString())));
        tag.put("seen",listTag);
        return tag;
    }

    public static CustomSavedData loadStatic(ServerLevel level, CompoundTag tag) {
        CustomSavedData data = new CustomSavedData();
        ListTag seen1 = tag.getList("seen", Tag.TAG_STRING);
        for (Tag tag1 : seen1) {
            data.seen.add(UUID.fromString(tag1.getAsString()));
        }
        return data;
    }

}
