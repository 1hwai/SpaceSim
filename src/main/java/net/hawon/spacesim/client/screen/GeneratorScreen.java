package net.hawon.spacesim.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.container.ExampleChestContainer;
import net.hawon.spacesim.common.container.GeneratorContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SpaceSim.MOD_ID,
            "textures/gui/generator.png");

    private ExtendedButton beanButton;

    public GeneratorScreen(GeneratorContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 168;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
        this.font.draw(stack, this.title, this.leftPos + 20, this.topPos + 5, 0x404040);
        this.font.draw(stack, this.playerInventoryTitle, this.leftPos + 8, this.topPos + 75, 0x404040);
    }

    @Override
    protected void init() {
        super.init();
        this.beanButton = addRenderableWidget(
                new ExtendedButton(this.leftPos + 2, this.topPos + 2, 16, 16, new TextComponent("B"),
                        btn -> Minecraft.getInstance().player.displayClientMessage(new TextComponent("beans"), false)));
    }

    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        drawString(stack, Minecraft.getInstance().font, "Energy: " + menu.getEnergy() + "J", this.imageWidth / 2 - 32, 48, 0xffffff);
    }
}