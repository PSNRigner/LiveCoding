package com.github.psnrigner.mha.plugin.game.ffa;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.characters.AbstractCharacter;
import com.github.psnrigner.mha.plugin.game.AbstractGame;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;
import com.github.psnrigner.mha.plugin.game.WorldConfiguration;
import org.bukkit.Location;
import org.bukkit.World;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FFAGame extends AbstractGame
{
    private final Random random;
    private List<Location> locations;
    private int lives;

    public FFAGame(MHAPlugin plugin)
    {
        super(plugin, "game.ffa", 1, 16);

        this.random = new SecureRandom();
    }

    @Override
    protected Class<? extends WorldConfiguration> getWorldConfigurationClass()
    {
        return FFAWorldConfiguration.class;
    }

    @Override
    protected void loadWorldConfig(World world, WorldConfiguration configuration)
    {
        FFAWorldConfiguration ffaWorldConfiguration = (FFAWorldConfiguration) configuration;

        this.locations = ffaWorldConfiguration.spawns.stream().map(location -> new Location(world, location.x, location.y, location.z)).collect(Collectors.toList());
        this.lives = ffaWorldConfiguration.lives;

        if (this.locations.size() < this.getMaxPlayers())
        {
            throw new IllegalStateException("Not enough spawn locations (required: " + this.getMaxPlayers() + ", found:" + this.locations.size() + ")");
        }
    }

    @Override
    protected void teleportPlayers()
    {
        Iterator<Location> nextSpawn = this.locations.iterator();

        this.plugin.getPlayerManager().getInGamePlayers().forEach(((uuid, player) ->
        {
            player.getPlayer().teleport(nextSpawn.next());
            player.freeze();
        }));
    }

    @Override
    protected boolean allowRespawn()
    {
        return this.lives > 1;
    }

    @Override
    protected void respawnPlayer(MHAPlayer player)
    {
        // TODO Respawn logic
    }

    @Override
    protected void setCharacters()
    {
        List<AbstractCharacter> characters = this.plugin.getCharacterManager().getCharacters();

        this.plugin.getPlayerManager().getInGamePlayers().forEach(((uuid, player) ->
        {
            if (player.getCharacter() == null)
            {
                player.setCharacter(characters.get(this.random.nextInt(characters.size())));
            }
        }));
    }

    @Override
    public void startGame()
    {
        super.startGame();

        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> this.plugin.getPlayerManager().getInGamePlayers().forEach(((uuid, player) -> player.unfreeze())), 100L);
    }
}
