package net.hawon.spacesim.common.network.packet.energy.machine;

import net.hawon.spacesim.common.block.nodes.MachineBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerMachinePacket {

    public final BlockPos pos;

    public ServerMachinePacket(BlockPos pos) {
        this.pos = pos;
    }

    public ServerMachinePacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) { //encode -> save data
        buffer.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
           Level level = Objects.requireNonNull(ctx.get().getSender()).level;
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof MachineBE machineBE) {

                success.set(true);
            }
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
