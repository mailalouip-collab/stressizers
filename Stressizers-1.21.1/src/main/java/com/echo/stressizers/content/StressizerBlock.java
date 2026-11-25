package com.echo.stressizers.content;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

/**
 * A shaft-driven kinetic component. It connects along its facing axis and
 * contributes stress capacity only while it is part of a rotating network.
 */
public class StressizerBlock extends DirectionalKineticBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    // Create multiplies this value by the Stressizer's actual RPM.
    private final float capacityPerRpm;

    public StressizerBlock(BlockBehaviour.Properties properties, float capacityPerRpm) {
        super(properties);
        this.capacityPerRpm = capacityPerRpm;
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    public float getCapacityPerRpm() {
        return capacityPerRpm;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public Class<? extends KineticBlockEntity> getBlockEntityClass() {
        return StressizerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
        return StressizerContent.STRESSIZER_ENTITY.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StressizerBlockEntity(StressizerContent.STRESSIZER_ENTITY.get(), pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FACING);
    }
}
