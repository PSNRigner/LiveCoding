package com.github.psnrigner.mha.plugin.disguises;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_13_R2.EntityHuman;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EnumGamemode;
import net.minecraft.server.v1_13_R2.Packet;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_13_R2.PacketPlayOutRespawn;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.WorldServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class DisguiseManager
{
    private final MHAPlugin plugin;
    private final Map<UUID, GameProfile> originalSkin;
    private final Field gameProfileField;

    public DisguiseManager(MHAPlugin plugin)
    {
        this.plugin = plugin;
        this.originalSkin = new HashMap<>();

        try
        {
            this.gameProfileField = EntityHuman.class.getDeclaredField("h");
            this.gameProfileField.setAccessible(true);
        }
        catch (Exception ex)
        {
            this.plugin.getLogger().log(Level.SEVERE, "Failed to get game profile field from EntityHuman", ex);

            throw new IllegalStateException(ex);
        }

        this.plugin.getLogger().info("Loaded disguise manager");
    }

    @SuppressWarnings("deprecation")
    public boolean disguise(Player player, Skin skin)
    {
        String texture = skin.getTexture();
        String signature = skin.getSignature();

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        List<Packet<?>> packetList = new ArrayList<>(4);
        List<Packet<?>> selfPacketList = new ArrayList<>(3);

        packetList.add(new PacketPlayOutEntityDestroy(player.getEntityId()));
        packetList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));

        GameProfile oldProfile = entityPlayer.getProfile();

        GameProfile newProfile = new GameProfile(oldProfile.getId(), oldProfile.getName());
        newProfile.getProperties().put("textures", new Property("textures", texture, signature));

        try
        {
            this.gameProfileField.set(entityPlayer, newProfile);
        }
        catch (IllegalAccessException ignored)
        {
            return false;
        }

        packetList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        packetList.add(new PacketPlayOutNamedEntitySpawn(entityPlayer));

        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();

        selfPacketList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
        selfPacketList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        selfPacketList.add(new PacketPlayOutRespawn(world.dimension, world.getDifficulty(), world.getWorldData().getType(), EnumGamemode.getById(player.getGameMode().getValue())));

        this.originalSkin.putIfAbsent(player.getUniqueId(), oldProfile);

        this.plugin.getServer().getOnlinePlayers().forEach(otherPlayer ->
        {
            PlayerConnection playerConnection = ((CraftPlayer) otherPlayer).getHandle().playerConnection;

            (otherPlayer.equals(player) ? selfPacketList : packetList).forEach(playerConnection::sendPacket);
            //packetList.forEach(playerConnection::sendPacket);
        });

        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean undisguise(Player player, boolean logout)
    {
        GameProfile oldProfile = this.originalSkin.get(player.getUniqueId());

        if (oldProfile == null)
        {
            return false;
        }

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        List<Packet<?>> packetList = new ArrayList<>(4);
        List<Packet<?>> selfPacketList = new ArrayList<>(3);

        packetList.add(new PacketPlayOutEntityDestroy(player.getEntityId()));
        packetList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));

        try
        {
            this.gameProfileField.set(entityPlayer, oldProfile);
        }
        catch (IllegalAccessException ignored)
        {
            return false;
        }

        if (!logout)
        {
            packetList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
            packetList.add(new PacketPlayOutNamedEntitySpawn(entityPlayer));
        }

        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();

        if (!logout)
        {
            selfPacketList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
            selfPacketList.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
            selfPacketList.add(new PacketPlayOutRespawn(world.dimension, world.getDifficulty(), world.getWorldData().getType(), EnumGamemode.getById(player.getGameMode().getValue())));
        }

        this.originalSkin.put(player.getUniqueId(), oldProfile);

        this.plugin.getServer().getOnlinePlayers().forEach(otherPlayer ->
        {
            PlayerConnection playerConnection = ((CraftPlayer) otherPlayer).getHandle().playerConnection;

            (otherPlayer.equals(player) ? selfPacketList : packetList).forEach(playerConnection::sendPacket);
        });

        return true;
    }
}
