package net.hawon.spacesim.common.network;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.network.packet.energy.ClientCablePacket;
import net.hawon.spacesim.common.network.packet.energy.ClientMachinePacket;
import net.hawon.spacesim.common.network.packet.energy.ServerCablePacket;
import net.hawon.spacesim.common.network.packet.energy.ServerMachinePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SpaceSim.MOD_ID, "networking"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private PacketHandler() {
    }

    public static void init() {
        int index = 0;

        //Cable
        INSTANCE.messageBuilder(ServerCablePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerCablePacket::encode).decoder(ServerCablePacket::new)
                .consumer(ServerCablePacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientCablePacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientCablePacket::encode).decoder(ClientCablePacket::new)
                .consumer(ClientCablePacket::handle)
                .add();
        //Machine
        INSTANCE.messageBuilder(ServerMachinePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerMachinePacket::encode).decoder(ServerMachinePacket::new)
                .consumer(ServerMachinePacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientMachinePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ClientMachinePacket::encode).decoder(ClientMachinePacket::new)
                .consumer(ClientMachinePacket::handle)
                .add();

    }
}
