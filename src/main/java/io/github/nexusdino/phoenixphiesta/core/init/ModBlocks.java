package io.github.nexusdino.phoenixphiesta.core.init;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.block.WattSteamEngineBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            PhoenixPhiesta.MOD_ID);

    public static final RegistryObject<Block> GARMET_ORE = registerBlock("garmet_ore", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))
    );
    public static final RegistryObject<Block> DEEPSLATE_GARMET_ORE = registerBlock("deepslate_garmet_ore", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE))
    );
    public static final RegistryObject<Block> GARMET_BLOCK = registerBlock("garmet_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK))
    );
    public static final RegistryObject<Block> STEAM_ENGINE = registerBlock("watt_steam_engine", () ->
            new WattSteamEngineBlock(BlockBehaviour.Properties.of(Material.METAL).strength(10F)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops())
    );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> supplier) {
        final var obj = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(obj.get(),
                new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        return obj;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
