package com.github.psnrigner.mha.plugin.listeners;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import org.bukkit.event.Listener;

public class SecurityListener implements Listener
{
    private final MHAPlugin plugin;

    public SecurityListener(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }
}
