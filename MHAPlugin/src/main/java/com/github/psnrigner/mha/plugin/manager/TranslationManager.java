package com.github.psnrigner.mha.plugin.manager;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class TranslationManager
{
    private static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.AQUA + "MHA" + ChatColor.GOLD + "] ";
    private final MHAPlugin plugin;
    private final Map<Language, Map<String, String>> translations;

    public TranslationManager(MHAPlugin plugin)
    {
        this.plugin = plugin;
        this.translations = new HashMap<>();

        for (Language language : Language.values())
        {
            Map<String, String> translations = new HashMap<>();

            try (InputStream inputStream = this.plugin.getResource("lang/" + language.code + ".yml"))
            {
                YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));

                for (String key : yamlFile.getKeys(true))
                {
                    translations.put(key, yamlFile.getString(key, key));
                }
            }
            catch (Exception ex)
            {
                this.plugin.getLogger().log(Level.WARNING, "Failed to load translations for language " + language, ex);
            }

            this.translations.put(language, translations);
        }
    }

    public void sendMessage(CommandSender commandSender, String key, Object... args)
    {
        commandSender.sendMessage(this.getTranslatedMessage(commandSender, key, args));
    }

    public void broadcastMessageWithPrefix(String key, Object... args)
    {
        for (Player player : this.plugin.getServer().getOnlinePlayers())
        {
            player.sendMessage(TranslationManager.PREFIX + this.getTranslatedMessage(player, key, args));
        }

        this.plugin.getServer().getConsoleSender().sendMessage(TranslationManager.PREFIX + this.getTranslatedMessage(this.plugin.getServer().getConsoleSender(), key, args));
    }

    public String getTranslatedMessage(CommandSender commandSender, String key, Object... args)
    {
        if (commandSender instanceof Player)
        {
            return this.getTranslatedMessage(((Player) commandSender).getUniqueId(), key, args);
        }
        else
        {
            String value = this.translations.get(Language.ENGLISH).get(key);

            if (value == null)
            {
                return key;
            }
            else
            {
                try
                {
                    return MessageFormat.format(value, args);
                }
                catch (Exception ignored)
                {
                    return key;
                }
            }
        }
    }

    public String getTranslatedMessage(UUID uuid, String key, Object... args)
    {
        Language language = Language.ENGLISH; // TODO Let them change

        String value = this.translations.get(language).get(key);

        if (value == null)
        {
            value = this.translations.get(Language.ENGLISH).get(key);
        }

        if (value == null)
        {
            return key;
        }
        else
        {
            try
            {
                return MessageFormat.format(value, args);
            }
            catch (Exception ignored)
            {
                return key;
            }
        }
    }

    public enum Language
    {
        ENGLISH("en"),
        FRENCH("fr");

        private final String code;

        Language(String code)
        {
            this.code = code;
        }
    }
}
