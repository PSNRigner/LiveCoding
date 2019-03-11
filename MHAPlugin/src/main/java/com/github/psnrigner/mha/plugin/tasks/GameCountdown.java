package com.github.psnrigner.mha.plugin.tasks;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class GameCountdown extends BukkitRunnable
{
    private final MHAPlugin plugin;

    private int countdown = -1;

    public GameCountdown(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        if (this.countdown == 0)
        {
            this.plugin.getTranslationManager().broadcastMessageWithPrefix("game.countdown.starting", ChatColor.YELLOW);

            try
            {
                this.plugin.getGame().startGame();
            }
            catch (Throwable throwable)
            {
                this.plugin.getLogger().log(Level.SEVERE, "Error starting game", throwable);
                this.plugin.getTranslationManager().broadcastMessageWithPrefix("error.starting_game", ChatColor.RED);
            }

            this.cancel();
            return;
        }

        Map<UUID, MHAPlayer> players = this.plugin.getPlayerManager().getInGamePlayers();

        if (this.countdown == -1 && players.size() >= this.plugin.getGame().getRequiredPlayers())
        {
            this.countdown = 30;
        }
        else if (this.countdown != -1 && players.size() < this.plugin.getGame().getRequiredPlayers())
        {
            this.countdown = -1;
        }

        if (this.countdown == -1)
        {
            return;
        }

        if (this.countdown % 10 == 0 || this.countdown <= 5)
        {
            this.plugin.getTranslationManager().broadcastMessageWithPrefix(this.countdown == 1 ? "game.countdown.step_single" : "game.countdown.step_plural", ChatColor.YELLOW, this.countdown);
        }

        --this.countdown;
    }
}
