package com.github.psnrigner.mha.plugin.commands;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.disguises.Skin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DisguiseCommand implements CommandExecutor, TabCompleter
{
    private final MHAPlugin plugin;

    public DisguiseCommand(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label /* STOP COMPLAINING ANT74 */, String[] args)
    {
        if (!(commandSender instanceof Player))
        {
            return true;
        }

        if (args.length != 1)
        {
            this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.usage", ChatColor.RED);
            return true;
        }

        if (args[0].equalsIgnoreCase("reset"))
        {
            boolean success = this.plugin.getDisguiseManager().undisguise((Player) commandSender, false);

            if (success)
            {
                this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.undisguised", ChatColor.GREEN);
            }
            else
            {
                this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.undisguised_fail", ChatColor.RED);
            }
        }
        else
        {
            Skin skin;

            try
            {
                skin = Skin.valueOf(args[0].toUpperCase());
            }
            catch (IllegalArgumentException ignored)
            {
                this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.invalid_skin", ChatColor.RED);
                return true;
            }

            boolean success = this.plugin.getDisguiseManager().disguise((Player) commandSender, skin);

            if (success)
            {
                this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.disguised", ChatColor.GREEN);
            }
            else
            {
                this.plugin.getTranslationManager().sendMessage(commandSender, "command.disguise.disguised_fail", ChatColor.RED);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args)
    {
        return new ArrayList<>(); // TODO
    }
}
