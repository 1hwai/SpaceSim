package net.hawon.spacesim.common.network;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.network.packet.energy.ClientEnergyPacket;
import net.hawon.spacesim.common.network.packet.energy.ServerEnergyPacket;
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
        INSTANCE.messageBuilder(ServerEnergyPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerEnergyPacket::encode).decoder(ServerEnergyPacket::new)
                .consumer(ServerEnergyPacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientEnergyPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientEnergyPacket::encode).decoder(ClientEnergyPacket::new)
                .consumer(ClientEnergyPacket::handle)
                .add();
    }
}
