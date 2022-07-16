package io.github.nexusdino.phoenixphiesta.common.block;

import io.github.nexusdino.phoenixphiesta.common.blockentity.WattSteamEngineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WattSteamEngineBlock extends BaseEntityBlock {

    public WattSteamEngineBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new WattSteamEngineBlockEntity(p_153215_, p_153216_);
    }

    @Override
    public @NotNull InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_,
                                          Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (!p_60504_.isClientSide && p_60504_.getBlockEntity(p_60505_) instanceof WattSteamEngineBlockEntity w) {
                NetworkHooks.openScreen(((ServerPlayer) p_60506_), w, p_60505_);
                return InteractionResult.SUCCESS;
            }
        return InteractionResult.PASS;
    }
}