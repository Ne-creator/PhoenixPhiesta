package io.github.nexusdino.phoenixphiesta.data.model;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, PhoenixPhiesta.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.GARMET_BLOCK.get());
        simpleBlock(ModBlocks.GARMET_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_GARMET_ORE.get());
    }
}
