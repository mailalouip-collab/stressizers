package com.echo.stressizers.content;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A pass-through kinetic component that contributes capacity while it is
 * receiving rotation. It does not create rotation on its own.
 *
 * Create normally treats stress-capacity providers as kinetic sources. This
 * block mirrors the speed entering its shaft axis, so it is only considered
 * active while the surrounding kinetic network is actually rotating.
 */
public class StressizerBlockEntity extends KineticBlockEntity {
    private boolean removingSource;

    public StressizerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * Mirror the speed being transmitted through this block. With no incoming
     * rotation, this returns zero and the Stressizer provides no capacity.
     */
    @Override
    public float getGeneratedSpeed() {
        return getTheoreticalSpeed();
    }

    @Override
    public float calculateAddedStressCapacity() {
        if (getTheoreticalSpeed() == 0)
            return 0;
        if (getBlockState().getBlock() instanceof StressizerBlock block)
            return block.getCapacityPerRpm();
        return 0;
    }

    /**
     * When the input speed first reaches this block, it is initially attached
     * to the network before its inherited speed is written. Update the
     * capacity entry after that speed change so Create counts this block.
     */
    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);
        if (level == null || level.isClientSide || !hasNetwork())
            return;

        KineticNetwork network = getOrCreateNetwork();
        float capacity = getTheoreticalSpeed() == 0 ? 0 : calculateAddedStressCapacity();
        network.updateCapacityFor(this, capacity);
    }

    /**
     * Keep Create's source map cleanup working even though this block's
     * generated speed becomes zero as its incoming rotation is removed.
     */
    @Override
    public boolean isSource() {
        return removingSource || getGeneratedSpeed() != 0;
    }

    @Override
    public void removeSource() {
        removingSource = true;
        try {
            super.removeSource();
        } finally {
            removingSource = false;
        }
    }
}
