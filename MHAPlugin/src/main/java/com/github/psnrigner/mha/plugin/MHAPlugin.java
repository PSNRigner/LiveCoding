package com.github.psnrigner.mha.plugin;

import com.github.psnrigner.mha.plugin.disguises.DisguiseManager;
import com.github.psnrigner.mha.plugin.game.AbstractGame;
import com.github.psnrigner.mha.plugin.manager.PlayerManager;
import com.github.psnrigner.mha.plugin.manager.TranslationManager;
import org.bukkit.plugin.Plugin;

public interface MHAPlugin extends Plugin
{
    AbstractGame getGame();

    TranslationManager getTranslationManager();

    DisguiseManager getDisguiseManager();

    PlayerManager getPlayerManager();
}
