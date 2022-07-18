package io.github.nexusdino.phoenixphiesta.core.network;

import io.github.nexusdino.phoenixphiesta.PhoenixPhiesta;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;

public final class PacketHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    public static int getPacketId() {
        return packetId++;
    }

    public static void init() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PhoenixPhiesta.MOD_ID, "main"))
                .networkProtocolVersion(() -> "1.0")
                .serverAcceptedVersions(s -> true)
                .clientAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ClientboundUpdateEnergyPacket.class, getPacketId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundUpdateEnergyPacket::encode)
                .decoder(ClientboundUpdateEnergyPacket::new)
                .consumerMainThread(ClientboundUpdateEnergyPacket::handle)
                .add();
    }

    public static <MSG> void sendToClient(MSG msg) {
        INSTANCE.sendTo(msg, Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }
}
