package net.hawon.spacesim.common.block.machines.skeleton;

import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.machine.ServerMachinePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public abstract class NodeBE extends BlockEntity {

    /*
    * Tree Node
    * */
    public NodeBE parent;
    public ArrayList<NodeBE> children = new ArrayList<>();
    public Direction input, output; //Electrical

    public NodeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void find() {
        if (!level.isClientSide())
            PacketHandler.INSTANCE.sendToServer(new ServerMachinePacket(worldPosition));
    }

    public void updateDirection(Direction facing) {
        output = facing;
        input = output.getOpposite();
        find();
    }

    public void setParent(NodeBE be) { // Fix required
        if (be == this)
            return;
        if (be == parent)
            return;
        if (parent != null) {
            parent.children.remove(this);
//            parent.children.add(be);
        }
        be.children.add(this);
        parent = be;
    }

    public boolean addChild(NodeBE be) {
        if (be.parent == this || children.contains(be))
            return false;
        be.parent = this;
        children.add(be);
        return true;
    }

    public void rmParent() {
        if (parent == null) return;
        parent.children.remove(this);
        parent = null;
    }

    public void remove() {
        rmParent();
        rmChildren();
    }

    public void rmChild(NodeBE be) {
        if (be.parent != this) return;
        be.parent = null;
        children.remove(be);
    }

    public void rmChildren() {
        children.forEach((node) -> node.parent = null);
        children.clear();
    }

}
