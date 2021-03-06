package net.hawon.spacesim.common.network.packet.energy.cable;

import net.hawon.spacesim.common.block.edges.cables.CableBE;
import net.hawon.spacesim.common.network.energy.CableNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerCablePacket {

    public final BlockPos cablePos;

    public ServerCablePacket(BlockPos cablePos) {
        this.cablePos = cablePos;
    }

    public ServerCablePacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) { //encode -> save data
        buffer.writeBlockPos(cablePos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Level level = Objects.requireNonNull(ctx.get().getSender()).level;
            BlockEntity be = level.getBlockEntity(cablePos);

            if (be instanceof CableBE cableBE) {
                CableNetwork network = new CableNetwork(level, cableBE);
                network.find();
                success.set(true);
            } else {
                CableNetwork network = new CableNetwork(level, cablePos);
                network.killConnection();
                success.set(true);
            }
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
