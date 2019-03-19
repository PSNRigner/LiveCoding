package com.github.psnrigner.mha.plugin.game;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.characters.AbstractCharacter;
import net.minecraft.server.v1_13_R2.EntityPig;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutMount;
import net.minecraft.server.v1_13_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MHAPlayer
{
    private static Map<World, EntityPig> freezeEntities = new ConcurrentHashMap<>();

    private final MHAPlugin plugin;
    private final UUID uuid;
    private Player player;
    private boolean frozen;
    private AbstractCharacter character;

    public MHAPlayer(MHAPlugin plugin, UUID uuid)
    {
        this.plugin = plugin;
        this.uuid = uuid;
        this.character = null;
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void freeze()
    {
        if (this.player == null || this.frozen)
        {
            return;
        }

        this.frozen = true;

        EntityPlayer entityPlayer = ((CraftPlayer) this.player).getHandle();

        EntityPig freezeEntity = MHAPlayer.freezeEntities.computeIfAbsent(entityPlayer.world, EntityPig::new);
        freezeEntity.setInvisible(true);

        Location location = this.player.getLocation();
        freezeEntity.setPosition(location.getX(), location.getY(), location.getZ());
        freezeEntity.passengers.add(entityPlayer);

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(freezeEntity));
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutMount(freezeEntity));

        freezeEntity.passengers.remove(entityPlayer);
    }

    public void unfreeze()
    {
        if (this.player == null || !this.frozen)
        {
            return;
        }

        this.frozen = false;

        EntityPlayer entityPlayer = ((CraftPlayer) this.player).getHandle();

        EntityPig freezeEntity = MHAPlayer.freezeEntities.computeIfAbsent(entityPlayer.world, EntityPig::new);

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(freezeEntity.getId()));
    }

    public boolean isFrozen()
    {
        return this.frozen;
    }

    public void updateInventory()
    {
        if (this.player == null)
        {
            return;
        }

        PlayerInventory playerInventory = this.player.getInventory();

        if (this.character != null)
        {
            this.character.getSkills().forEach((slot, skill) ->
            {
                ItemStack itemStack = skill.getItemStack(this);

                if (!itemStack.equals(playerInventory.getItem(slot)))
                {
                    playerInventory.setItem(slot, itemStack);
                }
            });
        }
    }

    public AbstractCharacter getCharacter()
    {
        return this.character;
    }

    public void setCharacter(AbstractCharacter character)
    {
        this.character = character;

        if (this.player != null)
        {
            if (character != null)
            {
                this.plugin.getDisguiseManager().disguise(this.player, character.getSkin());
            }
            else
            {
                this.plugin.getDisguiseManager().undisguise(this.player, false);
            }
        }
    }
}
