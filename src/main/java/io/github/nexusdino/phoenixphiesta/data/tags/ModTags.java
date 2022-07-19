package io.github.nexusdino.phoenixphiesta.data.tags;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import io.github.nexusdino.phoenixphiesta.core.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModTags {
    public static class Items extends ItemTagsProvider {
        public static final TagKey<Item> STEAM_ENGINE_FUELS = ItemTags.create(new ResourceLocation(PhoenixPhiesta.MOD_ID, "steam_engine_fuels"));

        public Items(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, new Blocks(generator, existingFileHelper), PhoenixPhiesta.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(Tags.Items.INGOTS).add(ModItems.GARMET_INGOT.get());
            tag(Tags.Items.RAW_MATERIALS).add(ModItems.RAW_GARMET.get());
            tag(STEAM_ENGINE_FUELS).addTag(ItemTags.COALS);
        }
    }

    public static class Blocks extends BlockTagsProvider {

        public Blocks(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, PhoenixPhiesta.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(Tags.Blocks.ORES).add(ModBlocks.GARMET_ORE.get(), ModBlocks.DEEPSLATE_GARMET_ORE.get());
        }
    }
}
