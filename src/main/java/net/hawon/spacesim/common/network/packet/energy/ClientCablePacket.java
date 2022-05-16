package net.hawon.spacesim.common.network.packet.energy;

import net.hawon.spacesim.client.ClientAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientCablePacket {
    public final BlockPos cablePos;

    public ClientCablePacket(BlockPos cablePos) {
        this.cablePos = cablePos;
    }

    public ClientCablePacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) { //encode -> save data
        buffer.writeBlockPos(cablePos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                success.set(ClientAccess.updateCable(cablePos));
            });
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
