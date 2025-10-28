package tfar.ps1skinselect;

import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

public enum ClothingColor {
    normal(null),
    white(DyeColor.WHITE),
    orange(DyeColor.ORANGE),
    magenta(DyeColor.MAGENTA),
    light_blue(DyeColor.LIGHT_BLUE),
    yellow(DyeColor.YELLOW),
    lime(DyeColor.LIME),
    pink(DyeColor.PINK),
    gray(DyeColor.GRAY),
    light_gray(DyeColor.LIGHT_GRAY),
    cyan(DyeColor.CYAN),
    purple(DyeColor.PURPLE),
    blue(DyeColor.BLUE),
    brown(DyeColor.BROWN),
    green(DyeColor.GREEN),
    red(DyeColor.RED),
    black(DyeColor.BLACK);

    public final DyeColor color;

    ClothingColor(@Nullable DyeColor color) {
        this.color = color;
    }

}
