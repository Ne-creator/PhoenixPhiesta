package io.github.nexusdino.phoenixphiesta.common.blockentity;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import io.github.nexusdino.phoenixphiesta.common.menu.WattSteamEngineMenu;
import io.github.nexusdino.phoenixphiesta.core.config.SteamEngineConfig;
import io.github.nexusdino.phoenixphiesta.core.init.ModBlockEntities;
import io.github.nexusdino.phoenixphiesta.core.init.ModRecipes;
import io.github.nexusdino.phoenixphiesta.core.network.ClientboundUpdateEnergyPacket;
import io.github.nexusdino.phoenixphiesta.core.network.PacketHandler;
import io.github.nexusdino.phoenixphiesta.core.util.CustomEnergyStorage;
import io.github.nexusdino.phoenixphiesta.core.util.IEnergyHandlerBlockEntity;
import io.github.nexusdino.phoenixphiesta.core.util.IItemHandlerBlockEntity;
import io.github.nexusdino.phoenixphiesta.data.recipe.SteamEngineRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WattSteamEngineBlockEntity extends BlockEntity implements MenuProvider, IItemHandlerBlockEntity, IEnergyHandlerBlockEntity, IAnimatable {
    private final ItemStackHandler itemHandler = createHandler();
    private final CustomEnergyStorage energyStorage = createStorage();
    private final IFluidTank fluidTank = createTank();

    private final ContainerData data;

    private int progress = 0, maxProgress = SteamEngineConfig.MAX_PROGRESS.get();

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidTank> lazyFluidHandler = LazyOptional.empty();

    public WattSteamEngineBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.STEAM_ENGINE.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> throw new UnsupportedOperationException();
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> progress = pValue;
                    case 1 -> maxProgress = pValue;
                    default -> throw new UnsupportedOperationException();
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("title." + PhoenixPhiesta.MOD_ID + ".steam_engine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new WattSteamEngineMenu(windowId, inv, data, this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        lazyItemHandler.ifPresent(iItemHandler -> ((ItemStackHandler) iItemHandler).deserializeNBT(pTag));
        lazyEnergyHandler.ifPresent(iEnergyStorage -> ((CustomEnergyStorage) iEnergyStorage).deserializeNBT(pTag));
        this.progress = pTag.getInt("Progress");
        this.maxProgress = pTag.getInt("MaxProgress");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Inv", itemHandler.serializeNBT());
        pTag.put("Energy", energyStorage.serializeNBT());
        pTag.putInt("Progress", progress);
        pTag.putInt("MaxProgress", maxProgress);
    }

    /**
     * Called when this is first added to the world (by {@link net.minecraft.world.level.chunk.LevelChunk#addAndRegisterBlockEntity(BlockEntity)})
     * or right before the first tick when the chunk is generated or loaded from disk.
     * Override instead of adding {@code if (firstTick)} stuff in update.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(this::createHandler);
        this.lazyEnergyHandler = LazyOptional.of(this::createStorage);
        this.lazyFluidHandler = LazyOptional.of(this::createTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyItemHandler.invalidate();
        this.lazyEnergyHandler.invalidate();
        this.lazyFluidHandler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tick() {
        if (hasRecipe()) {
            progress++;
            setChanged();
            if (progress != maxProgress) {
                assembleOutputs();
            }
        }
    }

    private void assembleOutputs() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < inv.getContainerSize(); i++) {
            inv.setItem(i, inv.getItem(i));
        }
        assert level != null;
        level.getRecipeManager().getRecipeFor(ModRecipes.STEAM_ENGINE_TYPE.get(), inv, level).ifPresent(steamEngineRecipe -> {
            itemHandler.extractItem(0, 1, false);
            itemHandler.extractItem(1, 1, false);
        });
    }

    private boolean hasRecipe() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        Optional<SteamEngineRecipe> match = Objects.requireNonNull(level).getRecipeManager()
                .getRecipeFor(ModRecipes.STEAM_ENGINE_TYPE.get(), inv, level);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            inv.setItem(i, inv.getItem(i));
        }

        return match.isPresent() && isFuelPresent() && inputDetected();
    }

    private boolean isFuelPresent() {
        return !itemHandler.getStackInSlot(0).isEmpty();
    }
    private boolean inputDetected() {
        return !itemHandler.getStackInSlot(1).isEmpty();
    }

    @Override
    public ItemStackHandler createHandler() {
        return new ItemStackHandler() {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return isItemValid(slot, stack) ? stack : super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public CustomEnergyStorage createStorage() {
        return new CustomEnergyStorage(SteamEngineConfig.CAPACITY.get(), SteamEngineConfig.MAX_TRANSFER.get()) {
            @Override
            public void onEnergyChange() {
                PacketHandler.sendToClient(new ClientboundUpdateEnergyPacket(energy));
                setChanged();
            }
        };
    }

    public IFluidTank createTank() {
        return new FluidTank(SteamEngineConfig.CAPACITY.get()) {
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
    }

    public int getContainerSize() {
        return 5;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, event -> {
            if (progress != maxProgress) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.watt_steam_engine.wheel", true));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return new AnimationFactory(this);
    }
}