package com.github.psnrigner.mha.plugin;

import com.github.psnrigner.mha.plugin.commands.DisguiseCommand;
import com.github.psnrigner.mha.plugin.disguises.DisguiseManager;
import com.github.psnrigner.mha.plugin.game.AbstractGame;
import com.github.psnrigner.mha.plugin.game.ffa.FFAGame;
import com.github.psnrigner.mha.plugin.listeners.ConnectionListener;
import com.github.psnrigner.mha.plugin.listeners.SecurityListener;
import com.github.psnrigner.mha.plugin.manager.PlayerManager;
import com.github.psnrigner.mha.plugin.manager.TranslationManager;
import com.github.psnrigner.mha.plugin.tasks.GameCountdown;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MHAPluginImpl extends JavaPlugin implements MHAPlugin
{
    private TranslationManager translationManager;
    private DisguiseManager disguiseManager;
    private PlayerManager playerManager;
    private AbstractGame game;

    @Override
    public void onEnable()
    {
        this.translationManager = new TranslationManager(this);
        this.disguiseManager = new DisguiseManager(this);
        this.playerManager = new PlayerManager();

        this.game = new FFAGame(); // TODO

        this.getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new SecurityListener(this), this);

        PluginCommand disguiseCommand = this.getCommand("disguise");
        DisguiseCommand disguiseCommandExecutor = new DisguiseCommand(this);
        disguiseCommand.setExecutor(disguiseCommandExecutor);
        disguiseCommand.setTabCompleter(disguiseCommandExecutor);

        new GameCountdown(this).runTaskTimer(this, 20L, 20L);
    }

    @Override
    public void onDisable()
    {

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
}
