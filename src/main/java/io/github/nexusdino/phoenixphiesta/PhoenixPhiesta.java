package io.github.nexusdino.phoenixphiesta;

import com.mojang.logging.LogUtils;
import io.github.nexusdino.phoenixphiesta.client.screen.WattSteamEngineScreen;
import io.github.nexusdino.phoenixphiesta.core.config.Config;
import io.github.nexusdino.phoenixphiesta.core.init.*;
import io.github.nexusdino.phoenixphiesta.core.network.PacketHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(PhoenixPhiesta.MOD_ID)
public class PhoenixPhiesta {
    public static final String MOD_ID = "phoenixphiesta";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.GARMET_INGOT.get());
        }
    };

    public PhoenixPhiesta() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        modEventBus.addListener(this::setup);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        Config.registerConfigs();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
    }

    @Mod.EventBusSubscriber(modid = PhoenixPhiesta.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.WATT_STEAM_ENGINE.get(), WattSteamEngineScreen::new);
            });
        }
    }
}
