package io.github.nexusdino.phoenixphiesta.data.gen;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.data.loot.ModLootTableProvider;
import io.github.nexusdino.phoenixphiesta.data.model.ModBlockStateProvider;
import io.github.nexusdino.phoenixphiesta.data.model.ModItemModelProvider;
import io.github.nexusdino.phoenixphiesta.data.recipe.ModRecipeProvider;
import io.github.nexusdino.phoenixphiesta.data.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PhoenixPhiesta.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGeneration {

    @SubscribeEvent
    public static void registerDataGenerators(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModItemModelProvider(generator, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new ModTags.Blocks(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModTags.Items(generator, existingFileHelper));
    }
}
