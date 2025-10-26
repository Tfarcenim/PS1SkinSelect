package tfar.ps1skinselect.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import tfar.ps1skinselect.BaseSkin;
import tfar.ps1skinselect.PlayerDuck;
import tfar.ps1skinselect.network.ForgePacketHandler;
import tfar.ps1skinselect.network.server.C2SSkinSettingsPacket;

public class PS1SkinSelectScreen extends Screen {

    Button changeSkin;
    Button save;
    Button close;

    BaseSkin skin;

    /** The X size of the inventory window in pixels. */
    protected int imageWidth = 240;
    /** The Y size of the inventory window in pixels. */
    protected int imageHeight = 180;

    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int leftPos;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int topPos;

    protected PS1SkinSelectScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderDirtBackground(0);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        InventoryScreen.renderEntityInInventory(width/2, topPos + 150, 64, 0, 0, this.minecraft.player);
    }

    @Override
    protected void init() {
        super.init();

        skin = ((PlayerDuck)minecraft.player).getBaseSkin();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        changeSkin = addRenderableWidget(new Button(width/2 - 80,10,150,20, new TextComponent(skin.name()), this::press));
        int w = 160;
        save = addRenderableWidget(new Button(width/2-w,height - 30,150,20, new TextComponent("Save"), this::pressSave));
        close = addRenderableWidget(new Button(width/2,height-30,150,20, new TextComponent("Close"), this::pressClose));
    }

    void press(Button b) {
        skin = skin == BaseSkin.STEVE ? BaseSkin.ALEX : BaseSkin.STEVE;

        //change how the local player renders
        ((PlayerDuck)minecraft.player).setBaseSkin(skin);

        b.setMessage(new TextComponent(skin.name()));
    }

    void pressSave(Button b) {
        ForgePacketHandler.sendToServer(new C2SSkinSettingsPacket(skin));
    }

    void pressClose(Button b) {
        minecraft.setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


}
