package com.github.psnrigner.mha.plugin.game;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.World;

import java.io.FileReader;

public abstract class AbstractGame
{
    private static final Gson GSON = new GsonBuilder().create();

    protected final MHAPlugin plugin;
    private final String name;
    private final int requiredPlayers;
    private final int maxPlayers;
    private boolean started;

    protected AbstractGame(MHAPlugin plugin, String name, int requiredPlayers, int maxPlayers)
    {
        this.plugin = plugin;
        this.name = name;
        this.requiredPlayers = requiredPlayers;
        this.maxPlayers = maxPlayers;
        this.started = false;

        try (FileReader fileReader = new FileReader("world/map.json")) // TODO Configurable maps
        {
            World world = this.plugin.getServer().getWorld("world"); // TODO Configurable maps

            WorldConfiguration worldConfiguration = AbstractGame.GSON.fromJson(fileReader, this.getWorldConfigurationClass());

            this.loadWorldConfig(world, worldConfiguration);
        }
        catch (Throwable throwable)
        {
            throw new IllegalStateException("Error while loading game config", throwable);
        }
    }

    protected abstract Class<? extends WorldConfiguration> getWorldConfigurationClass();

    protected abstract void loadWorldConfig(World world, WorldConfiguration configuration);

    protected abstract void teleportPlayers();

    protected abstract boolean allowRespawn();

    protected abstract void respawnPlayer(MHAPlayer player);

    protected abstract void setCharacters();

    public String getName()
    {
        return this.name;
    }

    public final int getRequiredPlayers()
    {
        return this.requiredPlayers;
    }

    public final int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void startGame()
    {
        this.started = true;

        this.plugin.getServer().getOnlinePlayers().forEach(player ->
        {
            player.setFallDistance(0F); // Reset fall distance for safety
            player.getInventory().clear();
        });
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> this.plugin.getPlayerManager().getInGamePlayers().values().forEach(MHAPlayer::updateInventory), 0L, 10L);

        this.setCharacters();
        this.teleportPlayers();
    }

    public boolean isStarted()
    {
        return this.started;
    }

    public void handleDeath(MHAPlayer player)
    {
        if (this.allowRespawn())
        {
            this.respawnPlayer(player);
        }
    }
}
