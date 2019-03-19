package com.github.psnrigner.mha.plugin;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.characters.CharacterManager;
import com.github.psnrigner.mha.plugin.commands.DisguiseCommand;
import com.github.psnrigner.mha.plugin.disguises.DisguiseManager;
import com.github.psnrigner.mha.plugin.game.AbstractGame;
import com.github.psnrigner.mha.plugin.game.ffa.FFAGame;
import com.github.psnrigner.mha.plugin.listeners.ConnectionListener;
import com.github.psnrigner.mha.plugin.listeners.SecurityListener;
import com.github.psnrigner.mha.plugin.listeners.SkillListener;
import com.github.psnrigner.mha.plugin.manager.PlayerManager;
import com.github.psnrigner.mha.plugin.manager.TranslationManager;
import com.github.psnrigner.mha.plugin.tasks.GameCountdown;
import org.bukkit.Location;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MHAPluginImpl extends JavaPlugin implements MHAPlugin
{
    private TranslationManager translationManager;
    private CharacterManager characterManager;
    private DisguiseManager disguiseManager;
    private PlayerManager playerManager;
    private AbstractGame game;

    @Override
    public void onEnable()
    {
        try
        {
            this.translationManager = new TranslationManager(this);
            this.characterManager = new CharacterManager(this);
            this.disguiseManager = new DisguiseManager(this);
            this.playerManager = new PlayerManager(this);

            this.game = new FFAGame(this); // TODO Load from config

            ConnectionListener connectionListener = new ConnectionListener(this);
            this.getServer().getPluginManager().registerEvents(connectionListener, this);
            this.getServer().getPluginManager().registerEvents(new SecurityListener(this), this);
            this.getServer().getPluginManager().registerEvents(new SkillListener(this), this);

            PluginCommand disguiseCommand = this.getCommand("disguise");
            DisguiseCommand disguiseCommandExecutor = new DisguiseCommand(this);
            disguiseCommand.setExecutor(disguiseCommandExecutor);
            disguiseCommand.setTabCompleter(disguiseCommandExecutor);

            new GameCountdown(this).runTaskTimer(this, 20L, 20L);

            this.getServer().getScheduler().runTaskTimer(this, () -> this.characterManager.getCharacters().forEach(character -> character.getSkills().values().forEach(AbstractSkill::tick)), 1L, 1L);

            this.getServer().getOnlinePlayers().forEach(player ->
            {
                connectionListener.onLogin(new AsyncPlayerPreLoginEvent(player.getName(), player.getAddress().getAddress(), player.getUniqueId()));
                connectionListener.onJoin(new PlayerJoinEvent(player, ""));
            });
        }
        catch (Throwable throwable)
        {
            this.getLogger().log(Level.SEVERE, "Error while starting server", throwable);
            this.getServer().shutdown();
        }
    }

    @Override
    public void onDisable()
    {
        this.disguiseManager.undisguiseAll();

        Location spawn = new Location(this.getServer().getWorlds().get(0), 0.5D, 1D, 0.5D); // TODO Load from config
        this.getServer().getOnlinePlayers().forEach(player ->
        {
            player.teleport(spawn);
            player.getInventory().clear();
        });
    }

    @Override
    public AbstractGame getGame()
    {
        return this.game;
    }

    @Override
    public TranslationManager getTranslationManager()
    {
        return this.translationManager;
    }

    @Override
    public DisguiseManager getDisguiseManager()
    {
        return this.disguiseManager;
    }

    @Override
    public PlayerManager getPlayerManager()
    {
        return this.playerManager;
    }

    @Override
    public CharacterManager getCharacterManager()
    {
        return this.characterManager;
    }
}
