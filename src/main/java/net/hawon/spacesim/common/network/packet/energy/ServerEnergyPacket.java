package net.hawon.spacesim.common.network.packet.energy;

import net.hawon.spacesim.common.block.pipe.cables.CableBlockEntity;
import net.hawon.spacesim.common.network.energy.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerEnergyPacket {

    public final BlockPos cablePos;

    public ServerEnergyPacket(BlockPos cablePos) {
        this.cablePos = cablePos;
    }

    public ServerEnergyPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) { //encode -> save data
        buffer.writeBlockPos(cablePos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            Level level = Objects.requireNonNull(ctx.get().getSender()).level;
            BlockEntity blockEntity = level.getBlockEntity(cablePos);

            if (blockEntity instanceof CableBlockEntity cableBE) {
                EnergyNetwork energyNetwork = new EnergyNetwork(level);
                energyNetwork.update(cablePos, cableBE.getSourcePos());

                success.set(true);
            }
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
