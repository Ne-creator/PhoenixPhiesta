package io.github.nexusdino.phoenixphiesta.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.client.rendering.EnergyInfoArea;
import io.github.nexusdino.phoenixphiesta.client.rendering.FluidHelper;
import io.github.nexusdino.phoenixphiesta.client.rendering.FluidTankRenderer;
import io.github.nexusdino.phoenixphiesta.common.menu.WattSteamEngineMenu;
import io.github.nexusdino.phoenixphiesta.core.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Optional.empty;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class WattSteamEngineScreen extends AbstractContainerScreen<WattSteamEngineMenu> {
    public static final ResourceLocation ENGINE_LOCATION = new ResourceLocation(PhoenixPhiesta.MOD_ID,
            "textures/gui/watt_steam_engine.png");

    private FluidTankRenderer<FluidStack> renderer;
    private EnergyInfoArea infoArea;

    public WattSteamEngineScreen(WattSteamEngineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 45)) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL),
                    empty(), pMouseX - x, pMouseY - y);
        }

        if (isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 13, 8, 64)) {
            renderTooltip(pPoseStack, infoArea.getTooltips(),
                    empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void init() {
        super.init();
        this.infoArea =
                new EnergyInfoArea(((width - imageWidth) / 2) + 156, ((height - imageHeight) / 2) + 13, menu.blockEntity.createStorage());
        this.renderer = new FluidTankRenderer<>(new FluidHelper(), 16000, true, 16, 32);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.9F);
        RenderSystem.setShaderTexture(0, ENGINE_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            blit(pPoseStack, i + 101, j + 16, 176, 0, 8, menu.getScaledProgress());
        }

        infoArea.draw(pPoseStack);
        renderer.drawFluid(pPoseStack, 56, 47, menu.getFluid());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
