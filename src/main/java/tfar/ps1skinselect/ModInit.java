package tfar.ps1skinselect;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModInit {

    public static final WardrobeBlock BLOCK = new WardrobeBlock(BlockBehaviour.Properties.of(Material.WOOD));
    public static final BlockItem ITEM = new BlockItem(BLOCK,new Item.Properties());

}
