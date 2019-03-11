package com.github.psnrigner.mha.plugin.listeners;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class ConnectionListener implements Listener
{
    private final MHAPlugin plugin;

    public ConnectionListener(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onLogin(AsyncPlayerPreLoginEvent event)
    {
        if (this.plugin.getGame().isStarted())
        {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.plugin.getTranslationManager().getTranslatedMessage(event.getUniqueId(), "login.deny_started", ChatColor.RED));
            return;
        }

        Map<UUID, MHAPlayer> players = this.plugin.getPlayerManager().getInGamePlayers();
        if (players.size() >= this.plugin.getGame().getMaxPlayers())
        {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.plugin.getTranslationManager().getTranslatedMessage(event.getUniqueId(), "login.deny_full", ChatColor.RED));
            return;
        }

        this.plugin.getPlayerManager().onLogin(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        MHAPlayer mhaPlayer = this.plugin.getPlayerManager().getPlayer(player.getUniqueId());

        if (mhaPlayer == null)
        {
            player.kickPlayer(this.plugin.getTranslationManager().getTranslatedMessage(player.getUniqueId(), "error.internal", ChatColor.RED));
            return;
        }

        if (this.plugin.getGame().isStarted())
        {
            player.kickPlayer(this.plugin.getTranslationManager().getTranslatedMessage(player.getUniqueId(), "login.deny_full", ChatColor.RED));
            return;
        }

        mhaPlayer.setPlayer(player);

        this.plugin.getTranslationManager().broadcastMessageWithPrefix("game.join", ChatColor.YELLOW, player.getDisplayName() + ChatColor.YELLOW);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event)
    {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        this.plugin.getPlayerManager().onLogout(player.getUniqueId());

        this.plugin.getTranslationManager().broadcastMessageWithPrefix("game.disconnect", ChatColor.YELLOW, player.getDisplayName() + ChatColor.YELLOW);
    }
}
