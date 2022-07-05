package net.hawon.spacesim.common.block.machines.skeleton;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public abstract class NodeBE extends BlockEntity {

    public NodeBE parent;
    public ArrayList<NodeBE> children = new ArrayList<>();

    public NodeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean setParent(NodeBE be) {// Fix required
        be.parent = parent;
        parent = be;
        be.children.add(this);
        return true;
    }

    public boolean addChild(NodeBE be) {
        if (children.contains(be))
            return false;
        be.parent = this;
        children.add(be);
        return true;
    }

    public void rmParent() {

    }

    public void remove() {
        if (parent != null)
            parent.children.remove(this);
        for (NodeBE be : children) {
            be.parent = null;
        }
        children.clear();
    }

    public void rmChild() {

    }
}
