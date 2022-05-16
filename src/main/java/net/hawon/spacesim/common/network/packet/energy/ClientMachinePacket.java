package net.hawon.spacesim.common.network.packet.energy;

import net.hawon.spacesim.client.ClientAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientMachinePacket {

    public final BlockPos machinePos;

    public ClientMachinePacket(BlockPos pos) {
        this.machinePos = pos;
    }

    public ClientMachinePacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(machinePos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                success.set(ClientAccess.updateCable(machinePos));
            });
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
