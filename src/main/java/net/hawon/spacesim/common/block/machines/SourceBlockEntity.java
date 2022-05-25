package net.hawon.spacesim.common.block.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.UUID;

public abstract class SourceBlockEntity extends BlockEntity {

    private int timer;
    public UUID id;
    private SourceBlockEntity source;
    private final HashMap<UUID, MachineBlockEntity> load = new HashMap<>();

    public SourceBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void checkIn(UUID id, MachineBlockEntity machineBE) {
        load.put(id, machineBE);
    }

    public void checkOut(UUID id) {
        load.remove(id);
    }

    public void setSource(SourceBlockEntity source) {
        this.source = source;
    }

    public SourceBlockEntity getSource() {
        return source;
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

}
