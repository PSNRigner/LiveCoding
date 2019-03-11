package com.github.psnrigner.mha.plugin.game;

import org.bukkit.entity.Player;

import java.util.UUID;

public class MHAPlayer
{
    private final UUID uuid;
    private Player player;

    public MHAPlayer(UUID uuid)
    {
        this.uuid = uuid;
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
}
