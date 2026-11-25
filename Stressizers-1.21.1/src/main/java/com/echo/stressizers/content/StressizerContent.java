package com.echo.stressizers.content;

import com.echo.stressizers.Stressizers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/** Registry declarations for the four Stressizer tiers. */
public final class StressizerContent {
    private StressizerContent() {}

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Stressizers.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Stressizers.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Stressizers.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Stressizers.MOD_ID);

    // Base stress capacity per RPM. At 16 RPM these provide
    // 256 / 1,024 / 4,096 / 16,384 SU respectively.
    public static final float ANDESITE_CAPACITY = 16.0f;
    public static final float BRASS_CAPACITY = 64.0f;
    public static final float DIAMOND_CAPACITY = 256.0f;
    public static final float NETHERITE_CAPACITY = 1024.0f;

    public static final DeferredHolder<Block, StressizerBlock> ANDESITE_STRESSIZER =
            register("andesite_stressizer", ANDESITE_CAPACITY);
    public static final DeferredHolder<Block, StressizerBlock> BRASS_STRESSIZER =
            register("brass_stressizer", BRASS_CAPACITY);
    public static final DeferredHolder<Block, StressizerBlock> DIAMOND_STRESSIZER =
            register("diamond_stressizer", DIAMOND_CAPACITY);
    public static final DeferredHolder<Block, StressizerBlock> NETHERITE_STRESSIZER =
            register("netherite_stressizer", NETHERITE_CAPACITY);

    public static final Supplier<BlockEntityType<StressizerBlockEntity>> STRESSIZER_ENTITY = BLOCK_ENTITIES.register(
            "stressizer", () -> BlockEntityType.Builder.of(StressizerBlockEntity::new,
                    ANDESITE_STRESSIZER.get(), BRASS_STRESSIZER.get(),
                    DIAMOND_STRESSIZER.get(), NETHERITE_STRESSIZER.get()).build(null));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STRESSIZERS_TAB =
            CREATIVE_TABS.register("stressizers", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.stressizers"))
                    .icon(() -> new ItemStack(ANDESITE_STRESSIZER.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ANDESITE_STRESSIZER.get());
                        output.accept(BRASS_STRESSIZER.get());
                        output.accept(DIAMOND_STRESSIZER.get());
                        output.accept(NETHERITE_STRESSIZER.get());
                    })
                    .build());

    private static DeferredHolder<Block, StressizerBlock> register(String name, float capacityPerRpm) {
        DeferredHolder<Block, StressizerBlock> block = BLOCKS.register(name,
                () -> new StressizerBlock(BlockBehaviour.Properties.of().strength(3.0f), capacityPerRpm));
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}
