package net.hawon.spacesim.common.block.nodes.skeleton;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.PacketHandler;
import net.hawon.spacesim.common.network.packet.energy.cable.ServerCablePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

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
        rotate();
    }

    public void rotate() {
        output = getBlockState().getValue(FACING);
        input = output.getOpposite();
        setChanged();
        PacketHandler.INSTANCE.sendToServer(new ServerCablePacket(worldPosition.relative(input)));

        System.out.println("Rotate : " + output);
    }

    public void setParent(NodeBE be) { // Fix required
        System.out.println("setParent() be : " + be);
        if (be == null) return;
        parent = be;
        if (!parent.children.contains(this))
            parent.children.add(this);
    }

    public void addChild(NodeBE be) {
        be.setParent(this);
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

    public void validateChildren(ArrayList<NodeBE> checkList) {
        if (children.isEmpty() || checkList.isEmpty()) return;
        for (int i = children.size() - 1; i >= 0; i--) {
            NodeBE child = children.get(i);
            if (!checkList.contains(child)) {
                child.rmParent();
            }
        }
    }

}
